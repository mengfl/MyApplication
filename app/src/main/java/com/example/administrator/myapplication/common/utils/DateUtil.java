package com.example.administrator.myapplication.common.utils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/3/23.
 */

public class DateUtil {
    /**
     * 指定格式返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 返回当前系统时间(格式以HH:mm形式)
     */
    public static String getDataTime() {
        return getDataTime("HH:mm");
    }


    /**
     * 将Long 类型时间转成指定格式
     * @param t   数字
     * @param format  要转成的格式
     * @return
     */
    public  static String LongToDate(String t, String format){
        if (t==null){
            return null;
        }
        long l = Long.parseLong(t);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String s1 = simpleDateFormat.format(new Date(l));
        return s1;
    }

    /* 将字符串转为时间戳 */
    public static long getStringToLong(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 修改时区
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date();
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    /**
     * 获取当前时间
     * @param isDetail   是否需要显示时分秒
     * @return
     */
    public static String getCurrentTime(boolean isDetail, Date date){
        SimpleDateFormat format=null;
        if (isDetail) {
            format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else {
            format=new SimpleDateFormat("yyyy-MM-dd");
        }
        String time=format.format(date);
        return time;
    }


    /**
     * 获取几天前或几天后的月份日期
     * @return
     */
    public static String getOneTime(int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, day);
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");
        return df.format(date);
    }

    /**
     * 分钟转小时
     * @param minutes
     * @return
     */
    public static String minToHours(int minutes){
          int hours=minutes/60;
          int aaa=minutes%60;
          if (hours>0){
              return hours+"时"+aaa+"分";
          }else {
              return aaa+"分";
          }
    }
}
