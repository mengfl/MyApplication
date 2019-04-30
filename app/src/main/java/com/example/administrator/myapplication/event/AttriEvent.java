package com.example.administrator.myapplication.event;

import com.example.administrator.myapplication.bean.PushMessage;

/**
 * Created by Administrator on 2017/7/28.
 */

public class AttriEvent {
    private PushMessage pushMessage;


    public AttriEvent(PushMessage pushMessage) {
        this.pushMessage = pushMessage;
    }

    public PushMessage getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(PushMessage pushMessage) {
        this.pushMessage = pushMessage;
    }
}
