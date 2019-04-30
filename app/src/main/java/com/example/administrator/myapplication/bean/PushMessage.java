package com.example.administrator.myapplication.bean;

/**
 * Created by Administrator on 2017/8/8.
 */

public class PushMessage {
    private String type;
    private String online;
    private String soc;
    private String time;
    private String threeAlarm;
    private boolean isSuccess;

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThreeAlarm() {
        return threeAlarm;
    }

    public void setThreeAlarm(String threeAlarm) {
        this.threeAlarm = threeAlarm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "online='" + online + '\'' +
                ", soc='" + soc + '\'' +
                ", time='" + time + '\'' +
                ", threeAlarm='" + threeAlarm + '\'' +
                '}';
    }
}
