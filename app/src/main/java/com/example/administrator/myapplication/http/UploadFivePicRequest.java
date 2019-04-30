package com.example.administrator.myapplication.http;

import android.content.Context;
import android.util.SparseArray;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.common.net.BaseRequest;

import java.io.File;

/**
 * 上传5张图片
 */
public class UploadFivePicRequest extends BaseRequest{
    public UploadFivePicRequest(Context context, String url,SparseArray<File> list) {
        super(context, url);
        add("vin", MyApplication.get().getVin());
        add("file",list.get(1));
        add("file1",list.get(2));
        add("file2",list.get(3));
        add("file3",list.get(4));
        add("file4",list.get(5));
        add("type","1,2,3,4,5");
    }

    @Override
    protected Object parse() {
        return  null;
    }
}
