package com.example.administrator.myapplication.fragment.record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.RecordAdapter;
import com.example.administrator.myapplication.adapter.RecordChildDelegate;
import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.bean.helper.RecordHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.recyclerview.MultiItemTypeAdapter;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.factory.StatusFactory;
import com.example.administrator.myapplication.http.RecordRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 终端直连记录
 */
public class DirectlyRecordFragment extends BaseFragment {
    @BindView(R.id.fra_direcylyrecord_recyclerview)
    RecyclerView mRecyclerview;
    private RecordAdapter mAdapter;

    private Record record;

    private List<Record> mData;
    List<Record> listZongHe;
    List<Record> listDriving;
    List<Record> listCharge;
    @Override
    public int layoutResourceID() {
        return R.layout.fragment_directlyrecord;
    }

    @Override
    protected void initData() {
        super.initData();

        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        mAdapter = new RecordAdapter(getActivity(), mData);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Record info = mData.get(position);
                if (Record.TYPE_PARENT == info.getViewType()) {
                      if ("综合阶段".equals(info.getName())){
                          if (!info.isExpand()) {
                              mData.addAll(position+1, listZongHe);
                              info.setExpand(true);
                              mAdapter.notifyDataSetChanged();
                          }else {
                              mData.removeAll(listZongHe);
                              info.setExpand(false);
                              mAdapter.notifyDataSetChanged();
                          }
                      }
                    if ("行驶阶段".equals(info.getName())){
                        if (!info.isExpand()) {
                            mData.addAll(position+1, listDriving);
                            info.setExpand(true);
                            mAdapter.notifyDataSetChanged();
                        }else {
                            mData.removeAll(listDriving);
                            info.setExpand(false);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    if ("充电阶段".equals(info.getName())){
                        if (!info.isExpand()) {
                            mData.addAll(position+1, listCharge);
                            info.setExpand(true);
                            mAdapter.notifyDataSetChanged();
                        }else {
                            mData.removeAll(listCharge);
                            info.setExpand(false);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    private void getData(){
        RecordRequest request=new RecordRequest(getActivity(), NetHelper.URL_RECORD);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                      RecordHelper helper= (RecordHelper) object;
                if (helper!=null){
                    judge(helper.getList());
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(2,request);
    }

    private void judge(List<Record> list){
        if (list!=null&&list.size()>0){
            mData=new ArrayList<>();
            listZongHe=new ArrayList<>();
            listDriving=new ArrayList<>();
            listCharge=new ArrayList<>();

            for (int i=0;i<list.size();i++){
                Record record=list.get(i);
                int currentStep=Integer.parseInt(record.getCurrentStep());
                record.setViewType(Record.TYPE_CHILD);
                switch (currentStep){
                    case   StatusFactory.STEP_DIRECTLY_LOGINOUT1:
                        record.setName("登入登出（第一轮）");
                        listZongHe.add(record);
                        break;
                    case   StatusFactory.STEP_DIRECTLY_LOGINOUT2:
                        record.setName("登入登出（第二轮）");
                        listZongHe.add(record);
                        break;
                    case   StatusFactory.STEP_DIRECTLY_LOGINOUT3:
                        record.setName("登入登出（第三轮）");
                        listZongHe.add(record);
                        break;
                    case   StatusFactory.STEP_DIRECTLY_POLICE:
                        record.setName("报警测试");
                        listZongHe.add(record);
                        break;
                    case   StatusFactory.STEP_DIRECTLY_SUPPLY:
                        record.setName("补发测试");
                        listZongHe.add(record);
                        break;
                    case   StatusFactory.STEP_DIRECTLY_DRIVING:
                        record.setName("行驶测试");
                        listDriving.add(record);
                        break;
                    case   StatusFactory.STEP_DIRECTLY_CHARGE:
                        record.setName("充电测试");
                        listCharge.add(record);
                        break;
                }
            }
            record = new Record();
            record.setName("综合阶段");
            record.setViewType(Record.TYPE_PARENT);
            mData.add(record);
            record = new Record();
            record.setName("行驶阶段");
            record.setViewType(Record.TYPE_PARENT);
            mData.add(record);
            record = new Record();
            record.setName("充电阶段");
            record.setViewType(Record.TYPE_PARENT);
            mData.add(record);
            mAdapter.setmDatas(mData);
            mAdapter.notifyDataSetChanged();
        }
    }
}
