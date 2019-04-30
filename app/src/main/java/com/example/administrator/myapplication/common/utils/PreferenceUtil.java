package com.example.administrator.myapplication.common.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 *  首选项工具  保存信息 到文件fileName中
 * Created by Administrator on 2016/5/14.
 */
public class PreferenceUtil {
    public static void write(Context context, String k, int v) {

        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.commit();
    }

    public static void write(Context context, String k, boolean v) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }

    public static void write(Context context, String k, String v) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(k, v);
        editor.commit();
    }

    public static int readInt(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        return preference.getInt(k, 0);
    }

    public static int readInt(Context context, String k, int defv) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        return preference.getInt(k, defv);
    }

    public static boolean readBoolean(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        return preference.getBoolean(k, false);
    }

    public static boolean readBoolean(Context context, String k, boolean defBool) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        return preference.getBoolean(k, defBool);
    }

    public static String readString(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        return preference.getString(k, (String)null);
    }

    public static String readString(Context context, String k, String defV) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        return preference.getString(k, defV);
    }

    public static void remove(Context context, String k) {
        SharedPreferences preference = context.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(k);
        editor.commit();
    }

    public static void clean(Context cxt) {
        SharedPreferences preference = cxt.getSharedPreferences(ConstantUtil.SOFT_NAME, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.commit();
    }


}
