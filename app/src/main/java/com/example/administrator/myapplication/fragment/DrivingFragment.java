package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ChooseCarActivity;
import com.example.administrator.myapplication.activity.UploadDrivingPicActivity;
import com.example.administrator.myapplication.activity.UploadPolicePicActivity;
import com.example.administrator.myapplication.bean.helper.StepResultHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.DateUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.TimeUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.dialog.ExitCheckDialog;
import com.example.administrator.myapplication.event.UploadPicEvent;
import com.example.administrator.myapplication.factory.CountFactory;
import com.example.administrator.myapplication.factory.StatusFactory;
import com.example.administrator.myapplication.http.StepRequest;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 行驶测试
 */

public class DrivingFragment extends BaseFragment {
    @BindView(R.id.fra_police_btn_start)
    Button btnStart;
    @BindView(R.id.fra_police_img_pic)
    TextView tvPic;
    @BindView(R.id.fra_police_tv_jump)
    TextView tvJump;
    @BindView(R.id.fra_police_tv_time)
    TextView tvTime;

    private Subscription subscription;
     private CountFactory factory;
    private boolean isTesting;
    private boolean isUpload;
    private ExitCheckDialog exitCheckDialog;
    @Override
    public int layoutResourceID() {
        return R.layout.fragment_driving;
    }

    @OnClick({R.id.fra_police_btn_start, R.id.fra_police_img_pic, R.id.fra_police_tv_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fra_police_btn_start:
                    recordBeginDate();

                break;
            case R.id.fra_police_img_pic:
                if (isUpload){
                    ViewInjectUtil.longToast("已经提交过照片，再次提交会覆盖");
                }
                Intent intent=new Intent(getActivity(), UploadDrivingPicActivity.class);
                intent.putExtra(ConstantUtil.BUNDLE_KEY_BOOLEAN,isUpload);
                startActivity(intent);
                break;
            case R.id.fra_police_tv_jump:
                if (!isTesting){
                    exitCheckDialog.show();
                }
//                else {
//                    ViewInjectUtil.toast("请等待测试结束后在返回");
//                }
                break;
            default:
                break;
        }
    }
    private void recordBeginDate(){
        StepRequest request=new StepRequest(getActivity(), NetHelper.URL_STEP,status, DateUtil.getCurrentTime(true,new Date()),null);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                startTest();
                showAddPic();
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0,request);
    }
    private void recordEndDate(){
        StepRequest request=new StepRequest(getActivity(),NetHelper.URL_STEP,status,null,DateUtil.getCurrentTime(true,new Date()));
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                StepResultHelper helper = (StepResultHelper) object;
                if (helper != null) {
                    boolean isOver=helper.isOver();
                    if (isOver){
                        Intent intent=new Intent(getActivity(), ChooseCarActivity.class);
                        getActivity().startActivity(intent);
                    }
                }
                getActivity().finish();
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(1,request);
    }
    private int status;
    @Override
    protected void initData() {
        super.initData();
        if (MyApplication.get().isDirectly()){
            status=StatusFactory.STEP_DIRECTLY_DRIVING;
        }else {
            status=StatusFactory.STEP_TURN_DRIVING;
        }    initExitDialog();
        initTimeFactory();
    }
    private void initExitDialog(){
        exitCheckDialog=new ExitCheckDialog(getActivity());
        exitCheckDialog.setOnTipsButtonClick(new ExitCheckDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                getActivity().finish();
            }
        });
    }
    private void initTimeFactory() {

        tvTime.setText("步骤倒计时："+TimeUtil.longToTime(ConstantUtil.TIME_DRIVING));
        factory = new CountFactory(ConstantUtil.TIME_DRIVING);
        factory.setOnCountDownListener(new CountFactory.OnCountDownListener() {
            @Override
            public void onProgress(long millisUntilFinished) {
                tvTime.setText("步骤倒计时："+TimeUtil.longToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                stopTest();
                tvTime.setText("步骤倒计时："+"00:00");
                recordEndDate();
            }
        });
    }

    private void startTimeDown() {

        if (factory!=null){
            factory.start();
        }
    }

    private void resetTimeDown() {
        factory.cancel();
        initTimeFactory();
    }

    private void startTest() {
        isTesting=true;
        startTimeDown();

    }

    private void stopTest() {
        isTesting=false;
        resetTimeDown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (factory!=null){
            factory.cancel();
        }
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }

    }
    private void showAddPic(){
        btnStart.setVisibility(View.GONE);
        tvPic.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        super.setListener();
        subscription= RxBus.getDefault().toObservable(UploadPicEvent.class).subscribe(new Action1<UploadPicEvent>() {
            @Override
            public void call(UploadPicEvent uploadPicEvent) {
                if (uploadPicEvent.getType()==UploadPicEvent.TYPE_DRIVING&&uploadPicEvent.isSuccess()){
                    isUpload=true;
                }
            }
        });
    }

}
