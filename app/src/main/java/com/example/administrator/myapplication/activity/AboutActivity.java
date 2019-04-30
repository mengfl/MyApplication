package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.SystemUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AbsBaseTitleActivity {

    @BindView(R.id.act_about_img)
    ImageView mImg;
    @BindView(R.id.act_about_tv)
    TextView mTv;

    @Override
    public int layoutResourceID() {
        return R.layout.activity_about;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("关于");
    }

    @Override
    protected void initView() {
        super.initView();
        mTv.setText(SystemUtil.getAppVersionName(this));
    }
}
