package com.example.administrator.myapplication.http;

import android.content.Context;

import com.example.administrator.myapplication.bean.Car;
import com.example.administrator.myapplication.bean.UserInfo;
import com.example.administrator.myapplication.bean.helper.CarHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.utils.SystemUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;

import java.util.List;

/**
 * 车辆查询--检测中
 */
public class SearchCheckingCarRequest extends BaseRequest {
    public SearchCheckingCarRequest(Context context, String url) {
        super(context, url);
        add("iccid", SystemUtil.getICCID(context));
    }

    @Override
    protected Object parse() {
        return fromJson(result, CarHelper.class).getResults();
    }
}
