package com.example.administrator.myapplication.factory;

import android.view.View;

import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.widget.CycleViewPager;

/**
 * Created by Evan Zeng on 2016/8/18.
 */

public class MyTransformation implements CycleViewPager.PageTransformer {

    private static final float MIN_SCALE=0.75f;


    @Override
    public void transformPage(View page, float position) {

        float scaleFactor= Math.max(MIN_SCALE,1- Math.abs(position));
//        if ("图片3".equals(page.getTag())){
//            LogerUtil.error("图片3",scaleFactor+"");
//        }
//        if ("图片1".equals(page.getTag())){
//            LogerUtil.error("图片1",scaleFactor+"");
//        }
//        if ("图片2".equals(page.getTag())){
//            LogerUtil.error("图片2",scaleFactor+"");
//        }
//        if ("图片5".equals(page.getTag())){
//            LogerUtil.error("图片5",scaleFactor+"");
//        }
        if (position<-1){

        }else if (position<0){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }else if (position>=0&&position<1){

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
        else if (position>=1) {

        }

    }
}
