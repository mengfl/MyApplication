package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.common.net.BaseRequest;

/**
 * 连接服务器  防止掉线
 */

public class ConnectServerRequest extends BaseRequest {
    public ConnectServerRequest(Context context, String url) {
        super(context, url);
        isVerifyLogin=true;
        isShowDialog=false;
    }

    @Override
    protected Object parse() {
        return null;
    }
}
