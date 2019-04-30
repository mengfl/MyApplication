package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.bean.helper.CheckHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.ui.BaseFragment;

/**
 * Created by Administrator on 2017/8/16.
 */

public class FinishCheckRequest extends BaseRequest{
    public static final int TYPE_DIRECTLY=1;  //查平台转发是否完成
    public static final int TYPE_TURN=2;   //查终端直连是否完成
    public static final int TYPE_TURN_OUT=3;  //能否点击平台登出
    public FinishCheckRequest(Context context, String url,int type) {
        super(context, url);
        add("id", MyApplication.get().getCarId());
        add("type",type);
    }

    @Override
    protected Object parse() {

        return  fromJson(result, CheckHelper.class).getResults();
    }
}
