package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ChooseCarActivity;
import com.example.administrator.myapplication.bean.helper.StepResultHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.DateUtil;
import com.example.administrator.myapplication.common.utils.TimeUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.dialog.ExitCheckDialog;
import com.example.administrator.myapplication.dialog.TipsDialog;
import com.example.administrator.myapplication.factory.CountFactory;
import com.example.administrator.myapplication.factory.StatusFactory;
import com.example.administrator.myapplication.http.StepRequest;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 补发测试
 */

public class SupplyFragment extends BaseFragment {
    @BindView(R.id.fra_supply_tv_time)
    TextView tvTime;
    @BindView(R.id.fra_supply_btn_start)
    Button btnStart;
    @BindView(R.id.fra_supply_tv_jump)
    TextView tvJump;
    private boolean isTesting = false;
    private CountFactory factory;
    private TipsDialog tipsDialog;
    private ExitCheckDialog exitCheckDialog;
    @Override
    public int layoutResourceID() {
        return R.layout.fragment_supply;
    }

    @OnClick({R.id.fra_supply_btn_start, R.id.fra_supply_tv_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fra_supply_btn_start:
                if (!isTesting) {

                    recordBeginDate();
                }
                break;
            case R.id.fra_supply_tv_jump:
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
    private int status;
    @Override
    protected void initData() {
        super.initData();
        if (MyApplication.get().isDirectly()){
            status=StatusFactory.STEP_DIRECTLY_SUPPLY;
        }else {
            status=StatusFactory.STEP_TURN_SUPPLY;
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
        tvTime.setText("步骤倒计时："+TimeUtil.longToTime(ConstantUtil.TIME_SUPPLY));
        factory = new CountFactory(ConstantUtil.TIME_SUPPLY);
        factory.setOnCountDownListener(new CountFactory.OnCountDownListener() {
            @Override
            public void onProgress(long millisUntilFinished) {
                tvTime.setText("步骤倒计时："+TimeUtil.longToTime(millisUntilFinished));
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
        isTesting=false;
        resetTimeDown();
        btnStart.setVisibility(View.VISIBLE);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (factory!=null){
            factory.cancel();
        }
    }
}
