package com.example.administrator.myapplication.event;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ServerListenEvent {
    private boolean isDisconnect;   //是否掉线了

    public ServerListenEvent(boolean isDisconnect) {
        this.isDisconnect = isDisconnect;
    }


    public boolean isDisconnect() {
        return isDisconnect;
    }

    public void setDisconnect(boolean disconnect) {
        isDisconnect = disconnect;
    }
}
