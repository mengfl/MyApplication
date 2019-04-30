package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.bean.helper.CarHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;

/**
 * 车辆查询--待检测
 */
public class SearchNotCheckCarRequest extends BaseRequest {
    public SearchNotCheckCarRequest(Context context, String url) {
        super(context, url);

    }

    @Override
    protected Object parse() {
        return fromJson(result, CarHelper.class).getResults();
    }
}
