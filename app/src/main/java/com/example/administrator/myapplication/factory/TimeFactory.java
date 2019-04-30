package com.example.administrator.myapplication.factory;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.administrator.myapplication.common.utils.TimeUtil;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TimeFactory {
    private long defaultTime;
    private long totalTime; //毫秒
    private CountFactory.OnCountDownListener listener;
    public TimeFactory(long time) {
        totalTime=time;
        defaultTime=totalTime;
    }


    Handler mHandler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (totalTime!=0){
                if (listener!=null){
                    totalTime=totalTime-1000;
                    listener.onProgress(totalTime);
                    mHandler.postDelayed(this,1000);
                }
            }else {
                if (listener!=null){
                    listener.onFinish();
                }
            }
        }
    };
    public void start(){
        mHandler.postDelayed(runnable,1000);
    }

    public void cancel(){
        totalTime=defaultTime;
        mHandler.removeCallbacks(runnable);
    }

    public interface  OnCountDownListener{
        void onProgress(long millisUntilFinished);
        void onFinish();
    }
    public void setOnCountDownListener(CountFactory.OnCountDownListener l){
        listener=l;
    }
}
