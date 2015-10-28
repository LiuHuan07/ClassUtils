package com.android.liuhuan.utilsclass.imgTwoCache;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LiuHuan on 2015/10/16.
 */
public class FileCache {
    private static FileCache ourInstance;

    public  static FileCache newInstance(Context context){

        if(context!=null){
            if (ourInstance==null){
                ourInstance= new FileCache(context);
            }else {
                throw new IllegalArgumentException("Context must be set");
            }
        }
        return ourInstance;

    }
    public static FileCache getInstance() {
        if (ourInstance==null){
            throw new IllegalStateException("newInstance invoke");
        }

        return ourInstance;
    }

    private Context context;

    private FileCache(Context context) {
        this.context=context;
    }

    //从文件存储加载对应网址的内容
    public byte[] load(String url){
        byte[] ret=null;
        if(url!=null){
            //1.获取并保存文件目录
            File cacheDir=null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)){
                cacheDir=context.getExternalCacheDir();
            }else {
                cacheDir=context.getCacheDir();//手机内部缓存
            }
            //映射文件名
            String fileName= EncryptUtils.md5(url);
            File targetFile = new File(cacheDir,fileName);

            if (targetFile.exists()){
                FileInputStream fs=null;
                try {
                    fs=new FileInputStream(targetFile);
                    ret= StreamUtils.readStream(fs);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    StreamUtils.close(fs);
                }
            }
        }
        return ret;
    }
    //保存对应网址到文件
    public void save(String url,byte[] data){
        //TODO 通过网址存文件
        if (url!=null && data!=null){
           //1.获取并保存文件目录
            File cacheDir=null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)){
                cacheDir=context.getExternalCacheDir();
            }else {
                cacheDir=context.getCacheDir();//手机内部缓存
            }
            //映射文件名
            String fileName= EncryptUtils.md5(url);
            File targetFile = new File(cacheDir,fileName);

            //IO操作
            FileOutputStream fos=null;
            try {
                fos=new FileOutputStream(targetFile);
                fos.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
               StreamUtils.close(fos);
            }
        }
    }
}
