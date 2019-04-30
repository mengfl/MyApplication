package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.bean.UserInfo;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.net.HttpResult;

/**
 * 登录request
 */
public class LoginRequest extends BaseRequest {
    public LoginRequest(Context context, String url,String name,String password) {
        super(context, url);
        add("username",name);
        add("password",password);
    }

    @Override
    protected Object parse() {
        return fromJson(result, UserInfo.class).getResults();
    }
}
