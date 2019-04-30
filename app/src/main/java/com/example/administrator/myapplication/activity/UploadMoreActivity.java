package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.MyApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.common.net.MyCallBack;
import com.example.administrator.myapplication.common.net.NetHelper;
import com.example.administrator.myapplication.common.ui.AbsBaseTitleActivity;
import com.example.administrator.myapplication.common.utils.BitmapUtil;
import com.example.administrator.myapplication.common.utils.ConstantUtil;
import com.example.administrator.myapplication.common.utils.LogerUtil;
import com.example.administrator.myapplication.common.utils.ViewInjectUtil;
import com.example.administrator.myapplication.common.widget.CycleViewPager;
import com.example.administrator.myapplication.adapter.ImageAdapter;
import com.example.administrator.myapplication.factory.MyTransformation;
import com.example.administrator.myapplication.http.UploadFivePicRequest;
import com.example.administrator.myapplication.http.UploadOnePicRequest;
import com.facebook.drawee.view.SimpleDraweeView;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;

/**
 * 上传多张照片
 */
public class UploadMoreActivity extends AbsBaseTitleActivity {

    @BindView(R.id.act_uploadmore_viewpager)
    CycleViewPager mViewpager;
    @BindView(R.id.act_uploadmore_tv)
    TextView tvSign;
    private String strSign="";
    private ImageAdapter mAdapter;
    private   final int PHOTO_REQUEST_CAREMA = 0;//相机拍照
    private SimpleDraweeView cacheSdv;
    private File cacheFile;
    private int position;
    private SparseArray<File> sparseArray;
    @Override
    public int layoutResourceID() {
        return R.layout.activity_upload_more;
    }

    @Override
    protected int fragmentLayoutId() {
        return 0;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tvTitle.setText("提交照片");
        viewRight.setVisibility(View.VISIBLE);
        tvRight.setText("提交");
    }

    @Override
    protected void setListener() {
        super.setListener();
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case  0:
                        strSign="拍摄整车图片，角度为左前方45度";
                        break;
                    case  1:
                        strSign="清晰拍摄样车铭牌，展现VIN码和整车参数";
                        break;
                    case  2:
                        strSign="拍摄终端特写照片，清晰展现厂商名称、终端型号信息及外形";
                        break;
                    case  3:
                        strSign="拍摄终端安装照片，要求能够清晰辨识终端整车安装位置";
                        break;
                    case  4:
                        strSign="拍摄测试前车辆仪表盘整体照片，要求展现SOC及累计里程";
                        break;
                    default:
                        break;
                }
                tvSign.setText(strSign);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter.setOnAddPicClick(new ImageAdapter.OnAddPicClick() {
            @Override
            public void onAddClick(File file, SimpleDraweeView simpleDraweeView, int clickPosition) {
                cacheFile=file;
//                createFile(ConstantUtil.PATH_PIC, ConstantUtil.NAME_CACHE);
                cacheSdv=simpleDraweeView;
                position=clickPosition;
                picFromBig();
            }
        });
    }
    private void createFile(String path,String name){
        cacheFile=new File(path,name);
        try {
            if (cacheFile.exists()) {
                cacheFile.delete();
            }

            cacheFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void initView() {
        super.initView();

        sparseArray = new SparseArray<>();

        mViewpager.setRecycleMode(true);
        mViewpager.setPageTransformer(true,new MyTransformation());
        mViewpager.setPageMargin(-90);
        mViewpager.setOffscreenPageLimit(1);
        mAdapter = new ImageAdapter(this);
        mViewpager.setAdapter(mAdapter);

    }

    @Override
    protected void rightClick() {
        super.rightClick();
        if (sparseArray.size()==5){
            uploadPic();
        }else {
            ViewInjectUtil.toast("请先拍照");
        }

    }

    private void uploadPic() {
        UploadFivePicRequest request=new UploadFivePicRequest(this, NetHelper.URL_UPLOAD_PICS, sparseArray);
        request.setCallBackListener(new MyCallBack() {
            @Override
            public void onSuccess(int what, Object object) {
                ViewInjectUtil.toast("上传成功");
                skipActivity(UploadMoreActivity.this,TestMethodActivity.class);
                finish();
            }

            @Override
            public void onFailed() {

            }
        });
        addNetRequest(0,request);
    }

    /**
     * 拍照
     */
    private void picFromCamera() {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 启动相机
        startActivityForResult(intent1, PHOTO_REQUEST_CAREMA);
    }

    private void picFromBig(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT<24){
              uri = Uri.fromFile(cacheFile);
        }else {
            uri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    cacheFile);
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

                        BitmapUtil.cleanCache(Uri.parse("file://" + cacheFile.getPath()));
                    BitmapUtil.judgeBitmap(cacheFile);
                        BitmapUtil.showPic(cacheSdv, cacheFile.getPath());
                        sparseArray.put(position,cacheFile);



                    break;
                default:
                    break;
            }
        }
    }

}
