package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Car;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.event.ChooseCarEvent;
import com.example.administrator.myapplication.fragment.choose.NotTestFragment;
import com.example.administrator.myapplication.fragment.choose.TestedFragment;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 选择已预约车型
 */
public class ChooseTypeActivity extends AbsBaseTitleActivity {


    @BindView(R.id.act_choosetype_rb_not_test)
    RadioButton rbNotTest;
    @BindView(R.id.act_choosetype_rb_tested)
    RadioButton rbTested;
    @BindView(R.id.act_choosetype_rg)
    RadioGroup mRg;

   private Car car;
   private Subscription subscription;
    @Override
    public int layoutResourceID() {
        return R.layout.activity_choose_type;
    }

    @Override
    protected int fragmentLayoutId() {
        return R.id.act_choosetype_fl;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("选择已预约的车型");
        viewRight.setVisibility(View.VISIBLE);
        tvRight.setText("确定");
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRg.check(R.id.act_choosetype_rb_not_test);
        startFragment(NotTestFragment.class);
        car=null;
        subscription= RxBus.getDefault().toObservable(ChooseCarEvent.class).subscribe(new Action1<ChooseCarEvent>() {
            @Override
            public void call(ChooseCarEvent chooseCarEvent) {
                   car=chooseCarEvent.getCar();
            }
        });
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.act_choosetype_rb_not_test :
                        startFragment(NotTestFragment.class);
                          car=null;
                        break;
                    case R.id.act_choosetype_rb_tested :
                        startFragment(TestedFragment.class);
                        car=null;
                        break;
                    default:
                        break;
                }
            }
        });

    }
    @Override
    protected void rightClick() {
        super.rightClick();
        if (car == null) {
            ViewInjectUtil.toast("请选择车辆");
        } else {
            Intent intent = new Intent(this, ChooseCarActivity.class);
            intent.putExtra(ConstantUtil.BUNDLE_KEY_OBJ, car);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
