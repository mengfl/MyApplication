package com.example.administrator.myapplication.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.PrepareTestActivity;
import com.example.administrator.myapplication.activity.TestingActivity;
import com.example.administrator.myapplication.bean.helper.OneStepResultHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.http.StepResultRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 终端直连
 */

public class DirectlyFragment extends BaseFragment {

    @BindView(R.id.fra_directly_ll_login)
    LinearLayout llLogin;
    @BindView(R.id.fra_directly_ll_police)
    LinearLayout llPolice;
    @BindView(R.id.fra_directly_ll_supply)
    LinearLayout llSupply;
    @BindView(R.id.fra_directly_ll_driving)
    LinearLayout llDriving;
    @BindView(R.id.fra_directly_ll_charging)
    LinearLayout llCharging;
    @BindView(R.id.fra_directly_tv_loginstatus)
    TextView tvLoginStatus;
    @BindView(R.id.fra_directly_tv_policestatus)
    TextView tvPoliceStatus;
    @BindView(R.id.fra_directly_tv_supplystatus)
    TextView tvSupplyStatus;
    @BindView(R.id.fra_directly_tv_drivingstatus)
    TextView tvDrivingStatus;
    @BindView(R.id.fra_directly_tv_chargingstatus)
    TextView tvChargingStatus;
    private List<Integer> listResult;


    @Override
    public int layoutResourceID() {
        return R.layout.fragment_directly;
    }

    @OnClick({R.id.fra_directly_ll_login, R.id.fra_directly_ll_police, R.id.fra_directly_ll_supply, R.id.fra_directly_ll_driving, R.id.fra_directly_ll_charging})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.fra_directly_ll_login:
                toIntent(1, 1);
                break;
            case R.id.fra_directly_ll_police:
                toIntent(1, 2);
                break;
            case R.id.fra_directly_ll_supply:
                toIntent(1, 3);
                break;
            case R.id.fra_directly_ll_driving:
                toIntent(2, 4);
                break;
            case R.id.fra_directly_ll_charging:
                toIntent(3, 5);
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
        StepResultRequest resultRequest = new StepResultRequest(getActivity(), NetHelper.URL_STEP_RESULT, 1);
        resultRequest.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                OneStepResultHelper helper = (OneStepResultHelper) object;
                if (helper != null) {
                    listResult = helper.getStepState();
                    if (listResult == null) {
                        listResult = new ArrayList<Integer>();
                        listResult.add(0);
                        listResult.add(0);
                        listResult.add(0);
                        listResult.add(0);
                        listResult.add(0);
                        listResult.add(0);
                        listResult.add(0);
                    }
                    initUI();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, resultRequest);
    }

    private void initUI() {
        judgeUI(2, tvLoginStatus);
        judgeUI(3, tvPoliceStatus);
        judgeUI(4, tvSupplyStatus);
        judgeUI(5, tvDrivingStatus);
        judgeUI(6, tvChargingStatus);
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

    private void toIntent(int a, int b) {
        MyApplication.get().setDirectly(true);
        Intent i = new Intent(getActivity(), TestingActivity.class);
        i.putExtra(ConstantUtil.BUNDLE_KEY_INT, a);
        i.putExtra("test", b);
        startActivity(i);

    }
}
