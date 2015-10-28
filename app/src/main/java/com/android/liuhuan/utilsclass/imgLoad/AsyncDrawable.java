package com.android.liuhuan.utilsclass.imgLoad;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * 异步下载的Drawable 实际解决图片错位
 * 自身不需要设置图片之类
 */
public class AsyncDrawable extends BitmapDrawable {
    /*
    * 真正下载图片的部分
    * */
    private final WeakReference<ImgLoadTask> taskReference;
    /*
    * 模拟一个BitmapDrawable，这个Drawable对象就可以直接给ImageView对象了
    * */
    public AsyncDrawable(Resources res, Bitmap bitmap,ImgLoadTask loadTask) {
        super(res, bitmap);
        taskReference=new WeakReference<ImgLoadTask>(loadTask);

    }
    /*
    * 获取当前Drawable包含的异步任务
    * */
    public ImgLoadTask getImageLoadTask(){
        ImgLoadTask ret=null;
        ret = taskReference.get();
        return ret;
    }
}
