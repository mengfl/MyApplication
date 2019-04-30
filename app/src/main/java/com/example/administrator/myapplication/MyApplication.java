package com.example.administrator.myapplication;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.FileUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MyApplication extends Application {
    private static MyApplication app;
    private boolean isDirectly = false;   //是否直连
    private String vin;
    private int type;//上传单张图片类型
    private String carId;
    private String cinId;
    private String carBrand;

    @Override
    public void onCreate() {
        super.onCreate();
        if (app == null) {
            app = this;
        }

        CrashReport.initCrashReport(this,"fe7e34362e",false);
        ConstantUtil.init(this);

        // 高级初始化：
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(MyApplication.this)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this)
                                .setBaseDirectoryPath(new File(FileUtil.getSDCardPath()))
                                .build()
                )
                .build()
        );
        //10秒连接超时， 10秒响应超时 ,okhttp做网络底层
        NoHttp.initialize(this, new NoHttp.Config().setConnectTimeout(30 * 1000).setReadTimeout(30 * 1000)
                .setNetworkExecutor(new OkHttpNetworkExecutor()));
        Logger.setDebug(true); //开始nohttp调试模式
        Logger.setTag("网络调试");
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush


    }

    public static MyApplication get() {
        return app;
    }

    public boolean isDirectly() {
        return isDirectly;
    }

    public void setDirectly(boolean directly) {
        isDirectly = directly;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCinId() {
        return cinId;
    }

    public void setCinId(String cinId) {
        this.cinId = cinId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
}
