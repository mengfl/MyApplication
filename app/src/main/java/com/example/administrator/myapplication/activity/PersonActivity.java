package com.example.administrator.myapplication.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.ActivityUtil;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.PreferenceUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.http.ExitRequest;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class PersonActivity extends AbsBaseTitleActivity {


    @BindView(R.id.act_person_tv_record)
    TextView tvRecord;
    @BindView(R.id.act_person_tv_name)
    TextView tvName;
    @BindView(R.id.act_person_tv_about)
    TextView tvAbout;
    @BindView(R.id.act_person_btn_exit)
    Button btnExit;

    @Override
    public int layoutResourceID() {
        return R.layout.activity_person;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("个人中心");
    }

    @Override
    protected void initView() {
        super.initView();
        String name=getIntent().getStringExtra(ConstantUtil.BUNDLE_KEY_STRING);
        tvName.setText(name);
    }

    @OnClick({R.id.act_person_tv_record, R.id.act_person_tv_about, R.id.act_person_btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_person_tv_record:
                showActivity(this,RecordCarActivity.class);
                break;
            case R.id.act_person_tv_about:
                showActivity(this,AboutActivity.class);
                break;
            case R.id.act_person_btn_exit:
                loginout();
                break;
        }
    }
    private void loginout(){
        ExitRequest request=new ExitRequest(this, NetHelper.URL_EXIT);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                ActivityUtil.create().finishAllActivity();
                skipActivity(PersonActivity.this,LoginActivity.class);
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0,request);
    }



}
