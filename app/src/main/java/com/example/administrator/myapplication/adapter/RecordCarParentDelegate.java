package com.example.administrator.myapplication.adapter;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.common.recyclerview.ItemViewDelegate;
import com.example.administrator.myapplication.common.recyclerview.ViewHolder;

/**
 * Created by Administrator on 2017/7/17.
 */

public class RecordCarParentDelegate implements ItemViewDelegate<RecordCarAdapter.RecordCarItem> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recordcarparent;
    }

    @Override
    public boolean isForViewType(RecordCarAdapter.RecordCarItem item, int position) {
        return item.getType()== RecordCarAdapter.RecordCarItem.TYPE_PARENT;
    }

    @Override
    public void convert(ViewHolder holder, RecordCarAdapter.RecordCarItem item, int position) {
           holder.setText(R.id.recordcarparent_tv_name,item.getName());
           holder.setText(R.id.recordcarparent_tv_times,item.getNum()+"");
    }
}
