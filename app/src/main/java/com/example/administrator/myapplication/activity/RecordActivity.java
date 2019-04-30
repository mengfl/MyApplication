package com.example.administrator.myapplication.activity;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.fragment.record.DirectlyRecordFragment;
import com.example.administrator.myapplication.fragment.record.TurnRecordFragment;
import com.example.administrator.myapplication.fragment.test.DirectlyFragment;
import com.example.administrator.myapplication.fragment.test.TurnFragment;

import butterknife.BindView;

/**
 * 测试记录2
 */
public class RecordActivity extends AbsBaseTitleActivity {

    @BindView(R.id.act_record_rb_directly)
    RadioButton rbDirectly;
    @BindView(R.id.act_record_rb_turn)
    RadioButton rbTurn;
    @BindView(R.id.act_record_rg)
    RadioGroup mRg;
    @BindView(R.id.act_record_fl)
    FrameLayout mFl;



    @Override
    public int layoutResourceID() {

        return  R.layout.activity_record;
    }

    @Override
    protected int fragmentLayoutId() {
        return R.id.act_record_fl;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("测试记录");
    }

    @Override
    protected void setListener() {
        super.setListener();
        startFragment(DirectlyRecordFragment.class);
        mRg.check(R.id.act_record_rb_directly);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.act_record_rb_directly :
                        MyApplication.get().setDirectly(true);
                        startFragment(DirectlyRecordFragment.class);
                        break;
                    case R.id.act_record_rb_turn  :
                        MyApplication.get().setDirectly(false);
                        startFragment(TurnRecordFragment.class);
                        break;
                    default:
                        break;
                }
            }
        });


    }
}
