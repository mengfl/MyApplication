package com.example.administrator.myapplication.wigdet;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/17.
 */
public class CarScanView extends LinearLayout {

    @BindView(R.id.carscan_img_bg)
    ImageView imgBg;
    @BindView(R.id.carscan_img_scan)
    ImageView imgScan;
    private Context mContext;

    public CarScanView(Context context) {
        this(context, null);
    }

    public CarScanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CarScanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_carscan, this);
        ButterKnife.bind(this);
        TranslateAnimation animation = new TranslateAnimation(0, 0,0, 800);
        animation.setDuration(3000);//设置动画持续时间
        animation.setRepeatCount(Integer.MAX_VALUE);//设置重复次数
        imgScan.startAnimation(animation);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        imgScan.clearAnimation();
    }
}
