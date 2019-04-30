package com.example.administrator.myapplication.common.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.Toast;


import com.example.administrator.myapplication.MyApplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final boolean OPEN_DEAL=false;

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static MyUncaughtExceptionHandler instance;

    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new Hashtable<String, String>();
    private MyApplication application;
    private MyUncaughtExceptionHandler() {

    }

    public static MyUncaughtExceptionHandler getInstance() {
        if(instance == null) {
            instance = new MyUncaughtExceptionHandler();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        application=(MyApplication) context.getApplicationContext();
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该AppErrorCatchHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();

        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            Toast.makeText(mContext,"程序出现错误，将在3秒后关闭", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            Intent mNotificationIntent = new Intent(application.getApplicationContext(), WelcomeActivity.class);
//            mNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent mContentIntent = PendingIntent.getActivity(application.getApplicationContext(),
//                    0, mNotificationIntent, 0);
//
//            //退出程序
//            AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 0,
//                    mContentIntent);

            ActivityUtil.create().appExit(application);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
      if (OPEN_DEAL){
          new Thread() {
              @Override
              public void run() {
                  Looper.prepare();

                  String errInfo = getErrorInfo(ex);

                  LogerUtil.writeExceptionLog(errInfo);
                  Looper.loop();
              }
          }.start();

      }
        return true;

    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }

            DisplayMetrics dm = context.getResources().getDisplayMetrics();

            if(dm != null) {
                infos.put("densityDpi", String.valueOf(dm.densityDpi));
                infos.put("widthPixels", String.valueOf(dm.widthPixels));
                infos.put("heightPixels", String.valueOf(dm.heightPixels));
            }
        } catch (NameNotFoundException e) {}

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
    }

    private String getErrorInfo(Throwable ex) {
        // 收集设备参数信息
        collectDeviceInfo(mContext);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(key.equals("TIME")) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:ms");
                    sb.append(key + " : " + format.format(new Date(Long.parseLong(value))) + "\n");
                }
                catch(Exception e) {
                    sb.append(key + " : " + value + "\n");
                }
            }
            else {
                sb.append(key + " : " + value + "\n");
            }
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();

        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        String result = writer.toString();
        sb.append(result);
         sb.append("\n").append("===================================" +
                 "============================================================");
        return sb.toString();
    }

}
