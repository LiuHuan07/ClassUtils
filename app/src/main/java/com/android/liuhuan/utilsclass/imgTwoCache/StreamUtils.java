package com.android.liuhuan.utilsclass.imgTwoCache;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.Objects;

/**
 * Created by LiuHuan on 2015/10/17.
 */
/*
* IO流的工具类
*
* */
public class StreamUtils {
    private StreamUtils(){

    }
    public static void close(Object stream){
        if (stream!=null){
            try {
                if (stream instanceof InputStream){
                    ((InputStream)stream).close();
                }else if (stream instanceof OutputStream){
                    ((OutputStream)stream).close();
                }else if (stream instanceof Reader){
                    ((Reader)stream).close();
                }else if (stream instanceof FileOutputStream){
                    ((FileOutputStream)stream).close();
                }else if (stream instanceof Writer){
                    ((Writer)stream).close();
                }else if (stream instanceof HttpURLConnection){
                    ((HttpURLConnection)stream).disconnect();
                }
            }catch (Exception e){

            }
        }
    }
    public static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream bos=null;
        byte[] ret=null;
        if (in!=null){
            bos = new ByteArrayOutputStream();

            byte[] buf=new byte[128];
            int len;
            while(true){
                //抛出异常
                len=in.read(buf);
                if (len==-1){
                    break;
                }
                bos.write(buf, 0, len);
            }
            //注意 buf 必须进行 = null的操作
            //减少内存溢出的可能性
            buf=null;
            ret=bos.toByteArray();
            bos.close();
        }
        return ret;

    }
}
