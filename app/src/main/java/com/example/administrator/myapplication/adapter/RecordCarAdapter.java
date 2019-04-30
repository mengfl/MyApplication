package com.example.administrator.myapplication.adapter;

import android.content.Context;

import com.example.administrator.myapplication.bean.Record;
import com.example.administrator.myapplication.common.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import static com.example.administrator.myapplication.adapter.RecordCarAdapter.RecordCarItem.TYPE_CHILD;
import static com.example.administrator.myapplication.adapter.RecordCarAdapter.RecordCarItem.TYPE_PARENT;

/**
 * Created by Administrator on 2017/7/17.
 */

public class RecordCarAdapter extends MultiItemTypeAdapter<RecordCarAdapter.RecordCarItem> {
    public RecordCarAdapter(Context context, List<RecordCarItem> datas) {
        super(context, datas);
        addItemViewDelegate(TYPE_PARENT, new RecordCarParentDelegate());
        addItemViewDelegate(TYPE_CHILD, new RecordCarChildDelegate(mContext));
    }

    public static class RecordCarItem {
        public static final int TYPE_PARENT = 1;
        public static final int TYPE_CHILD = 2;
        private int type;
        private String name;
        private int num;
        private String date;
        private boolean isExpand;
        private String carId;
        private  int currentTime;
       private String cinId;

        public String getCinId() {
            return cinId;
        }

        public void setCinId(String cinId) {
            this.cinId = cinId;
        }

        public int getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }
    }
}
