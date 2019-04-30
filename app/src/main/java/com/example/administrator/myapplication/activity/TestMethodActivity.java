package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.helper.CheckHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.event.FinishCheckEvent;
import com.example.administrator.myapplication.fragment.test.DirectlyFragment;
import com.example.administrator.myapplication.fragment.test.TurnFragment;
import com.example.administrator.myapplication.http.FinishCheckRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试方式选择
 */
public class TestMethodActivity extends AbsBaseTitleActivity {


    @BindView(R.id.act_testmethod_rb_directly)
    RadioButton rbDirectly;
    @BindView(R.id.act_testmethod_rb_turn)
    RadioButton rbTurn;
    @BindView(R.id.act_testmethod_rg)
    RadioGroup mRg;
    @BindView(R.id.act_testmethod_fl)
    FrameLayout mFl;

    @Override
    public int layoutResourceID() {
        return R.layout.activity_test_method;
    }

    @Override
    protected int fragmentLayoutId() {
        return R.id.act_testmethod_fl;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("选择测试");
        viewRight.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void setListener() {
        super.setListener();
        getData(FinishCheckRequest.TYPE_DIRECTLY);
        rbDirectly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(FinishCheckRequest.TYPE_DIRECTLY);
            }
        });
        rbTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(FinishCheckRequest.TYPE_TURN);
            }
        });
    }

    private void getData(final int type) {
        FinishCheckRequest request = new FinishCheckRequest(this, NetHelper.URL_FINISH_CHECK, type);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                switch (type) {
                    case FinishCheckRequest.TYPE_DIRECTLY://点击终端直连
                        //true  代表平台转发完成  界面留在终端直连   startFragment(DirectlyFragment.class);
                        //false  代表平台转发未完成   界面留在平台转发    startFragment(TurnFragment.class);
                        CheckHelper helper = (CheckHelper) object;
                        if (helper.isView()) {
                            mRg.check(R.id.act_testmethod_rb_directly);
                            startFragment(DirectlyFragment.class);
                        } else {
                            ViewInjectUtil.toast("平台转发测试未结束，不能切换测试方式");
                            mRg.check(R.id.act_testmethod_rb_turn);
                            startFragment(TurnFragment.class);
                            if ("0".equals(helper.getClickStatus())){
                                RxBus.getDefault().post(new FinishCheckEvent(false));
                            }else {
                                //1 已点击登入
                                RxBus.getDefault().post(new FinishCheckEvent(true));
                            }
                        }
                        break;
                    case FinishCheckRequest.TYPE_TURN:  //点击平台转发  多一个字段
                        //true  代表终端直连完成  界面留在平台转发   startFragment(TurnFragment.class);
                        //false 代表终端直连未完成   界面留在终端直连  startFragment(DirectlyFragment.class);
                        helper = (CheckHelper) object;
                        if (helper.isView()) {
                            mRg.check(R.id.act_testmethod_rb_turn);
                            startFragment(TurnFragment.class);
                            if ("0".equals(helper.getClickStatus())){
                                RxBus.getDefault().post(new FinishCheckEvent(false));
                            }else {
                                //1 已点击登入
                                RxBus.getDefault().post(new FinishCheckEvent(true));
                            }
                        } else {
                            ViewInjectUtil.toast("终端直连测试未结束，不能切换测试方式");
                            mRg.check(R.id.act_testmethod_rb_directly);
                            startFragment(DirectlyFragment.class);
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
