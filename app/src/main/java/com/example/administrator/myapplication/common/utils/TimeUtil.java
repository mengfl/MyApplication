package com.example.administrator.myapplication.common.utils;

/**
 * Created by Administrator on 2017/7/14.
 */

public class TimeUtil {
    public static String longToTime(long s){
        if (s==0){
            return "00:00";
        }
        String strMin;
        String strSecond;
              long a=s/1000;
              long min=a/60;
              long second=a%60;
            if (min<10){
                strMin="0"+min;
            }else {
                strMin = min + "";
            }

        if (second<10){
            strSecond="0"+second;
        }else {
            strSecond = second + "";
        }
           return  strMin+":"+strSecond;
    }
}
