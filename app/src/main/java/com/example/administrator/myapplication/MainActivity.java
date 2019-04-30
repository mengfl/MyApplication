package com.example.administrator.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myapplication.activity.LoginActivity;
import com.example.administrator.myapplication.common.ui.AbsBaseActivity;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.dialog.TipsDialog;
import com.example.administrator.myapplication.event.AttriEvent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AbsBaseActivity {

    @BindView(R.id.act_main_et_name)
    EditText etName;
    @BindView(R.id.act_main_et_pwd)
    EditText etPwd;
    @BindView(R.id.act_main_btn_login)
    Button btnLogin;
    @BindView(R.id.act_main_btn_intent)
    Button btnIntent;
    @BindView(R.id.act_main_btn_open)
    Button btnOpen;
    @BindView(R.id.act_main_tv_msg)
    TextView tvMsg;
    @BindView(R.id.act_main_tv_json)
    TextView tvJson;
    private TipsDialog dialog;
    @Override
    public int layoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @OnClick({R.id.act_main_btn_login, R.id.act_main_btn_open, R.id.act_main_btn_intent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_main_btn_login:
                showActivity(MainActivity.this, LoginActivity.class);
                break;
            case R.id.act_main_btn_intent:

                break;
            case R.id.act_main_btn_open:
                JPushInterface.resumePush(this);
                break;

        }
    }

    @Override
    protected void initView() {
        super.initView();
        dialog=new TipsDialog(this);
        dialog.fail("按时间大洛杉矶德拉科数据啊");
        dialog.show();
    }
    private Subscription subscription;

    @Override
    protected void setListener() {
        super.setListener();
//        subscription = RxBus.getDefault().toObservable(AttriEvent.class).subscribe(new Action1<AttriEvent>() {
//            @Override
//            public void call(AttriEvent attriEvent) {
//                tvMsg.setText("收到消息："+attriEvent.getMessage());
//
//            }
//        });
    }
}
