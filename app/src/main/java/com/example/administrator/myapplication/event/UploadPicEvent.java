package com.example.administrator.myapplication.event;

/**
 * Created by Administrator on 2017/8/14.
 */

public class UploadPicEvent {
    public static  final int TYPE_POLICE=1;
    public static  final int TYPE_CHARGE=2;
    public static  final int TYPE_DRIVING=3;

    private int type=-1;

    private boolean isSuccess;

    public UploadPicEvent(int type, boolean isSuccess) {
        this.type = type;
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
