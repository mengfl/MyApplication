package com.example.administrator.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.administrator.myapplication.bean.PushMessage;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.event.AttriEvent;
import com.example.administrator.myapplication.event.LoginOutEvent;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/28.
 */

public class MyPushReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_EXTRA);
           if (!TextUtils.isEmpty(title)) {
               LogerUtil.error("收到的推送消息",title);
              PushMessage pushMessage = new Gson().fromJson(title, PushMessage.class);
               if (pushMessage!=null){
                   if (TextUtils.isEmpty(pushMessage.getType())){
                       RxBus.getDefault().post(new AttriEvent(pushMessage));
                   }else {
                       RxBus.getDefault().post(new LoginOutEvent(pushMessage.isSuccess()));
                   }
               }

           }
    }


}
