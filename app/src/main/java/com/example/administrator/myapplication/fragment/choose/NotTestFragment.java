package com.example.administrator.myapplication.fragment.choose;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ChooseCarAdapter;
import com.example.administrator.myapplication.bean.Car;
import com.example.administrator.myapplication.bean.helper.CarHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.recyclerview.HorizontalDivider;
import com.example.administrator.myapplication.common.recyclerview.MultiItemTypeAdapter;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.event.ChooseCarEvent;
import com.example.administrator.myapplication.http.SearchCheckingCarRequest;
import com.example.administrator.myapplication.http.SearchNotCheckCarRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 待检测
 */
public class NotTestFragment extends BaseFragment {
    @BindView(R.id.fra_nottest_recyclerview)
    RecyclerView mRecyclerview;

    private ChooseCarAdapter mAdapter;
    private List<Car> mData = new ArrayList<>();
    private Car carInfo;

    @Override
    public int layoutResourceID() {
        return R.layout.fragment_nottest;
    }

    @Override
    protected void initData() {
        super.initData();
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        mAdapter = new ChooseCarAdapter(getActivity(), R.layout.item_choosetype, mData,false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        HorizontalDivider divider = new HorizontalDivider(getActivity(), HorizontalDivider.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.shape_divider));
        mRecyclerview.addItemDecoration(divider);
        mRecyclerview.setAdapter(mAdapter);
    }
    private void getData() {

        SearchNotCheckCarRequest request = new SearchNotCheckCarRequest(getActivity(), NetHelper.URL_SEARCH_NOT_CHECK_CAR);

        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                CarHelper carHelper = (CarHelper) object;
                if (carHelper != null) {
                    mData = carHelper.getList();
                    mAdapter.setmDatas(mData);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, request);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                for (int i = 0; i < mData.size(); i++) {
//                    mData.get(i).setSelect(false);
//                }
//                mData.get(position).setSelect(true);
//                mAdapter.notifyDataSetChanged();
//                carInfo = mData.get(position);
//                RxBus.getDefault().post(new ChooseCarEvent(carInfo));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
