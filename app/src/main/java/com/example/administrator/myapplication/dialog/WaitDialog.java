package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;


/**
 * 网络加载等待框
 */
public class WaitDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private ImageView img;
    Animation operatingAnim;
    public WaitDialog(@NonNull Context context) {
        super(context, R.style.BottomSimpleDialog);
        mContext=context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_wait, null);
        setContentView(contentView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        img= (ImageView) findViewById(R.id.dialog_wait_img);

         operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_dialogloading);
         operatingAnim.setFillAfter(true);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

    }
    @Override
    public void dismiss() {
        super.dismiss();
        img.clearAnimation();
    }

    @Override
    public void show() {
        super.show();
        img.startAnimation(operatingAnim);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        dismiss();
    }



}
