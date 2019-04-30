package com.example.administrator.myapplication.wigdet;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class TestStepView extends LinearLayout {
    @BindView(R.id.teststep_img_zonghe)
    ImageView imgZonghe;
    @BindView(R.id.teststep_img_line1)
    ImageView imgLine1;
    @BindView(R.id.teststep_img_driving)
    ImageView imgDriving;
    @BindView(R.id.teststep_img_line2)
    ImageView imgLine2;
    @BindView(R.id.teststep_img_charge)
    ImageView imgCharge;
    @BindView(R.id.teststep_tv_zonghe)
    TextView tvZonghe;
    @BindView(R.id.teststep_tv_driving)
    TextView tvDriving;
    @BindView(R.id.teststep_tv_charge)
    TextView tvCharge;
    private Context mContext;

    public TestStepView(Context context) {
        this(context, null);
    }

    public TestStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_teststep, this);
        ButterKnife.bind(this);
    }

    public void setStep(int positioin){
        switch (positioin){
            case 0:
                imgZonghe.setImageResource(R.drawable.icon_testing_press);
                imgDriving.setImageResource(R.drawable.icon_nottest_normal);
                imgCharge.setImageResource(R.drawable.icon_nottest_normal);
                imgLine1.setImageResource(R.drawable.line_normal);
                imgLine2.setImageResource(R.drawable.line_normal);
                tvZonghe.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                tvDriving.setTextColor(getResources().getColor(R.color.color_fe));
                tvCharge.setTextColor(getResources().getColor(R.color.color_fe));
                break;
            case 1:
                imgZonghe.setImageResource(R.drawable.icon_tested_press);
                imgDriving.setImageResource(R.drawable.icon_testing_press);
                imgCharge.setImageResource(R.drawable.icon_nottest_normal);
                imgLine1.setImageResource(R.drawable.line_press);
                imgLine2.setImageResource(R.drawable.line_normal);
                tvZonghe.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                tvDriving.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                tvCharge.setTextColor(getResources().getColor(R.color.color_fe));
                break;
            case 2:
                imgZonghe.setImageResource(R.drawable.icon_tested_press);
                imgDriving.setImageResource(R.drawable.icon_tested_press);
                imgCharge.setImageResource(R.drawable.icon_testing_press);
                imgLine1.setImageResource(R.drawable.line_press);
                imgLine2.setImageResource(R.drawable.line_press);
                tvZonghe.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                tvDriving.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                tvCharge.setTextColor(getResources().getColor(R.color.color_a2f1ff));
                break;
        }
    }

}
