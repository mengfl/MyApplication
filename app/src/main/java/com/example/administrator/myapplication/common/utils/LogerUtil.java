package com.example.administrator.myapplication.common.utils;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *  日志工具
 */

public class LogerUtil {
    public static boolean IS_DEBUG = true;
    public static boolean IS_WRITE=true;

    public static final void openDebutLog(boolean enable) {
        IS_DEBUG = enable;
    }

    public static final void error(String key, String value) {
        if (IS_DEBUG) {
            Log.e(key,value);

        }
    }
    public static final void info(String key, String value) {
        if (IS_DEBUG) {
            Log.i(key, value);
        }
    }
    public static final void debug(String key, String value) {
        if (IS_DEBUG) {
            Log.d(key, value);
        }
    }
    public static final void warn(String key, String value) {
        if (IS_DEBUG) {
            Log.w(key, value);
        }
    }

    /**
     * 打印日志，并写入文件中
     * @param key
     * @param value
     */
    public static void printAndWrite(String key, String value){
        error(key,value);
        writeExceptionLog("==="+key+"==="+value);
    }
    /**
     * 记录异常到文件
     * @param exceptionDescription
     */
    public static void writeExceptionLog(String exceptionDescription) {
        if (!IS_WRITE) return;
        //再判断文件是否存在
        File log=new File(ConstantUtil.PATH_LOG.concat(File.separator).concat(DateUtil.getCurrentTime(false,new Date())).concat(".log"));
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                LogerUtil.writeExceptionLog(e.getMessage());
            }
        }
        //写入文件
        if(log.exists())
        {
            try
            {

                FileWriter fw = new FileWriter(log, true);
                fw.write("Time---" +DateUtil.getCurrentTime(true,new Date()) + "\r\n" + exceptionDescription + "\r\n\r\n");
                fw.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                LogerUtil.writeExceptionLog(e.getMessage());
            }
        }
    }
}
