package com.example.administrator.myapplication.common.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public abstract class AbsBaseTitleActivity extends AbsBaseActivity {

    protected TextView tvTitle;

    protected View viewLeft;
    protected ImageView imgLeft;

    protected View viewRight;
    protected ImageView imgRight;
    protected TextView tvRight;
    protected  View viewWhole;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWhole= (View) findViewById(R.id.backtitle_rl_whole);
        tvTitle= (TextView) findViewById(R.id.backtitle_center_tv);

        viewLeft=  findViewById(R.id.backtitle_left_rl);
        imgLeft= (ImageView) findViewById(R.id.backtitle_left_img);
        viewRight=  findViewById(R.id.backtitle_right_rl);
        imgRight= (ImageView) findViewById(R.id.backtitle_right_img);
        tvRight= (TextView) findViewById(R.id.backtitle_right_tv);
        initTitle();

        viewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftClick();
            }
        });
        viewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClick();
            }
        });

    }



    /**
     * 定义标题栏
     */
    protected  void initTitle(){

    }

    protected  void  leftClick(){
        finish();
    }
    protected  void  rightClick(){


    }

    @Override
    public void onBackPressed() {

    }
}
