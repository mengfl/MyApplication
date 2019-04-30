package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.stepView.StepView;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.fragment.ChargeFragment;
import com.example.administrator.myapplication.fragment.DrivingFragment;
import com.example.administrator.myapplication.fragment.InOutFragment;
import com.example.administrator.myapplication.fragment.PoliceFragment;
import com.example.administrator.myapplication.fragment.SupplyFragment;
import com.example.administrator.myapplication.wigdet.CarAttriView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测前准备
 */
public class PrepareTestActivity extends AbsBaseTitleActivity {
    @BindView(R.id.act_preparetest_stepview)
    StepView mStepview;
    @BindView(R.id.act_preparetest_carattriview)
    CarAttriView mCarattriview;
    @BindView(R.id.act_preparetest_tv_title)
    TextView tvInfo;
    @BindView(R.id.act_preparetest_tv_content)
    TextView tvContent;
    @BindView(R.id.act_preparetest_tv_time)
    TextView tvTime;
    @BindView(R.id.act_preparetest_img_pic)
    ImageView imgPic;
    private String[] titles = {"综合阶段", "行驶测试", "充电测试"};
    private int phase;//判断是哪个大阶段
    private int test;//判断是哪项测试

    @Override
    public int layoutResourceID() {
        return R.layout.activity_prepare_test;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }


    @Override
    protected void initView() {
        super.initView();
        phase = getIntent().getIntExtra(ConstantUtil.BUNDLE_KEY_INT, -1);
        test = getIntent().getIntExtra("test", -1);
        initStepView();
        initUI();
    }

    private void initStepView() {
        mStepview.setLabels(titles)
                .setProgressColorIndicator(getResources().getColor(R.color.color_a2f1ff))
                .setBarColorIndicator(getResources().getColor(R.color.color_white))
                .setLabelColorIndicator(getResources().getColor(R.color.color_a2f1ff))
                .setCompletedPosition(0);

    }
    private void initUI() {
        tvInfo.setText("测前准备");
        tvContent.setText("测前准备测前准备测前准备测前准备测前准备测前准备测前准备测前准备测前准备");
        switch (phase){
            case  1:
                mStepview.setCompletedPosition(0);
                break;
            case  2:
                mStepview.setCompletedPosition(1);
                break;
            case  3:
                mStepview.setCompletedPosition(2);
                break;
            default:
                break;
        }

    }
    @Override
    protected void initTitle() {
        super.initTitle();
        if ( MyApplication.get().isDirectly()){
            tvTitle.setText("终端直连");
        }else {
            tvTitle.setText("平台转发");
        }
        viewWhole.setBackgroundColor(getResources().getColor(R.color.color_193856));
        viewRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.icon_drawer);
        imgRight.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.GONE);

    }


    @OnClick(R.id.act_preparetest_img_pic)
    public void onViewClicked() {
        Intent i = new Intent(this, UploadMoreActivity.class);
        i.putExtra(ConstantUtil.BUNDLE_KEY_INT,phase);
        i.putExtra("test",test);
        startActivity(i);
           finish();
    }

    @Override
    protected void rightClick() {
        super.rightClick();
        skipActivity(this,TestMethodActivity.class);
    }
}
