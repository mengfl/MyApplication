package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.RecordCarAdapter;
import com.example.administrator.myapplication.bean.Car;
import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.bean.RecordCarTime;
import com.example.administrator.myapplication.bean.helper.RecordCarHelper;
import com.example.administrator.myapplication.bean.helper.RecordCarTimeHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.recyclerview.MultiItemTypeAdapter;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.http.RecordCarRequest;
import com.example.administrator.myapplication.http.RecordCarTimeRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试记录选车
 */
public class RecordCarActivity extends AbsBaseTitleActivity {


    @BindView(R.id.act_recordcar_recyclerview)
    RecyclerView mRecyclerview;
    List<RecordCarAdapter.RecordCarItem> mData = new ArrayList<>();
    private RecordCarAdapter mAdapter;
    private HashMap<String, List<RecordCarAdapter.RecordCarItem>> hashMap;

    @Override
    public int layoutResourceID() {
        return R.layout.activity_record_car;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }


    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
        getData();
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("测试记录");
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                RecordCarAdapter.RecordCarItem info = mData.get(position);
                if (RecordCarAdapter.RecordCarItem.TYPE_PARENT == info.getType()) {
                    //点击的parent
                    List<RecordCarAdapter.RecordCarItem> aaa = hashMap.get(info.getCarId());

                    if (info.isExpand()) {
                        //已经扩展了
                        if (aaa != null && aaa.size() > 0) {
                            info.setExpand(false);
                            mData.removeAll(aaa);
                            mAdapter.setmDatas(mData);
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        //没扩展
                        if (aaa==null||aaa.size()<=0){
                            getTimesData(position);
                        }else {
                            mData.get(position).setExpand(true);
                            mData.addAll(position+1,aaa);
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    //点击的child

                    Intent intent=new Intent(RecordCarActivity.this,RecordActivity.class);
                    MyApplication.get().setCinId(mData.get(position).getCinId());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new RecordCarAdapter(this, mData);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mAdapter);
    }

    private void getData() {
        RecordCarRequest request = new RecordCarRequest(this, NetHelper.URL_RECORD_CHOOSE);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                RecordCarHelper helper = (RecordCarHelper) object;
                if (helper != null) {
                    List<Car> list = helper.getList();
                    juedgeCarData(list);
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, request);
    }

    private void juedgeCarData(List<Car> list) {
        if (list != null && list.size() > 0) {
            hashMap = new HashMap<String, List<RecordCarAdapter.RecordCarItem>>();
            RecordCarAdapter.RecordCarItem item;
            for (int i = 0; i < list.size(); i++) {
                Car car = list.get(i);
                item = new RecordCarAdapter.RecordCarItem();
                item.setName(car.getCarBrand());
                item.setType(RecordCarAdapter.RecordCarItem.TYPE_PARENT);
                item.setNum(car.getTestNum());
                item.setCarId(car.getCarId());
                mData.add(item);
            }
            mAdapter.setmDatas(mData);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getTimesData(final int position) {
        RecordCarTimeRequest recordCarTimeRequest = new RecordCarTimeRequest(this, NetHelper.URL_RECORD_TIMES, mData.get(position).getCarId());
        recordCarTimeRequest.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                RecordCarTimeHelper helper = (RecordCarTimeHelper) object;
                if (helper!=null){
                    judgeTimeData(helper.getList(),position);
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(1, recordCarTimeRequest);

    }
   private void judgeTimeData(List<RecordCarTime> list,int position){

          List<RecordCarAdapter.RecordCarItem> carItemList=new ArrayList<>();
         if (list!=null&&list.size()>0){
             for (int i=0;i<list.size();i++){
                 RecordCarTime time= list.get(i);
                 RecordCarAdapter.RecordCarItem  item=new RecordCarAdapter.RecordCarItem();
                 item.setType(RecordCarAdapter.RecordCarItem.TYPE_CHILD);
                 item.setDate(time.getTestStartDate());
                 item.setCurrentTime(time.getRow());
                 item.setCinId(time.getCinId());
                 carItemList.add(item);
             }
             mData.get(position).setExpand(true);
             hashMap.put(mData.get(position).getCarId(),carItemList);
             mData.addAll(position + 1,carItemList);
             mAdapter.notifyDataSetChanged();

         }





   }

}
