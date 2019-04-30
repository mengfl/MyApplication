package com.example.administrator.myapplication.bean.helper;

import com.example.administrator.myapplication.bean.Car;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RecordCarHelper {
    private boolean isNewRecord;
    private List<Car> list;

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public List<Car> getList() {
        return list;
    }

    public void setList(List<Car> list) {
        this.list = list;
    }
}
