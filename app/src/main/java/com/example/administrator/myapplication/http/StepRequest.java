package com.example.administrator.myapplication.http;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.bean.helper.StepResultHelper;
import com.example.administrator.myapplication.common.net.BaseRequest;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.utils.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 测试接口
 */
public class StepRequest extends BaseRequest {

    public StepRequest(Context context, String url, int step, String stateDate, String endDate) {
        super(context, url);
        add("step", step);
        add("id", MyApplication.get().getCarId());
        add("stateDate", stateDate);
        add("endDate", endDate);
        if (TextUtils.isEmpty(endDate)){
            add("iccid", SystemUtil.getICCID(context));
        }
    }
    public StepRequest(Context context, String url, int step, String stateDate, String endDate,String platLoginEndTime) {
        super(context, url);
        add("step", step);
        add("id", MyApplication.get().getCarId());
        add("stateDate", stateDate);
        add("endDate", endDate);
        add("platLoginEndDate", platLoginEndTime);

    }
    @Override
    protected Object parse() {
        StepResultHelper helper = null;
        String strResult = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            helper = new StepResultHelper();
            strResult = jsonObject.getString(NetHelper.KEY_RESULTS);
            boolean testOver = jsonObject.getBoolean("endAll");
            helper.setResult(strResult);
            helper.setOver(testOver);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return helper;
    }
}
