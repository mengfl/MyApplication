package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.BitmapUtil;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.RxBus;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.event.UploadPicEvent;
import com.example.administrator.myapplication.http.UploadOnePicRequest;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 上传图片activity
 */
public class UploadChargePicActivity extends AbsBaseTitleActivity {

    @BindView(R.id.simpleDraweeView)
    SimpleDraweeView mSdv;

    @BindView(R.id.act_uploadpic_tv)
    TextView tvInfo;
    private File frontFile;
    private   final int PHOTO_REQUEST_CAREMA = 0;//相机拍照
    private boolean isUpload;
    private boolean isCanSubmit;
    @Override
    public int layoutResourceID() {
        return R.layout.activity_upload_pic;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initTitle() {
        super.initTitle();

        tvTitle.setText("上传图片");
        viewRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");
    }

    @Override
    protected void initView() {
        super.initView();
        tvInfo.setText(getString(R.string.uploadpic_charging));
        isUpload=getIntent().getBooleanExtra(ConstantUtil.BUNDLE_KEY_BOOLEAN,false);
        frontFile=new File(ConstantUtil.PATH_PIC,ConstantUtil.NAME_PIC_CHARGE);
        if (isUpload){
            BitmapUtil.showPic(mSdv,frontFile.getAbsolutePath());
        }else {
            try {
                if (frontFile.exists()) {
                    frontFile.delete();
                }
                frontFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapUtil.showRes(mSdv,R.drawable.pic_example_charge);
        }

    }


    @Override
    protected void rightClick() {
        super.rightClick();
        if (isCanSubmit){
            uploadFile(frontFile);
        }else {
            ViewInjectUtil.toast("请先拍照");
        }
    }

    @OnClick(R.id.simpleDraweeView)
    public void onViewClicked() {
                picFromBig();
    }


   private void picFromBig(){
       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       Uri uri;
       if (Build.VERSION.SDK_INT<24){
           uri = Uri.fromFile(frontFile);
       }else {
           uri = FileProvider.getUriForFile(
                   this,
                   getPackageName() + ".fileprovider",
                   frontFile);
       }
       intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
       startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
   }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAREMA:/**从拍照返回的数据*/

                    BitmapUtil.cleanCache(Uri.parse("file://" + frontFile.getPath()));
                    BitmapUtil.judgeBitmap(frontFile);
                    BitmapUtil.showPic(mSdv, frontFile.getPath());
                    isCanSubmit=true;

                    break;
                default:
                    break;
            }
        }
    }
    public void uploadFile(File file) {

        UploadOnePicRequest request = new UploadOnePicRequest(this, NetHelper.URL_UPLOAD_PIC,file);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                ViewInjectUtil.toast("上传成功");
                RxBus.getDefault().post(new UploadPicEvent(UploadPicEvent.TYPE_CHARGE,true));
                finish();
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0, request);
    }
}
