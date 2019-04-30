package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.utils.SystemUtil;

/**
 * 登录request
 */
public class BindCarRequest extends BaseRequest {
    public BindCarRequest(Context context, String url) {
        super(context, url);
        add("carId", MyApplication.get().getCarId());
        add("iccid", SystemUtil.getICCID(context));
    }

    @Override
    protected Object parse() {
        return null;
    }
}
