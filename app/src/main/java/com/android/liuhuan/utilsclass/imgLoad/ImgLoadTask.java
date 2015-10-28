package com.android.liuhuan.utilsclass.imgLoad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import demo.androidgaiji.liuhuan.bitmapdisplay.cache.FileCache;
import demo.androidgaiji.liuhuan.bitmapdisplay.utils.HttpTools;

/**
 * Created by LiuHuan on 2015/10/16.
 */
public class ImgLoadTask extends AsyncTask<String,Integer,Bitmap> {

    private final WeakReference<ImageView> imgViewReference;
    /*
    * 加载图片，最终显示的宽高
    *
    * */
    private  int requestWidth;
    private int requestHeight;

    /**
     * 异步任务的构造
     *
     * @param imageView ImageView 需要显示的ImageView
     * @param requestWidth  请求的宽度 0 代表显示原始图像，> 0 将图像缩小
     * @param requestHeight 请求的高度 0 代表显示原始图像，> 0 将图像缩小
     */
    public ImgLoadTask(ImageView imageView,int requestWidth,int requestHeight){
        imgViewReference =new WeakReference<ImageView>(imageView);
        this.requestHeight=requestHeight;
        this.requestWidth=requestWidth;
    }
    @Override
    protected Bitmap doInBackground(String... param) {
        Bitmap ret=null;
        if (param!=null&&param.length>0){
            String url=param[0];
            //获取url中对应的文件缓存
            byte[] data = FileCache.getInstance().load(url);
            if (data != null) {
                //TODO 有文件对象，不需要联网

            }else {
                //TODO 联网下载图片
               data= HttpTools.doGet(url);
                FileCache.getInstance().save(url,data);
            }
            if (data!=null) {
                //按照原始的图片尺寸进行Bitmap的生成
//               ret= BitmapFactory.decodeByteArray(data, 0, data.length);

                //采用二次采样（缩小图片尺寸的方式）
                //1.步骤一：获取原始图片的宽高信息，拥有进行采样的计算
               //1.1创建Options,给BitmapFactory的内部解码器传递参数
                BitmapFactory.Options options=new BitmapFactory.Options();
                //1.2设置inJustDecodeBounds来控制解码器，只进行图片宽高的设置，不会加载Bitmap
                //不占用内存，当使用这个参数，代表BitmapFactory。decodeXXX类似的方法，不会加载Bitmap
                options.inJustDecodeBounds=true;
                //解码，使用Options参数设置解码方式
                BitmapFactory.decodeByteArray(data,0,data.length,options);
                //-------------------------------------------------------
                //2.步骤2 根据图片的真实尺寸，与当前需要显示的尺寸，进行计算，生成图片采样
                //2.1
                int picW = options.outWidth;//6000
                int picH = options.outHeight;//4000
                //2.2准备显示在手机上的尺寸。256*128 128*64
                //尺寸是根据程序需要来设置的
                //maxWith maxHeight
                int reW=requestWidth;
                int reqH=requestHeight;
                //2.3计算，设置图片的采样率
                options.inSampleSize =
                        calculateInSampleSize(options,reW,reqH);
//                options.inSampleSize = 4;//宽度的1/4 高度的1/4
                //2.4开发 解码 ，实际生成Bitmap
                options.inJustDecodeBounds=false;
                //2.4.1Bitmap.Config的说明
                //要求解码器对于每一个采样的像素使用RGB_565存储方式
                //一个像素占用两个字节比ARGB_8888小了一半
                //如果解码器不能够使用指定配置，就自动使用ARGB_8888
                options.inPreferredConfig= Bitmap.Config.RGB_565;
                //2.4.2是一个过时的设置--可清除的
                options.inPurgeable = true;
                //2.5 使用设置采样的参数，进行解码，获取Bitmap
                ret=BitmapFactory.decodeByteArray(data,0,data.length,options);
               //data需要释放
                data=null;
            }
        }
        return ret;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null){
            //获取弱引用的对象可能为空
            ImageView imageView = imgViewReference.get();

            if (imageView!=null){
                //每一个图片都可以包含AsyncDrawable对象
                //用于处理图片错位
                Drawable drawable = imageView.getDrawable();
                if (drawable!=null && drawable instanceof AsyncDrawable){
                    //检测图片错位的情况
                    AsyncDrawable asyncDrawable=(AsyncDrawable)drawable;
                    ImgLoadTask task = asyncDrawable.getImageLoadTask();
                    //当前的ImageView内部包含的AsyncDrawable和当前的任务是对应的
                    //代表当前的任务可以设置图片
                    if (this==task){
                        imageView.setImageBitmap(bitmap);
                    }
                }else {
                    //不用检测图片错位的情况
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    /**
     * 计算图片二次采样的采样率，使用获取图片宽高之后的 Options 作为第一个参数；
     * 并且，通过请求的 宽度、高度尺寸，进行采样率的计算；
     *
     * @param options
     * @param reqWidth  请求的宽度
     * @param reqHeight 请求的高度
     * @return int 采样率
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        // 当请求的宽度、高度 > 0 时候，进行缩放，
        // 否则，图片不进行缩放；
        if (reqWidth > 0 && reqHeight > 0) {
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }

        return inSampleSize;
    }
}
