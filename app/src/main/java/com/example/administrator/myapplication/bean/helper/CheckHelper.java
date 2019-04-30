package com.example.administrator.myapplication.bean.helper;

/**
 * Created by Administrator on 2017/8/16.
 */

public class CheckHelper {
    String view;
    String clickStatus;

    public boolean isView() {
         if ("true".equals(view)){
             return  true;
         }
        return false;
    }


    public String getClickStatus() {
        return clickStatus;
    }

    public void setClickStatus(String clickStatus) {
        this.clickStatus = clickStatus;
    }
}
