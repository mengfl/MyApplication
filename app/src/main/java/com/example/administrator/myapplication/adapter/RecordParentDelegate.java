package com.example.administrator.myapplication.adapter;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.common.recyclerview.ItemViewDelegate;
import com.example.administrator.myapplication.common.recyclerview.ViewHolder;

/**
 * Created by Administrator on 2017/7/17.
 */

public class RecordParentDelegate implements ItemViewDelegate<Record> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recordparent;
    }

    @Override
    public boolean isForViewType(Record item, int position) {
        return item.getViewType()==Record.TYPE_PARENT;
    }

    @Override
    public void convert(ViewHolder holder, Record record, int position) {
           holder.setText(R.id.recordparent_tv,record.getName());
    }
}
