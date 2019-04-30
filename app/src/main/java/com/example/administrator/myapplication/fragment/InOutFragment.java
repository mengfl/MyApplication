package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.helper.StepResultHelper;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.BaseFragment;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.DateUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.TimeUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.dialog.ExitCheckDialog;
import com.example.administrator.myapplication.dialog.TipsDialog;
import com.example.administrator.myapplication.event.LoginOutEvent;
import com.example.administrator.myapplication.factory.CountFactory;
import com.example.administrator.myapplication.factory.StatusFactory;
import com.example.administrator.myapplication.http.StepRequest;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 登入登出第一轮
 */

public class InOutFragment extends BaseFragment {
    @BindView(R.id.fra_inout_tv_title)
    TextView tvTitle;
    @BindView(R.id.fra_inout_tv_content)
    TextView tvContent;
    @BindView(R.id.fra_inout_tv_time)
    TextView tvTime;
    @BindView(R.id.fra_inout_btn_start)
    Button btnStart;
    @BindView(R.id.fra_inout_tv_jump)
    TextView tvJump;

    private boolean isTesting = false;  //是否正在测试
    private CountFactory factory;
    private TipsDialog tipsDialog; //测试结果dialog
    private int status;
    private ExitCheckDialog exitCheckDialog;   //返回测试dialog

    @Override
    public int layoutResourceID() {
        return R.layout.fragment_inout;
    }

    @Override
    protected void initData() {
        super.initData();
        setTxtValue();
        if (MyApplication.get().isDirectly()){
            status=StatusFactory.STEP_DIRECTLY_LOGINOUT1;
        }else {
            status=StatusFactory.STEP_TURN_LOGINOUT1;
        }
        initExitDialog();
        initTimeFactory();

    }

    @OnClick({R.id.fra_inout_btn_start, R.id.fra_inout_tv_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fra_inout_btn_start:
                if (!isTesting) {

                    recordBeginDate();
                }
                break;
            case R.id.fra_inout_tv_jump:
                if (!isTesting){
                    exitCheckDialog.show();
                }
//                else {
//                    ViewInjectUtil.toast("请等待测试结束后在返回");
//                }
                break;
            default:
                break;
        }
    }

    private Subscription subscription;
    private boolean isReveive=true; //是否已经收到登出消息
    @Override
    protected void setListener() {
        super.setListener();
         subscription=RxBus.getDefault().toObservable(LoginOutEvent.class).subscribe(new Action1<LoginOutEvent>() {
             @Override
             public void call(LoginOutEvent loginOutEvent) {
                      if (isReveive&&loginOutEvent.isOver()){
                          //收到登出报文
                          isReveive=false;
                          stopTest();
                          recordEndDate();
                          tvTime.setText("步骤倒计时：" + "00:00");
                      }
             }
         });
    }

    private void setTxtValue() {
        tvTitle.setText(String.format(getResources().getString(R.string.inout_title), "一"));
        tvContent.setText(String.format(getResources().getString(R.string.inout_content), "一"));
    }
    private void initExitDialog(){
        exitCheckDialog=new ExitCheckDialog(getActivity());
        exitCheckDialog.setOnTipsButtonClick(new ExitCheckDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                getActivity().finish();
            }
        });
    }
    /**
     * 定义计时器
     */
    private void initTimeFactory() {
        tvTime.setText("步骤倒计时："+TimeUtil.longToTime(ConstantUtil.TIME_INOUT));
        factory = new CountFactory(ConstantUtil.TIME_INOUT);
        factory.setOnCountDownListener(new CountFactory.OnCountDownListener() {
            @Override
            public void onProgress(long millisUntilFinished) {
                tvTime.setText("步骤倒计时：" + TimeUtil.longToTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                if (!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
                recordEndDate();
                tvTime.setText("步骤倒计时：" + "00:00");
            }
        });
    }


    /**
     * 记录开始时间
     */
    private void recordBeginDate(){
       StepRequest request=new StepRequest(getActivity(), NetHelper.URL_STEP,status, DateUtil.getCurrentTime(true,new Date()),null);
       request.setCallBackListener(new MyCallBack() {
           @Override
           public void onSuccess(int what, Object object) {
               startTest();
           }

           @Override
           public void onFailed() {

           }
       });
       addNetRequest(0,request);
   }

    /**
     * 记录结束时间
     */
   private void recordEndDate(){
        StepRequest request=new StepRequest(getActivity(),NetHelper.URL_STEP,status,null,DateUtil.getCurrentTime(true,new Date()));
       request.setCallBackListener(new MyCallBack() {
           @Override
           public void onSuccess(int what, Object object) {
               StepResultHelper helper= (StepResultHelper) object;
               if (helper!=null){
                   String strResult= helper.getResult();
                   if ("检测成功".equals(strResult)){
                       testSuccess();
                   }else {
                       testFail();
                   }
               }else {
                   testFail();
               }
           }

           @Override
           public void onFailed() {
               stopTest();
           }
       });
       addNetRequest(1,request);
   }







    private void testSuccess() {
        tipsDialog = new TipsDialog(getActivity());
        tipsDialog.setOnTipsButtonClick(new TipsDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                startFragment(InOutFragment2.class);
            }
        });
        tipsDialog.success("测试成功");
        if (!tipsDialog.isShowing()) {
            tipsDialog.show();
        }
    }

    private void testFail() {
        tipsDialog = new TipsDialog(getActivity());
        tipsDialog.setOnTipsButtonClick(new TipsDialog.OnTipsButtonClick() {
            @Override
            public void onOkClick() {
                     stopTest();
            }
        });
        tipsDialog.fail("测试失败");
        if (!tipsDialog.isShowing()) {
            tipsDialog.show();
        }
    }





    /**
     * 开始测试
     */
    private void startTest() {
        btnStart.setVisibility(View.GONE);
        startTimeDown();
        isTesting = true;

    }
    /**
     * 开始计时
     */
    private void startTimeDown() {
        if (factory != null) {
            factory.start();
        }
    }

    /**
     * 停止测试
     */
    private void stopTest() {
        btnStart.setVisibility(View.VISIBLE);
        isTesting = false;
        resetTimeDown();
    }
    /**
     * 重置计时
     */
    private void resetTimeDown() {
        factory.cancel();
        initTimeFactory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (factory != null) {
            factory.cancel();
        }
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

}
