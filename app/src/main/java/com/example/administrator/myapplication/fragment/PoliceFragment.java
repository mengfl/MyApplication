package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ChooseCarActivity;
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
import com.example.administrator.myapplication.dialog.TipsDialog;
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
 * 报警测试
 */

public class PoliceFragment extends BaseFragment {
    @BindView(R.id.fra_police_btn_start)
    Button btnStart;
    @BindView(R.id.fra_police_img_pic)
    ImageView imgPic;
    @BindView(R.id.fra_police_tv_pic)
    TextView tvPic;
    @BindView(R.id.fra_police_tv_jump)
    TextView tvJump;
    @BindView(R.id.fra_police_tv_time)
    TextView tvTime;

    private boolean isTesting = false;
    private CountFactory factory;
    private TipsDialog tipsDialog;
    private int status;
    private Subscription subscription;
    private ExitCheckDialog exitCheckDialog;
    @Override
    public int layoutResourceID() {
        return R.layout.fragment_police;
    }

    @OnClick({R.id.fra_police_btn_start, R.id.fra_police_img_pic, R.id.fra_police_tv_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fra_police_btn_start:
                if (!isTesting) {

                    recordBeginDate();
                }
                break;
            case R.id.fra_police_img_pic:
                Intent intent=new Intent(getActivity(), UploadPolicePicActivity.class);
                startActivityForResult(intent,123);
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
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0,request);
    }
    private boolean isOver;
    private void recordEndDate(){
        StepRequest request=new StepRequest(getActivity(),NetHelper.URL_STEP,status,null,DateUtil.getCurrentTime(true,new Date()));
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                StepResultHelper helper = (StepResultHelper) object;
                if (helper != null) {
                    String strResult = helper.getResult();
                    isOver=helper.isOver();
                    if ("检测成功".equals(strResult)) {
                        testSuccess();
                    } else {
                        testFail();
                    }
                }

            }

            @Override
            public void onFailed() {
                stopTest();
            }
        });
        addNetRequest(1,request);
    }
    @Override
    protected void initData() {
        super.initData();
        if (MyApplication.get().isDirectly()){
            status=StatusFactory.STEP_DIRECTLY_POLICE;
        }else {
            status=StatusFactory.STEP_TURN_POLICE;
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
    @Override
    protected void setListener() {
        super.setListener();
        subscription= RxBus.getDefault().toObservable(UploadPicEvent.class).subscribe(new Action1<UploadPicEvent>() {
            @Override
            public void call(UploadPicEvent uploadPicEvent) {
                if (uploadPicEvent.getType()==UploadPicEvent.TYPE_POLICE&&uploadPicEvent.isSuccess()){
                     tvPic.setVisibility(View.VISIBLE);
                     imgPic.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initTimeFactory() {
        tvTime.setText("步骤倒计时："+TimeUtil.longToTime(ConstantUtil.TIME_POLICE));
        factory = new CountFactory(ConstantUtil.TIME_POLICE);
        factory.setOnCountDownListener(new CountFactory.OnCountDownListener() {
            @Override
            public void onProgress(long millisUntilFinished) {
                tvTime.setText("步骤倒计时：" + TimeUtil.longToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                stopTest();
                recordEndDate();
                tvTime.setText("步骤倒计时：" + "00:00");
            }
        });

    }
    private void testSuccess() {
        tipsDialog = new TipsDialog(getActivity());
        tipsDialog.setOnTipsButtonClick(new TipsDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                if (isOver){
                    Intent intent=new Intent(getActivity(), ChooseCarActivity.class);
                    getActivity().startActivity(intent);
                }
                   getActivity().finish();
            }
        });
        tipsDialog.success("测试成功");
        if (!tipsDialog.isShowing()) {
            tipsDialog.show();
        }
    }

    private void testFail() {
        tipsDialog = new TipsDialog(getActivity());
        tipsDialog.setOnTipsButtonClick(new TipsDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                getActivity().finish();
            }
        });
        tipsDialog.fail("测试失败");
        if (!tipsDialog.isShowing()) {
            tipsDialog.show();
        }
    }

    private void startTimeDown() {
        if (factory != null) {
            factory.start();
        }
    }

    private void resetTimeDown() {
        factory.cancel();
        initTimeFactory();
    }

    private void startTest() {
        btnStart.setVisibility(View.GONE);
        startTimeDown();
        isTesting = true;
    }

    private void stopTest() {
        btnStart.setVisibility(View.VISIBLE);
        isTesting=false;
        resetTimeDown();
        tvPic.setVisibility(View.GONE);
        imgPic.setVisibility(View.VISIBLE);
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
}
