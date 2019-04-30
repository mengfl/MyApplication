package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.bean.helper.CarHelper;
import com.example.administrator.myapplication.bean.helper.RecordHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.utils.ConstantUtil;

/**
 * 测试记录接口
 */
public class RecordRequest extends BaseRequest {

    public RecordRequest(Context context, String url) {
        super(context, url);
        add("id",MyApplication.get().getCinId());

    }

    @Override
    protected Object parse() {
        return fromJson(result, RecordHelper.class).getResults();
    }
}
