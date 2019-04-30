package com.example.administrator.myapplication.factory;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2017/8/4.
 */

public class CountFactory  {

    private long totalTime;
    private boolean isGo=true;
    private OnCountDownListener listener;
    public CountFactory(long time) {
         totalTime=time;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                if (isGo){
                    if (listener!=null){
                        if (msg.what==0){
                            listener.onProgress((Long) msg.obj);
                        }
                        if (msg.what==1){
                            isGo=false;
                            listener.onFinish();
                        }
                    }
                }
        }
    };
    public void start(){
          new Thread(new Runnable() {
              @Override
              public void run() {
                  while (isGo){
                      try {
                          Thread.sleep(1000);
                          totalTime=totalTime-1000;
                          Message message=new Message();
                          if (totalTime>0){
                              message.what=0;
                              message.obj=totalTime;
                          }else {
                              message.what=1;
                          }
                          handler.sendMessage(message);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
              }
          }).start();
    }
    public interface  OnCountDownListener{
        void onProgress(long millisUntilFinished);
        void onFinish();
    }
    public void setOnCountDownListener(OnCountDownListener l){
        listener=l;
    }
    public void cancel(){
        handler.removeMessages(0);
        handler.removeMessages(1);
        isGo=false;

    }
}
