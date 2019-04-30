package com.example.administrator.myapplication.event;

/**
 * Created by Administrator on 2017/8/16.
 */

public class FinishCheckEvent {
     private boolean isPre;  //是否已经点击了登入

    public FinishCheckEvent(boolean isPre) {
        this.isPre = isPre;
    }

    public boolean isPre() {
        return isPre;
    }

    public void setPre(boolean pre) {
        isPre = pre;
    }
}
