package com.example.administrator.myapplication.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.MyApplication;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 图片显示工具 fresco
 */

public class BitmapUtil {
    /**
     * 清理缓存
     * @param uri
     */
    public static void cleanCache(Uri uri){
        ImagePipeline imagePipeline= Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
        imagePipeline.evictFromCache(uri);

    }
    public static void  showUrl(Context context, SimpleDraweeView draweeView , String url){
        Uri uri= Uri.parse(url);
        draweeView.setImageURI(uri);

    }
    public static void  showUri(SimpleDraweeView draweeView , Uri uri){
        draweeView.setImageURI(uri);
    }

    /**
     * 显示本地图片。
     * @param draweeView imageView。
     * @param path       路径。
     */
    public static void showFile(SimpleDraweeView draweeView, String path) {
        try {
            Uri uri = Uri.parse("file://" + path);
            draweeView.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 显示一个本地图片。
     * @param draweeView imageView。
     * @param path       路径。
     * @param width      实际宽。
     * @param height     实际高度。
     */
    public static void showFile(Context context, SimpleDraweeView draweeView, String path, int width, int height) {
        try {
             calcRealSizeByDesign(context,draweeView,width,height);
            Uri uri = Uri.parse("file://" + path);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示一个Res中的图片。
     *
     * @param draweeView ImageView。
     * @param resId      资源ID。
     */
    public static void showRes(SimpleDraweeView draweeView, @DrawableRes int resId) {
        try {
            draweeView.setImageURI(Uri.parse("res:///" + resId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示content provider图片。
     *
     * @param draweeView image view。
     * @param path       路径。
     */
    public static void showContentProvider(SimpleDraweeView draweeView, String path) {
        try {
            draweeView.setImageURI(Uri.parse("content://" + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示Assets中的图片。
     *
     * @param draweeView ImageView.
     * @param path       路径。
     */
    public static void showAsset(SimpleDraweeView draweeView, String path) {
        try {
            draweeView.setImageURI(Uri.parse("asset://" + path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 加载图片成bitmap。
     *
     * @param imageUrl 图片地址。
     */
    public static void loadToBitmap(String imageUrl, BaseBitmapDataSubscriber mDataSubscriber) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(imageUrl))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage
                (imageRequest, MyApplication.get());
        dataSource.subscribe(mDataSubscriber, CallerThreadExecutor.getInstance());
    }
    /**
     * 设置view大小。
     *
     * @param view  View。
     * @param width 指定宽。
     * @param width 指定高。
     */
    public static void requestLayout(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(width, height);
            view.setLayoutParams(layoutParams);
        } else {
            view.getLayoutParams().width = width;
            view.getLayoutParams().height = height;
            view.requestLayout();
        }
    }

    /**
     * 根据设计图宽高，计算出View在该屏幕上的实际宽高。
     *
     * @param width  设计图中View宽。
     * @param height 设计图中View高。
     */
    public static void calcRealSizeByDesign(Context context, View view, int width, int height) {
        int realWidth, realHeight;
        realWidth = DensityUtil.getScreenW(context) * width / 1080;
        realHeight =  DensityUtil.getScreenH(context) * height / 1920;
        requestLayout(view, realWidth, realHeight);
    }
    public static void showPic(SimpleDraweeView view, String path) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + path))
                .setProgressiveRenderingEnabled(true)
                .setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setResizeOptions(new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .setAutoPlayAnimations(true).build();
        view.setController(controller);
    }



    public static void judgeBitmap(File file){
        Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap=reviewPicRotate(bitmap,file.getAbsolutePath());
        saveBitmapFile(bitmap,file);
        bitmap=getSmallBitmap(file.getAbsolutePath(),720,1280);
        saveBitmapFile(bitmap,file);
        if (bitmap!=null){
            bitmap.recycle();
        }
    }
    /**
     * 将bitmap保存到本地
     * @param bitmap
     */
    public static void saveBitmapFile(Bitmap bitmap,File file) {
        try {

            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据路径获得图片并压缩返回bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth,
                                        int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取图片文件的信息，是否旋转了90度，如果是则反转
     * @param bitmap 需要旋转的图片
     * @param path   图片的路径
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap,String path){
        int degree = getPicRotate(path);
        if(degree!=0){
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,m, true);// 从新生成图片
        }
        return bitmap;
    }
    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        LogerUtil.error("压缩比",inSampleSize+"");
        return inSampleSize;
    }


    /**
     * 读取图片文件旋转的角度
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

//
//    /**
//     * 质量压缩方法
//     *
//     * @param image
//     * @return
//     */
//    public static Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 >1024 ) {  //循环判断如果压缩后图片是否大于1024kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }

//    /**
//     * 通过URI获取Bitmap
//     * @param uri
//     * @return
//     */
//    public  static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
//        Bitmap bitmap = null;
//        try {
//            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return bitmap;
//    }
//
//    public static void compressionBitmapFormUri(Context context,File file) throws FileNotFoundException, IOException {
//        Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
//        bitmap=rotaingImageView(readPictureDegree(file.getAbsolutePath()),bitmap); //将图旋转
//        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
//        onlyBoundsOptions.inJustDecodeBounds = true;
//        onlyBoundsOptions.inDither = true;//optional
//        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
//        saveBitmap(file,bitmap);
//
//
//
//
//
//
//        Uri uri=Uri.parse("file://" + file.getPath());
//        InputStream input = context.getContentResolver().openInputStream(uri);
//
//        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
//        onlyBoundsOptions.inJustDecodeBounds = true;
//        onlyBoundsOptions.inDither = true;//optional
//        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
//        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
//        input.close();
//        int originalWidth = onlyBoundsOptions.outWidth;
//        int originalHeight = onlyBoundsOptions.outHeight;
//
//        //图片分辨率以480x800为标准
//        float hh = 1920f;//这里设置高度为800f
//        float ww = 1080f;//这里设置宽度为480f
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (originalWidth / ww);
//        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (originalHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        //比例压缩
//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inSampleSize = be;//设置缩放比例
//        bitmapOptions.inDither = true;//optional
//        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
//        input = context.getContentResolver().openInputStream(uri);
//        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
//        input.close();
//        saveBitmap(file,compressImage(bitmap));
//    }
//    public static void saveBitmap(File f,Bitmap bm) {
//        if (f.exists()) {
//            f.delete();
//        }
//        try {
//            FileOutputStream out = new FileOutputStream(f);
//            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }




}
