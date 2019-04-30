package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.bean.helper.RecordCarHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RecordCarRequest extends BaseRequest {
    public RecordCarRequest(Context context, String url) {
        super(context, url);
    }

    @Override
    protected Object parse() {
        return fromJson(result, RecordCarHelper.class).getResults();
    }
}
