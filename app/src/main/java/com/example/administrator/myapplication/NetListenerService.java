package com.example.administrator.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.myapplication.activity.LoginActivity;
import com.example.administrator.myapplication.common.net.CallServer;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.SystemUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.event.NetListenerEvent;
import com.example.administrator.myapplication.event.ServerListenEvent;
import com.example.administrator.myapplication.factory.CountFactory;
import com.example.administrator.myapplication.http.ConnectServerRequest;

import rx.Subscription;
import rx.functions.Action1;

public class NetListenerService extends Service {
    private CountFactory countFactory;
    private int count=0;
    private Subscription subscription;
    @Override
    public void onCreate() {
        super.onCreate();
        serverListener();
        LogerUtil.error("NetListenerService", "onCreate");
        countFactory=new CountFactory(1000*60*60);
        countFactory.setOnCountDownListener(new CountFactory.OnCountDownListener() {
            @Override
            public void onProgress(long millisUntilFinished) {
                count++;
                if (count==60*2){
                    count=0;
                    connectServer();
                }
                boolean f=SystemUtil.checkNet(NetListenerService.this);
                if (!f){
                    RxBus.getDefault().post(new NetListenerEvent());
                    stopSelf();
                }

            }

            @Override
            public void onFinish() {
                countFactory.cancel();
            }
        });
        countFactory.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogerUtil.error("NetListenerService","onDestroy");
        countFactory.cancel();
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    private void connectServer(){
        ConnectServerRequest request=new ConnectServerRequest(this, NetHelper.URL_CONNECT_SERVER);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {

            }

            @Override
            public void onFailed() {

            }
        });
        request.setCancelSign(request.getSign());
        CallServer.getInstance().add(0, request);
    }
    private void serverListener(){
        subscription=RxBus.getDefault().toObservable(ServerListenEvent.class).subscribe(new Action1<ServerListenEvent>() {
            @Override
            public void call(ServerListenEvent serverListenEvent) {
                  if (serverListenEvent.isDisconnect()){
                      Intent intent=new Intent(NetListenerService.this, LoginActivity.class);
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      startActivity(intent);
                      stopSelf();
                      ViewInjectUtil.longToast("长时间未操作，您已掉线，请重新登录");
                  }
            }
        });
    }
}
