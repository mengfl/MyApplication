package com.example.administrator.myapplication.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Car;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.DoubleClickExitUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.http.BindCarRequest;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 选择车辆型号
 */
public class ChooseCarActivity extends AbsBaseTitleActivity {


    @BindView(R.id.act_choosecar_tv_choosetype)
    TextView tvChoosetype;
    @BindView(R.id.act_choosecar_tv_date)
    TextView tvDate;
    @BindView(R.id.act_choosecar_tv_notinum)
    TextView tvNotinum;
    @BindView(R.id.act_choosecar_tv_vin)
    TextView tvVin;
    @BindView(R.id.act_choosecar_tv_licensenum)
    TextView tvLicensenum;
    @BindView(R.id.act_choosecar_btn_ok)
    Button btnOk;

    private static final int REQUEST_CHOOSE = 1;
    private Car car;
    private boolean isSetNull;
    private DoubleClickExitUtil doubleClickExitUtil;

    @Override
    public int layoutResourceID() {
        return R.layout.activity_choose_car;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        doubleClickExitUtil = new DoubleClickExitUtil(this);
        tvTitle.setText("选择车辆型号");
        imgLeft.setImageResource(R.drawable.icon_mine);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isSetNull) {
            car = null;
            tvDate.setText("预约日期      ");
            tvNotinum.setText("车辆型号      ");
            tvVin.setText(" 车辆VIN      ");
            MyApplication.get().setVin("");
            MyApplication.get().setCarId("");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        account = getIntent().getStringExtra(ConstantUtil.BUNDLE_KEY_STRING);
    }

    @Override
    protected void leftClick() {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra(ConstantUtil.BUNDLE_KEY_STRING, account);
        startActivity(intent);
    }

    private String account;


    @OnClick({R.id.act_choosecar_tv_choosetype, R.id.act_choosecar_btn_ok})
    public void onViewClicked(View view) {
        Intent i = null;
        switch (view.getId()) {
            case R.id.act_choosecar_tv_choosetype:
                i = new Intent(ChooseCarActivity.this, ChooseTypeActivity.class);
                startActivityForResult(i, REQUEST_CHOOSE);
                break;
            case R.id.act_choosecar_btn_ok:
                if (car == null) {
                    ViewInjectUtil.toast("请选择车辆型号");
                } else {
                    bindCarId();
                }
                break;
            default:
                break;
        }
    }

    private void bindCarId() {
        BindCarRequest request = new BindCarRequest(this, NetHelper.URL_BIND_CARID);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                Intent i = null;
                isSetNull = true;
                if ("0".equals(car.getSubmitPhotos())) {
                    i = new Intent(ChooseCarActivity.this, UploadMoreActivity.class);
                } else {
                    i = new Intent(ChooseCarActivity.this, TestMethodActivity.class);
                }
                startActivity(i);
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(2, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE) {
                isSetNull = false;
                car = (Car) data.getSerializableExtra(ConstantUtil.BUNDLE_KEY_OBJ);
                tvDate.setText("预约日期      " + car.getExpectStartDate());
                tvNotinum.setText("车辆型号      " + car.getCarBrand());
                tvVin.setText(" 车辆VIN      " + car.getCarVin());
                MyApplication.get().setCarBrand(car.getCarBrand());
                MyApplication.get().setVin(car.getCarVin());
                MyApplication.get().setCarId(car.getCarId());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return doubleClickExitUtil.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
