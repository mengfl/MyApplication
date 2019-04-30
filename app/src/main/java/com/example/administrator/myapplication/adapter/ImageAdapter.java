package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.utils.BitmapUtil;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.FileUtil;
import com.example.administrator.myapplication.dialog.TipsDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> viewList;
    private View view1,view2,view3,view4,view5;
    private SimpleDraweeView sdv1,sdv2,sdv3,sdv4,sdv5;
    private File pic1,pic2,pic3,pic4,pic5;
    public ImageAdapter(Context context) {
        mContext=context;
        initView();
        showExamplePic();
        initFile();
        addListener();
    }

    private void showExamplePic() {
        BitmapUtil.showRes(sdv1,R.drawable.pic_example1);
        BitmapUtil.showRes(sdv2,R.drawable.pic_example2);
        BitmapUtil.showRes(sdv3,R.drawable.pic_example4);
        BitmapUtil.showRes(sdv4,R.drawable.pic_example3);
        BitmapUtil.showRes(sdv5,R.drawable.pic_example5);
    }

    private void initView() {  viewList = new ArrayList<>();
        view1=LayoutInflater.from(mContext).inflate(R.layout.layout_imageadapter,null);
        view2=LayoutInflater.from(mContext).inflate(R.layout.layout_imageadapter,null);
        view3=LayoutInflater.from(mContext).inflate(R.layout.layout_imageadapter,null);
        view4=LayoutInflater.from(mContext).inflate(R.layout.layout_imageadapter,null);
        view5=LayoutInflater.from(mContext).inflate(R.layout.layout_imageadapter,null);
        sdv1=view1.findViewById(R.id.imageadapter_sdv);
        sdv2=view2.findViewById(R.id.imageadapter_sdv);
        sdv3=view3.findViewById(R.id.imageadapter_sdv);
        sdv4=view4.findViewById(R.id.imageadapter_sdv);
        sdv5=view5.findViewById(R.id.imageadapter_sdv);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
    }

    private void initFile() {
        pic1 = new File(ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC1);
        try {
            if (pic1.exists()) {
                pic1.delete();
            }
            pic1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pic2 = new File(ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC2);
        try {
            if (pic2.exists()) {
                pic2.delete();
            }
            pic2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        pic3 = new File(ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC3);
        try {
            if (pic3.exists()) {
                pic3.delete();
            }
            pic3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pic4 = new File(ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC4);
        try {
            if (pic4.exists()) {
                pic4.delete();
            }
            pic4.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pic5 = new File(ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC5);
        try {
            if (pic5.exists()) {
                pic5.delete();
            }
            pic5.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }





//            createFile(pic1,ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC1);
//            createFile(pic2,ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC2);
//            createFile(pic3,ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC3);
//            createFile(pic4,ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC4);
//            createFile(pic5,ConstantUtil.PATH_PIC, ConstantUtil.NAME_PIC5);
    }
    private void createFile(File  file,String path,String name){
           file=new File(path,name);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addListener() {
        sdv1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (mListener!=null){
                      mListener.onAddClick(pic1,sdv1,1);
                 }
             }
         });
        sdv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onAddClick(pic2,sdv2,2);
                }
            }
        });
        sdv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onAddClick(pic3,sdv3,3);
                }
            }
        });
        sdv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onAddClick(pic4,sdv4,4);
                }
            }
        });
        sdv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onAddClick(pic5,sdv5,5);
                }
            }
        });
    }


    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnAddPicClick {
        void onAddClick(File file,SimpleDraweeView simpleDraweeView,int clickPosition);
    }
    private OnAddPicClick mListener;
    public void setOnAddPicClick( OnAddPicClick lis) {
        mListener = lis;
    }

}
