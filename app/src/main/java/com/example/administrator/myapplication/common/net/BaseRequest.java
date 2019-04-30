package com.example.administrator.myapplication.common.net;


import android.content.Context;
import android.content.Intent;

import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.GsonBuilderUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.PreferenceUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.dialog.WaitDialog;
import com.example.administrator.myapplication.event.NetListenerEvent;
import com.example.administrator.myapplication.event.ServerListenEvent;
import com.google.gson.Gson;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;

/**
 * 请求基类
 */

public abstract class BaseRequest extends RestRequest<String> {
    protected Context context;
    private MyListener listener;
    protected boolean isShowDialog = true;
    protected WaitDialog dialog;
    protected String result;
    private MyCallBack callBack;
    private String sign;  //请求标志，用于取消请求
    protected Gson gson;
    private boolean resultCode;
    private String resultMsg;
    protected  boolean isVerifyLogin=false;
    /**
     * 默认构造函数
     * post方式   不添加用户token
     * @param context
     * @param url
     */
    public BaseRequest(Context context, String url) {
        this(context, url, RequestMethod.POST);
    }

    public BaseRequest(Context context, String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        this.sign = url;
        this.context = context;
        listener = new MyListener();
        dialog = new WaitDialog(context);
        gson = GsonBuilderUtil.create();
    }

    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {

        result = StringRequest.parseResponseString(responseHeaders, responseBody);
        LogerUtil.error("网络结果",result);
        return result;
    }

    public HttpResult fromJson(String json, Class clazz) {
        Type objectType = type(HttpResult.class, clazz);
        return gson.fromJson(json, objectType);
    }


    ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public void parseCodeMsg(int what){
        try {
            LogerUtil.writeExceptionLog("======"+result+"======");
            JSONObject jsonObject=new JSONObject(result);
            resultCode=jsonObject.getBoolean(NetHelper.KEY_CODE);
            resultMsg=jsonObject.getString(NetHelper.KEY_MSG);
            if (resultCode){
                if (callBack != null) {
                    callBack.onSuccess(what, parse());
                }
            }else {
                ViewInjectUtil.toast(resultMsg);
            }
        } catch (JSONException e) {
            if (isVerifyLogin){
                  RxBus.getDefault().post(new ServerListenEvent(true));
            }else {
                ViewInjectUtil.toast("格式异常");
            }
            e.printStackTrace();
        }
    }


    class MyListener implements OnResponseListener<String> {
        @Override
        public void onStart(int what) {
            if (isShowDialog && !dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            parseCodeMsg(what);
        }

        @Override
        public void onFailed(int what, Response<String> response) {

            Exception exception = response.getException();
            if (exception instanceof NetworkError) {
                ViewInjectUtil.toast("连接出错，请检查网络");
            } else if (exception instanceof NotFoundCacheError) {

            } else if (exception instanceof ServerError) {
                ViewInjectUtil.toast("服务异常");
            } else if (exception instanceof StorageReadWriteError) {

            } else if (exception instanceof StorageSpaceNotEnoughError) {

            } else if (exception instanceof TimeoutError) {
                ViewInjectUtil.toast("连接超时");
            } else if (exception instanceof UnKnownHostError) {

            } else if (exception instanceof URLError) {

            }else if(exception instanceof ConnectException){
                ViewInjectUtil.toast("连接出错");
            }else {
                ViewInjectUtil.toast("未知错误");
            }
            if (callBack != null) {
                callBack.onFailed();
            }
        }

        @Override
        public void onFinish(int what) {
            if (isShowDialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    protected abstract Object parse();

    public void setCallBackListener(MyCallBack callBack) {
        this.callBack = callBack;
    }

    public MyListener getListener() {
        return listener;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
