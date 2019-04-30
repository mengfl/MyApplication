package com.example.administrator.myapplication.common.net;

/**
 *  自定义回调结果
 */

public interface MyCallBack  {
    void onSuccess(int what, Object object);
    void onFailed();
}
