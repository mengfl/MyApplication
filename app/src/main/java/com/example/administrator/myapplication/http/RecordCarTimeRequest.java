package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.bean.helper.RecordCarHelper;
import com.example.administrator.myapplication.bean.helper.RecordCarTimeHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RecordCarTimeRequest extends BaseRequest {
    public RecordCarTimeRequest(Context context, String url,String carId) {
        super(context, url);
        add("id",carId);
    }

    @Override
    protected Object parse() {
        return fromJson(result, RecordCarTimeHelper.class).getResults();
    }
}
