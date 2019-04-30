package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ChooseCarActivity;
import com.example.administrator.myapplication.activity.UploadChargePicActivity;
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
 * 平台登入fragment
 */

public class PlatLoginFragment extends BaseFragment {
    @BindView(R.id.fra_platlogin_btn_start)
    Button btnStart;
    @BindView(R.id.fra_platlogin_tv_jump)
    TextView tvJump;
    @BindView(R.id.fra_platlogin_tv_time)
    TextView tvTime;

    private CountFactory factory;
    private boolean isTesting;
    private ExitCheckDialog exitCheckDialog;
    private TipsDialog tipsDialog;
    @Override
    public int layoutResourceID() {
        return R.layout.fragment_platlogin;
    }

    @Override
    protected void initData() {
        super.initData();
        initExitDialog();
        initTimeFactory();
    }


    @OnClick({R.id.fra_platlogin_btn_start, R.id.fra_platlogin_tv_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fra_platlogin_btn_start:
                recordBeginDate();

                break;

            case R.id.fra_platlogin_tv_jump:
                if (!isTesting) {
                    exitCheckDialog.show();
                }
                break;
            default:
                break;
        }
    }

    private void initExitDialog() {
        exitCheckDialog = new ExitCheckDialog(getActivity());
        exitCheckDialog.setOnTipsButtonClick(new ExitCheckDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                getActivity().finish();
            }
        });
    }

    private void initTimeFactory() {
        tvTime.setText("步骤倒计时：" + TimeUtil.longToTime(ConstantUtil.TIME_PLATLOGIN));
        factory = new CountFactory(ConstantUtil.TIME_PLATLOGIN);
        factory.setOnCountDownListener(new CountFactory.OnCountDownListener() {
            @Override
            public void onProgress(long millisUntilFinished) {
                tvTime.setText("步骤倒计时：" + TimeUtil.longToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                stopTest();
                tvTime.setText("步骤倒计时：" + "00:00");
                recordEndDate();
            }
        });

    }

    private void recordBeginDate() {
        StepRequest request = new StepRequest(getActivity(), NetHelper.URL_STEP, StatusFactory.STEP_PLATFORM, DateUtil.getCurrentTime(true, new Date()), null);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                startTest();
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, request);
    }

    private void recordEndDate() {
        StepRequest request = new StepRequest(getActivity(), NetHelper.URL_STEP, StatusFactory.STEP_PLATFORM, null,null, DateUtil.getCurrentTime(true, new Date()));
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                StepResultHelper helper= (StepResultHelper) object;
                if (helper!=null){
                    String strResult= helper.getResult();
                    if ("平台登入成功".equals(strResult)){
                        testSuccess();
                    }else {
                        testFail();
                    }
                }else {
                    testFail();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(1, request);
    }

    private void testSuccess() {
        tipsDialog = new TipsDialog(getActivity());
        tipsDialog.setOnTipsButtonClick(new TipsDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
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
                stopTest();
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
        isTesting = true;
        startTimeDown();
    }

    private void stopTest() {
        btnStart.setVisibility(View.VISIBLE);
        isTesting = false;
        resetTimeDown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (factory != null) {
            factory.cancel();
        }
    }


}
