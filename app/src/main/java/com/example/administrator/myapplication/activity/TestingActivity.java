package com.example.administrator.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.NetListenerService;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.PushMessage;
import com.example.administrator.myapplication.common.stepView.StepView;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.dialog.ExitCheckDialog;
import com.example.administrator.myapplication.event.AttriEvent;
import com.example.administrator.myapplication.event.LoginOutEvent;
import com.example.administrator.myapplication.event.NetListenerEvent;
import com.example.administrator.myapplication.fragment.ChargeFragment;
import com.example.administrator.myapplication.fragment.DrivingFragment;
import com.example.administrator.myapplication.fragment.InOutFragment;
import com.example.administrator.myapplication.fragment.InOutFragment2;
import com.example.administrator.myapplication.fragment.InOutFragment3;
import com.example.administrator.myapplication.fragment.PlatLoginFragment;
import com.example.administrator.myapplication.fragment.PoliceFragment;
import com.example.administrator.myapplication.fragment.SupplyFragment;
import com.example.administrator.myapplication.wigdet.CarAttriView;
import com.example.administrator.myapplication.wigdet.TestStepView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 所有测试过程activity
 */
public class TestingActivity extends AbsBaseTitleActivity {

    @BindView(R.id.act_testing_teststepview)
    TestStepView mStepview;
    @BindView(R.id.act_testing_carattriview)
    CarAttriView mCarattriview;
    @BindView(R.id.act_testing_fl)
    FrameLayout mFl;
    private String[] titles = {"综合阶段", "行驶测试", "充电测试"};

    private int phase;//判断是哪个大阶段
    private int test;//判断是哪项测试
    PowerManager.WakeLock mWakeLock;
    private Subscription subscription;
    private Gson gson;
    private PushMessage pushMessage;
    //    private Subscription subLoginOut;
    private Subscription subNet;
    private ExitCheckDialog netDialog;

    @Override
    public int layoutResourceID() {
        return R.layout.activity_testing;
    }

    @Override
    protected int fragmentLayoutId() {
        return R.id.act_testing_fl;
    }

    @Override
    protected void initView() {
        super.initView();
        initNetDialog();
        startService();
        gson = new Gson();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "");
        phase = getIntent().getIntExtra(ConstantUtil.BUNDLE_KEY_INT, -1);
        test = getIntent().getIntExtra("test", -1);
        initUI();
    }

    private void initNetDialog() {
        netDialog = new ExitCheckDialog(this);
        netDialog.setOnTipsButtonClick(new ExitCheckDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                finish();
            }
        });
        netDialog.netDisConnect();
    }


    private void startService() {
        Intent intent = new Intent(this, NetListenerService.class);
        startService(intent);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        mWakeLock.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    private boolean isNetError = true;

    @Override
    protected void setListener() {
        super.setListener();
        subscription = RxBus.getDefault().toObservable(AttriEvent.class).subscribe(new Action1<AttriEvent>() {
            @Override
            public void call(AttriEvent attriEvent) {
                mCarattriview.setData(attriEvent.getPushMessage());
            }
        });
        subNet = RxBus.getDefault().toObservable(NetListenerEvent.class).subscribe(new Action1<NetListenerEvent>() {
            @Override
            public void call(NetListenerEvent attriEvent) {
                if (isNetError) {
                    isNetError = false;
                    if (!netDialog.isShowing()) {
                        netDialog.show();
                    }
                }
            }
        });
    }

    private void initUI() {
        switch (phase) {
            case 1:
                mStepview.setStep(0);
                break;
            case 2:
                mStepview.setStep(1);
                break;
            case 3:
                mStepview.setStep(2);
                break;
            default:
                break;
        }
        switch (test) {
            case 0:
                startFragment(PlatLoginFragment.class);
                break;
            case 1:
                startFragment(InOutFragment.class);
                break;
            case 2:
                if (MyApplication.get().isDirectly()) {
                    MyApplication.get().setType(7);
                } else {
                    MyApplication.get().setType(10);
                }
                startFragment(PoliceFragment.class);
                break;
            case 3:

                startFragment(SupplyFragment.class);
                break;
            case 4:
                if (MyApplication.get().isDirectly()) {
                    MyApplication.get().setType(6);
                } else {
                    MyApplication.get().setType(9);
                }
                startFragment(DrivingFragment.class);
                break;
            case 5:
                if (MyApplication.get().isDirectly()) {
                    MyApplication.get().setType(8);
                } else {
                    MyApplication.get().setType(11);
                }
                startFragment(ChargeFragment.class);
                break;
            default:
                break;
        }
    }


    @Override
    protected void initTitle() {
        super.initTitle();
        String carBrand = MyApplication.get().getCarBrand();
        if (TextUtils.isEmpty(carBrand)) {
            tvTitle.setText("阶段测试");
        } else {
            tvTitle.setText(carBrand);
        }

        viewWhole.setBackgroundColor(getResources().getColor(R.color.color_193856));
        viewRight.setVisibility(View.GONE);
        viewLeft.setVisibility(View.GONE);
    }

    @Override
    protected void leftClick() {
        skipActivity(this, PersonActivity.class);
    }

    @Override
    protected void rightClick() {
        super.rightClick();

        skipActivity(this, TestMethodActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        JPushInterface.resumePush(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JPushInterface.stopPush(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(TestingActivity.this, NetListenerService.class);
        stopService(i);
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if (subNet != null && !subNet.isUnsubscribed()) {
            subNet.unsubscribe();
        }
    }
}
