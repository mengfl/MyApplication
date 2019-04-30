package com.example.administrator.myapplication.common.utils;

import android.content.Context;

import java.io.File;

/**
 * 常量集
 */
public class ConstantUtil {

    public static String PATH_ROOT; // 根目录
    public static String SOFT_NAME = "AppCheck"; // 软件名称
    public static String PATH_LOG; // 日志目录
    public static String PATH_PIC; // 图片目录
    public static String PATH_DB; // 数据库目录
    public static String APK_NAME="CheckApk.apk"; //

    public static String NAME_PIC_POLICE="police.jpg";
    public static String NAME_PIC_CHARGE="charge.jpg";
    public static String NAME_PIC_DRIVING="driving.jpg";
    public static String NAME_PIC1="pic1.jpg";
    public static String NAME_PIC2="pic2.jpg";
    public static String NAME_PIC3="pic3.jpg";
    public static String NAME_PIC4="pic4.jpg";
    public static String NAME_PIC5="pic5.jpg";
    public static long TIME_INOUT=7*60*1000;//登入登出倒计时
    public static long TIME_POLICE=10*60*1000;//报警倒计时
    public static long TIME_SUPPLY=15*60*1000;//补发倒计时
    public static long TIME_DRIVING=30*60*1000;//行驶倒计时
    public static long TIME_CHARGE=15*60*1000;//充电倒计时
    public static long TIME_PLATLOGIN=60*1000;//平台倒计时

//    public static long TIME_INOUT=3*60*1000;//登入登出倒计时
//    public static long TIME_POLICE=3*60*1000;//报警倒计时
//    public static long TIME_SUPPLY=3*60*1000;//补发倒计时
//    public static long TIME_DRIVING=3*60*1000;//行驶倒计时
//    public static long TIME_CHARGE=3*60*1000;//充电倒计时
//    public static long TIME_PLATLOGIN=60*1000;//平台倒计时

    //=======================================intent常量========================================================
    public static final String BUNDLE_KEY_STRING = "bundle_string";
    public static final String BUNDLE_KEY_OBJ = "bundle_obj";
    public static final String BUNDLE_KEY_INT = "bundle_int";
    public static final String BUNDLE_KEY_MACHINE = "bundle_machine";
    public static final String BUNDLE_KEY_BOOLEAN = "bundle_boolean";
    public static final String BUNDLE_KEY_TITLE = "bundle_title";
    public static final String BUNDLE_KEY_LIST = "bundle_list";
    public static final String BUNDLE_KEY_URL = "bundle_url";

    //=======================================preference key========================================================
    public static final String SHARE_KEY_ACCOUNT = "key_account";
    public static final String SHARE_KEY_PWD = "key_pwd";
    public static final String SHARE_KEY_REMEMBER = "key_remember";

    /**
     * 定义软件目录
     *
     * @param context
     */
    public static void init(Context context) {
        PATH_ROOT = FileUtil.getSDCardPath()
                .concat(File.separator).concat(SOFT_NAME);
        PATH_LOG = PATH_ROOT.concat(File.separator).concat("log");
        PATH_DB=PATH_ROOT.concat(File.separator).concat("db");
        PATH_PIC=PATH_ROOT.concat(File.separator).concat("pic");

        File dir = new File(PATH_ROOT);
        if (!dir.exists())
            dir.mkdirs();

        dir = new File(PATH_LOG);
        if (!dir.exists()) dir.mkdirs();

        dir = new File(PATH_DB);
        if (!dir.exists()) dir.mkdirs();
        dir = new File(PATH_PIC);
        if (!dir.exists()) dir.mkdirs();
    }
}
