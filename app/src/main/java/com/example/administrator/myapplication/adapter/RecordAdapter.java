package com.example.administrator.myapplication.adapter;

import android.content.Context;

import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.common.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class RecordAdapter  extends  MultiItemTypeAdapter<Record>{
    public RecordAdapter(Context context, List<Record> datas) {
        super(context, datas);
        addItemViewDelegate(Record.TYPE_PARENT,new RecordParentDelegate());
        addItemViewDelegate(Record.TYPE_CHILD,new RecordChildDelegate(mContext));
    }
}
