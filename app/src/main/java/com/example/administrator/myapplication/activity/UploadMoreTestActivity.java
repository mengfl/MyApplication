package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MoreTestAdapter;
import com.example.administrator.myapplication.bean.MorePic;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.factory.MyZoomListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadMoreTestActivity extends AbsBaseTitleActivity {


    @BindView(R.id.act_uploadmoretest_recycler)
    RecyclerView mRecycler;
    private MoreTestAdapter mAdapter;
    private List<MorePic> mData;
    @Override
    public int layoutResourceID() {
        return R.layout.activity_upload_more_test;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecycler();
    }

    private void initRecycler() {
        mData = new ArrayList<>();
        MorePic pic=new MorePic();
        pic.setResId(R.drawable.pic_example1);
        pic.setSign("图片1");
        mData.add(pic);

        pic=new MorePic();
        pic.setResId(R.drawable.pic_example2);
        pic.setSign("图片2");
        mData.add(pic);

        pic=new MorePic();
        pic.setResId(R.drawable.pic_example3);
        pic.setSign("图片3");
        mData.add(pic);

        pic=new MorePic();
        pic.setResId(R.drawable.pic_example4);
        pic.setSign("图片4");
        mData.add(pic);

        pic=new MorePic();
        pic.setResId(R.drawable.pic_example5);
        pic.setSign("图片5");
        mData.add(pic);

         mAdapter=new MoreTestAdapter(this,R.layout.item_moretest,mData);
        CarouselLayoutManager manager=new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        manager.setPostLayoutListener(new MyZoomListener());
        manager.setMaxVisibleItems(1);

        mRecycler.setLayoutManager(manager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);
        mRecycler.addOnScrollListener(new CenterScrollListener());
    }
}
