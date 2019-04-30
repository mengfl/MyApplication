package com.example.administrator.myapplication.event;

/**
 * Created by Administrator on 2017/8/14.
 */

public class LoginOutEvent {
     private boolean isOver;  //登出是否结束
    private int positionInOut;


    public LoginOutEvent(int positionInOut) {
        this.positionInOut = positionInOut;
    }


    public LoginOutEvent(boolean isOver) {
        this.isOver = isOver;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public int getPositionInOut() {
        return positionInOut;
    }

    public void setPositionInOut(int positionInOut) {
        this.positionInOut = positionInOut;
    }
}
