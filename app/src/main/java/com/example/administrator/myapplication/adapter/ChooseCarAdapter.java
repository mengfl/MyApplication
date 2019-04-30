package com.example.administrator.myapplication.adapter;

import android.content.Context;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Car;
import com.example.administrator.myapplication.common.recyclerview.CommonAdapter;
import com.example.administrator.myapplication.common.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
public class ChooseCarAdapter extends  CommonAdapter<Car>  {
    private boolean isShow;  //是否显示选择
    public ChooseCarAdapter(Context context, int layoutId, List<Car> datas,boolean isShow) {
        super(context, layoutId, datas);
        this.isShow=isShow;
    }

    @Override
    protected void convert(ViewHolder holder, Car car, int position) {
          if (isShow){
               holder.setVisible(R.id.choosetype_cb,true);
          }else {
              holder.setVisible(R.id.choosetype_cb,false);
          }
          holder.setText(R.id.choosetype_tv_name,car.getCarBrand()+"("+car.getCarVin()+")");
//          holder.setText(R.id.choosetype_tv_name,car.getCarVin());
          holder.setText(R.id.choosetype_tv_date,"检测时间："+car.getExpectStartDate());
          holder.setChecked(R.id.choosetype_cb,car.isSelect());
    }
}
