package com.example.administrator.myapplication.adapter;

import android.content.Context;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.MorePic;
import com.example.administrator.myapplication.common.recyclerview.CommonAdapter;
import com.example.administrator.myapplication.common.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class MoreTestAdapter extends CommonAdapter<MorePic> {
    public MoreTestAdapter(Context context, int layoutId, List<MorePic> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MorePic morePic, int position) {
             holder.setText(R.id.moretest_tv_sign,morePic.getSign());
             holder.setImageByRes(R.id.moretest_sdv,morePic.getResId());
    }
}
