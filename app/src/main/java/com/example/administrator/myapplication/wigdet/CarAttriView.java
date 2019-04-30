package com.example.administrator.myapplication.wigdet;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.PushMessage;
import com.example.administrator.myapplication.common.utils.LogerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/14.
 */

public class CarAttriView extends LinearLayout {

    @BindView(R.id.layout_carattri_tv_status)
    TextView tvStatus;
    @BindView(R.id.layout_carattri_tv_soc)
    TextView tvSoc;
    @BindView(R.id.layout_carattri_tv_date)
    TextView tvDate;
    @BindView(R.id.layout_carattri_tv_alarm)
    TextView tvAlarm;
    private Context mContext;

    public CarAttriView(Context context) {
        this(context, null);
    }

    public CarAttriView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CarAttriView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_carattri, this);
        ButterKnife.bind(this);

    }
    public void setData(PushMessage pushMessage){
         if (pushMessage!=null){
             tvStatus.setText("是否上线:"+pushMessage.getOnline());
             tvAlarm.setText("三级报警:"+pushMessage.getThreeAlarm());
             tvDate.setText(pushMessage.getTime());
             tvSoc.setText("SOC:"+pushMessage.getSoc());
             tvDate.setVisibility(VISIBLE);
         }

    }

}
