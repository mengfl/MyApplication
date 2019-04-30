package com.example.administrator.myapplication.bean.helper;

import com.example.administrator.myapplication.bean.RecordCarTime;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RecordCarTimeHelper {

    private boolean isNewRecord;
    private List<RecordCarTime> list;

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public List<RecordCarTime> getList() {
        return list;
    }

    public void setList(List<RecordCarTime> list) {
        this.list = list;
    }
}
