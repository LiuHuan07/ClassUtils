package com.android.liuhuan.utilsclass.imgTwoCache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by LiuHuan on 2015/10/17.
 */
public final class HttpTools {
    //不能重写不能new
    private HttpTools(){

    }
    public static byte[] doGet(String url){
        byte[] ret=null;
        HttpURLConnection conn=null;
        if (url!=null){
            try {
                URL u=new URL(url);
                conn = (HttpURLConnection)u.openConnection();
                conn.connect();
                int code=conn.getResponseCode();
                if (code==200){
                    //TODO data赋值
                    InputStream fs=null;
                    try {
                        fs= conn.getInputStream();
                        //利用工具类读流
                        ret= StreamUtils.readStream(fs);
                        //保存文件到缓存中
                        FileCache.getInstance().save(url,ret);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        StreamUtils.close(fs);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
               StreamUtils.close(conn);
            }
        }
        return ret;
    }
}
