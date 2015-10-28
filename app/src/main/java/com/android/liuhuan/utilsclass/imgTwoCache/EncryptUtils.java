package com.android.liuhuan.utilsclass.imgTwoCache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by LiuHuan on 2015/10/17.
 */
public class EncryptUtils {
    private EncryptUtils(){

    }

    //将网址映射为文件名
    public static String md5(String stringContent){
        String ret = null ;

        try {
            //创建消息摘要，使用MD5算法
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] data=digest.digest(stringContent.getBytes());//生成字节数组
            //byte[] 每一个字节转换为十六进制表示法，并且拼起来是字符串
            ret=toHex(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return ret;
    }

    //将字节数组转换为16进制的字符串
    public static String toHex(byte[] data){
        String ret = null;
        if (data!=null && data.length>0){
            StringBuilder sb=new StringBuilder();
            for (byte b:data) {
                int v=b & 0x0FF;

                String hexString = Integer.toHexString(v);
                if (v>0x0F){
                    //0x3C---->"3C"
                    sb.append(hexString);
                }else {
                    sb.append("0").append(hexString);
                }
            }
            ret=sb.toString();
        }
        return ret;
    }
}
