package com.example.administrator.myapplication.fragment.test;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.ChooseCarActivity;
import com.example.administrator.myapplication.activity.PrepareTestActivity;
import com.example.administrator.myapplication.activity.TestingActivity;
import com.example.administrator.myapplication.bean.helper.CheckHelper;
import com.example.administrator.myapplication.bean.helper.OneStepResultHelper;
import com.example.administrator.myapplication.bean.helper.StepResultHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.DateUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.dialog.TipsDialog;
import com.example.administrator.myapplication.factory.StatusFactory;
import com.example.administrator.myapplication.http.FinishCheckRequest;
import com.example.administrator.myapplication.http.StepRequest;
import com.example.administrator.myapplication.http.StepResultRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 平台转发
 */

public class TurnFragment extends BaseFragment {
    @BindView(R.id.fra_turn_ll_before)
    LinearLayout llBefore;
    @BindView(R.id.fra_turn_ll_login)
    LinearLayout llLogin;
    @BindView(R.id.fra_turn_ll_police)
    LinearLayout llPolice;
    @BindView(R.id.fra_turn_ll_supply)
    LinearLayout llSupply;
    @BindView(R.id.fra_turn_ll_driving)
    LinearLayout llDriving;
    @BindView(R.id.fra_turn_ll_charging)
    LinearLayout llCharging;
    @BindView(R.id.fra_turn_ll_after)
    LinearLayout llAfter;

    @BindView(R.id.fra_turn_tv_beforestatus)
    TextView tvBeforeStatus;
    @BindView(R.id.fra_turn_tv_loginstatus)
    TextView tvLoginStatus;
    @BindView(R.id.fra_turn_tv_policestatus)
    TextView tvPoliceStatus;
    @BindView(R.id.fra_turn_tv_supplystatus)
    TextView tvSupplyStatus;
    @BindView(R.id.fra_turn_tv_drivingstatus)
    TextView tvDrivingStatus;
    @BindView(R.id.fra_turn_tv_chargingstatus)
    TextView tvChargingStatus;
    @BindView(R.id.fra_turn_tv_afterstatus)
    TextView tvAfterStatus;

    private TipsDialog dialog;
    private List<Integer> listResult;   //查看每项步骤是否完成
    @Override
    public int layoutResourceID() {
        return R.layout.fragment_turn;
    }


    @Override
    protected void initData() {
        super.initData();
        initDialog();
    }

    private void initDialog() {
        if (dialog == null) {
            dialog = new TipsDialog(getActivity());
            dialog.setOnTipsButtonClick(new TipsDialog.OnTipsButtonClick() {
                @Override
                public void onOkClick() {
                    if (isOver){
                        Intent intent = new Intent(getActivity(), ChooseCarActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }
            });
        }
    }

    @OnClick({R.id.fra_turn_ll_before, R.id.fra_turn_ll_login, R.id.fra_turn_ll_police, R.id.fra_turn_ll_supply, R.id.fra_turn_ll_driving, R.id.fra_turn_ll_charging, R.id.fra_turn_ll_after})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.fra_turn_ll_before:
                toIntent(1, 0);
                break;
            case R.id.fra_turn_ll_login:
                toIntent(1, 1);
                break;
            case R.id.fra_turn_ll_police:
                toIntent(1, 2);
                break;
            case R.id.fra_turn_ll_supply:
                toIntent(1, 3);
                break;
            case R.id.fra_turn_ll_driving:
                toIntent(2, 4);
                break;
            case R.id.fra_turn_ll_charging:
                toIntent(3, 5);
                break;
            case R.id.fra_turn_ll_after:
                    getData(FinishCheckRequest.TYPE_TURN_OUT);
                break;
            default:
                break;

        }
    }



    @Override
    public void onResume() {
        super.onResume();
        checkStepResult();
    }

    private void checkStepResult() {
        StepResultRequest resultRequest = new StepResultRequest(getActivity(), NetHelper.URL_STEP_RESULT, 2);
        resultRequest.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                OneStepResultHelper helper = (OneStepResultHelper) object;
                if (helper != null) {
                    listResult = helper.getStepState();
                    if (listResult == null) {
                        listResult = new ArrayList<Integer>();
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                        listResult.add(2);
                    }
                    initUI();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(3, resultRequest);
    }

    private void initUI() {
        judgeUI(0, tvBeforeStatus);
        judgeUI(3, tvLoginStatus);
        judgeUI(4, tvPoliceStatus);
        judgeUI(5, tvSupplyStatus);
        judgeUI(6, tvDrivingStatus);
        judgeUI(7, tvChargingStatus);
        judgeUI(8, tvAfterStatus);
    }
    private void judgeUI(int position, TextView textView) {
        int status = listResult.get(position);
        switch (status) {
            case 0:
                textView.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                textView.setText("已通过");
                break;
            case 1:
                textView.setTextColor(getResources().getColor(R.color.color_red));
                textView.setText("测试失败");
                break;
            case 2:
                textView.setTextColor(getResources().getColor(R.color.color_white));
                textView.setText("未测试");
                break;
        }
    }


    private boolean isOver=false;
    private void overTest() {
        StepRequest request = new StepRequest(getActivity(), NetHelper.URL_STEP, StatusFactory.STEP_PLATFORM, null, DateUtil.getCurrentTime(true, new Date()));
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                checkStepResult();
                StepResultHelper helper= (StepResultHelper) object;
                if (helper!=null){
                    String strResult= helper.getResult();
                    isOver= helper.isOver();
                    if ("检测成功".equals(strResult)){
                        dialog.success("平台登出", "平台登出成功");
                    }else {
                        dialog.fail("平台登出", "平台登出失败");
                    }
                }else {
                    dialog.fail("平台登出", "平台登出失败");
                }
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(1, request);
    }

    private void toIntent(int a, int b) {
        if (a==1&&b==0){
            MyApplication.get().setDirectly(false);
            Intent i = new Intent(getActivity(), TestingActivity.class);
            i.putExtra(ConstantUtil.BUNDLE_KEY_INT, a);
            i.putExtra("test", b);
            startActivity(i);
        }else {
            if (listResult.get(0)==0){
                MyApplication.get().setDirectly(false);
                Intent i = new Intent(getActivity(), TestingActivity.class);
                i.putExtra(ConstantUtil.BUNDLE_KEY_INT, a);
                i.putExtra("test", b);
                startActivity(i);
            }else {
                ViewInjectUtil.toast("请先完成平台登入");
            }
        }


    }

    private void getData(final int type) {
        FinishCheckRequest request = new FinishCheckRequest(getActivity(), NetHelper.URL_FINISH_CHECK, type);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                switch (type) {
                    case FinishCheckRequest.TYPE_TURN_OUT:   //判断是否可以点击平台登出
                        CheckHelper  helper = (CheckHelper) object;
                        if (helper.isView()) {
                            overTest();
                        } else {
                            ViewInjectUtil.toast("请先完成所有测试");
                        }
                        break;
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, request);
    }


}
