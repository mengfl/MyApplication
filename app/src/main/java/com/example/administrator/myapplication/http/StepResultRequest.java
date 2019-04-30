package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.bean.helper.OneStepResultHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;

/**
 * Created by Administrator on 2017/8/29.
 */

public class StepResultRequest extends BaseRequest {
    public StepResultRequest(Context context, String url,int type) {
        super(context, url);
        add("id", MyApplication.get().getCarId());
        add("type",type);
    }

    @Override
    protected Object parse() {
        return fromJson(result, OneStepResultHelper.class).getResults();
    }
}
