package com.example.administrator.myapplication.http;

import android.content.Context;


import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.common.net.BaseRequest;

import java.io.File;

/**
 * 上传文件
 */
public class UploadOnePicRequest extends BaseRequest{
    public UploadOnePicRequest(Context context, String url,File file) {
        super(context, url);
        add("vin", MyApplication.get().getVin());
        add("file",file);
        add("type",MyApplication.get().getType());
    }

    @Override
    protected Object parse() {
        return  null;
    }
}
