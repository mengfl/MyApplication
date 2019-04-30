package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.common.recyclerview.ItemViewDelegate;
import com.example.administrator.myapplication.common.recyclerview.ViewHolder;

import java.util.Scanner;

/**
 * Created by Administrator on 2017/7/17.
 */

public class RecordCarChildDelegate implements ItemViewDelegate<RecordCarAdapter.RecordCarItem> {
    private Context mContext;
    public RecordCarChildDelegate(Context context) {
        this.mContext=context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recordcarchild;
    }

    @Override
    public boolean isForViewType(RecordCarAdapter.RecordCarItem item, int position) {
        return item.getType() == RecordCarAdapter.RecordCarItem.TYPE_CHILD;
    }

    @Override
    public void convert(ViewHolder holder, RecordCarAdapter.RecordCarItem item, int position) {
        holder.setText(R.id.recordcarchild_tv_name,"第"+toChinese(item.getCurrentTime()+"")+"次检测");
        holder.setText(R.id.recordcarchild_tv_time,"检测时间："+item.getDate());
    }

    private String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {
            int num = string.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }

        }
        return result;

    }


}
