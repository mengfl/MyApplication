package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


/**
 * 提示dialog
 */
public class ExitCheckDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private TextView tvContent,tvOk,tvCancle;



    public ExitCheckDialog(@NonNull Context context) {
        super(context,R.style.TipsDialog);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
          /*
         * 将对话框的大小按屏幕大小的百分比设置
         */

        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_exitcheck, null);

        setContentView(contentView);
        setCanceledOnTouchOutside(false);

        tvContent = (TextView) findViewById(R.id.dialog_tips_tv_content);

        tvOk = (TextView) findViewById(R.id.dialog_tips_tv_ok);
        tvCancle = (TextView) findViewById(R.id.dialog_tips_tv_cancle);

        tvOk.setOnClickListener(this);
        tvCancle.setOnClickListener(this);
        Window window=getWindow();
        WindowManager m =window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();

        p.width = (int) (d.getWidth() * 0.8);
        window.setAttributes(p);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dialog_tips_tv_ok:
                if (mListener!=null){
                    mListener.onOkClick();
                }
                break;
            case R.id.dialog_tips_tv_cancle:

                break;
        }
        dismiss();
    }
    public interface OnTipsButtonClick {
        void onOkClick();
    }
    private OnTipsButtonClick mListener;
    public void setOnTipsButtonClick(OnTipsButtonClick lis) {
        mListener = lis;
    }
    public void success(String content){
        tvContent.setText(content);

    }
    public void fail(String string){
        tvContent.setText(string);

    }
    public void netDisConnect(){
        tvCancle.setVisibility(View.GONE);
        tvContent.setText("网络断开，请重连测试");
    }
}
