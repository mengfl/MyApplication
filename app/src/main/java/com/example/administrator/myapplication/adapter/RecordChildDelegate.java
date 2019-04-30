package com.example.administrator.myapplication.adapter;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.common.recyclerview.ItemViewDelegate;
import com.example.administrator.myapplication.common.recyclerview.ViewHolder;

/**
 * Created by Administrator on 2017/7/17.
 */

public class RecordChildDelegate implements ItemViewDelegate<Record> {
    private Context mContext;
    public RecordChildDelegate(Context context) {
        this.mContext=context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recordchild;
    }

    @Override
    public boolean isForViewType(Record item, int position) {
        return item.getViewType() == Record.TYPE_CHILD;
    }

    @Override
    public void convert(ViewHolder holder, Record record, int position) {
        holder.setText(R.id.recordchild_tv_name, record.getName());
        holder.setText(R.id.recordchild_tv_begintime, "开始时长：" + record.getStepStartDate());
        holder.setText(R.id.recordchild_tv_overtime, "结束时长：" + record.getStepEndDate());
        if (TextUtils.isEmpty(record.getStepStartDate()) || TextUtils.isEmpty(record.getStepEndDate())) {
            holder.setText(R.id.recordchild_tv_status, "未完成");
        } else {
            holder.setText(R.id.recordchild_tv_status, "已完成");
        }
        if ("6".equals(record.getCurrentStep())||
                "7".equals(record.getCurrentStep())||
                "14".equals(record.getCurrentStep())||
                "15".equals(record.getCurrentStep())){
            holder.setText(R.id.recordchild_tv_result,"");
        }else {
            if ("0".equals(record.getResult())) {
                holder.setTextColor(R.id.recordchild_tv_result,mContext.getResources().getColor(R.color.color_a2f1ff) );
                holder.setText(R.id.recordchild_tv_result, "成功");
            } else if("1".equals(record.getResult())){
                holder.setTextColor(R.id.recordchild_tv_result,mContext.getResources().getColor(R.color.color_red) );
                holder.setText(R.id.recordchild_tv_result, "失败");
            }else {
                holder.setText(R.id.recordchild_tv_result,"");
            }
        }



    }
}
