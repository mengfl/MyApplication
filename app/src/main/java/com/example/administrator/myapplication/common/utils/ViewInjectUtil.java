package com.example.administrator.myapplication.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 侵入式View的调用工具类
 */
public class ViewInjectUtil {

    private ViewInjectUtil() {
    }

    private static class ClassHolder {
        private static final ViewInjectUtil instance = new ViewInjectUtil();
    }

    /**
     * 类对象创建方法
     *
     * @return 本类的对象
     */
    public static ViewInjectUtil create() {
        return ClassHolder.instance;
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    public static void toast(String msg) {
        try {
            toast(ActivityUtil.create().topActivity(), msg);
        } catch (Exception e) {
        }
    }

    /**
     * 长时间显示一个toast
     *
     * @param msg
     */
    public static void longToast(String msg) {
        try {
            longToast(ActivityUtil.create().topActivity(), msg);
        } catch (Exception e) {
        }
    }

    /**
     * 长时间显示一个toast
     *
     * @param msg
     */
    private static void longToast(Context context, String msg) {
       Toast toast= Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 显示一个toast
     *
     * @param msg
     */
    private static void toast(Context context, String msg) {
        Toast toast= Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }


    private Toast mToast;
//    private ViewInjectUtil(Context context, CharSequence text, int duration) {
//        View v = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
//        ((TextView)v.findViewById(R.id.toast_tv_sign)).setText(text);
//        mToast = new Toast(context);
//        mToast.setDuration(duration);
//        mToast.setGravity(Gravity.CENTER,0,0);
//        mToast.setView(v);
//    }

//    public static ViewInjectUtil makeText(Context context, CharSequence text, int duration) {
//        return new ViewInjectUtil(context, text, duration);
//    }
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
