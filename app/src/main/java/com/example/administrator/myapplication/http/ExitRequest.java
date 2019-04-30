package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.bean.UserInfo;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.utils.SystemUtil;

/**
 * 登录request
 */
public class ExitRequest extends BaseRequest {
    public ExitRequest(Context context, String url) {
        super(context, url);
        add("iccid", SystemUtil.getICCID(context));
    }

    @Override
    protected Object parse() {
        return null;
    }
}
