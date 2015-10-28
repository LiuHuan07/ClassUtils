package com.android.liuhuan.utilsclass.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by LiuHuan on 2015/10/23.
 */
/*
* 常用加密工具，包含<br/>
* <oL>
*     <li>Hex 编码</li>
* </ol>
* */
public final class EncryptUtil {
    private EncryptUtil(){

    }
    //------------------------------------------------------
    /**
     * RSA加解密
     * @param data
     * @param key 可以是PublicKey,也可以是PrivateKey
     * @return
     */
    public static byte[] rsaEncrypt(int mode,byte[] data,Key key){
        byte[] ret=null;
        if (data!=null && data.length>0 && key!=null){
            //1.创建Cipher使用RSA
            try {
                Cipher cipher=Cipher.getInstance("RSA");
                cipher.init(mode,key);
                ret=cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    //--------------------------------------------------------
    //RSA密钥生成

    /**
     * 通过指定的密钥长度，生成非对称的密钥对
     * @param keySize 推荐使用1024，2048
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keySize){
        KeyPair ret=null;
        try {
            //1.准备生成
            KeyPairGenerator generator =
                    KeyPairGenerator.getInstance("RSA");
            //2.初始化，设置密钥长度
            generator.initialize(keySize);
            //3.生成，并返回
            ret=generator.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }
    //----------------------------------------------------------
    //AES 加密与解密（其中一种设置的方式，采用单一密码的情况）
    //1.
    public static byte[] aesEncrypt(byte[] data,byte[] keyData){
        return aesSingle(Cipher.ENCRYPT_MODE,data,keyData);
    }
    public static byte[] aesDecrypt(byte[] data,byte[] keyData){
        return aesSingle(Cipher.DECRYPT_MODE,data,keyData);
    }

    public static byte[] aesSingle(int mode,byte[] data,byte[] keyData){
        byte[] ret=null;
        if (data!=null
                && data.length>0
                && keyData!=null
                && keyData.length==16){//128bit
            try {
                Cipher cipher=Cipher.getInstance("AES");
                //AES 方式1，单一密码的情况,不同于DES
                SecretKeySpec keySpec = new SecretKeySpec(keyData,"AES");
                cipher.init(mode,keySpec);
                ret=cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    //2.AES带有加密模式的方法，形成的加密强度更高，需要iv参数
    public static byte[] aesEncrypt(byte[] data,byte[] keyData,byte[] ivData){
        return aesWithIv(Cipher.ENCRYPT_MODE, data, keyData,ivData);
    }
    public static byte[] aesDecrypt(byte[] data,byte[] keyData,byte[] ivData){
        return aesWithIv(Cipher.DECRYPT_MODE,data,keyData,ivData);
    }
    /**
     *
     * @param mode
     * @param data
     * @param keyData
     * @param ivData 用于AES/CBC/PKCSPadding这个带有加密模式的算法
     * @return
     */
    private static byte[] aesWithIv(int mode,byte[] data,byte[] keyData,byte[] ivData){
        byte[] ret=null;
        if (data!=null && data.length>0
                && keyData!=null && keyData.length==16
                && ivData!=null && ivData.length==16){
            //支持的加密的模式：
            //AES/CBC/PKCS5Padding
            //AES/ECB/PKCS5Padding
            try {
                Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
                //密码部分还是设置成"AES"即可
                SecretKeySpec keySpec=new SecretKeySpec(keyData,"AES");
                //准备IV参数，用于支持CBC或者ECB模式
                IvParameterSpec iv=new IvParameterSpec(ivData);
                //设置密码以及iv参数
                cipher.init(mode,keySpec,iv);
                ret=cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    //--------------------------------------------------------
    //  DES 加密与解密
    private static byte[] des(int mode,byte[] data,byte[] keyData){
        byte[] ret=null;
        if (data!=null
                && data.length>0
                && keyData!=null
                && keyData.length==8){
            try {
                //1.所有的加解密都用到Cipher
                Cipher ciper = Cipher.getInstance("DES");
                //3. 准备key对象
                //3.1 Des使用DesKeySpec
                DESKeySpec keySpec = new DESKeySpec(keyData);
                //3.2DESKeySpec于要转换成key对象才可以使用
                //需要使用SecretKeyFactory来处理
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                //3.3生成key对象
                SecretKey key = keyFactory.generateSecret(keySpec);
                //2.设置Cipher是加密还是解密就是模式
                //同时对于对称加密还需要设置密码key对象
                //参数2使用key对象
                ciper.init(mode, key);
                //4.加密
                //dofFinal()可以设置字节数组作为待加密的
                //返回值就是最终的加密
                ret = ciper.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    public static byte[] desEncrypt(byte[] data,byte[] keyData){

        return des(Cipher.ENCRYPT_MODE, data, keyData);
    }

    public static byte[] desDecrypt(byte[] data,byte[] keyData){

        return des(Cipher.DECRYPT_MODE,data,keyData);
    }

    //----------------------------------------------------------
    /**
     * 将字节数组转换为字符转
     * 一个字节会形成两个字符，最终长度，是原始数据的2倍
     * @param data
     * @return
     */
    public static String toHex(byte[] data){
        String ret=null;
        //TODO 将字节数组转换为字符串
        if (data!=null && data.length>0){
            StringBuffer sb=new StringBuffer();
            for (byte b:data){
                //分别获取高四位和低四位的内容，将两个数据转为字符
                int h=(b>>4)&0x0f;
                int l=b&0x0f;
                char ch,cl;
                if (h>9){//0x0a~0x0f
                    ch=(char)('A'+(h-10));
                }else{
                    ch=(char)('0'+h);
                }
                if (l>9){
                    cl=(char)('A'+(l-10));
                }else{
                    cl=(char)('0'+l);
                }
                sb.append(ch).append(cl);
            }
            ret=sb.toString();
        }
        return ret;
    }

    public static byte[] fromHex(String str){
        byte[] ret=null;
        //TODO 将Hex编码的字符串，还原为原始的字节数组
        if(str!=null){
            int len=str.length();
            char[] chs=str.toCharArray();
            if (len>0 && len%2==0){

                ret=new byte[len/2];
                for (int i=0,j=0;i<len-1;i+=2){
                    char ch=chs[i];
                    char cl=chs[i+1];
                    int ih=0,il=0,v=0;

                    if(ch>='A' && ch<='F'){
                        ih=10+(ch-'A');
                    }else if (ch>='a' && ch<='f'){
                        ih=10+(ch-'a');
                    }else if (ch>='0' && ch<='9'){
                        ih=ch-'0';
                    }
                    if(cl>='A' && cl<='F'){
                        il=10+(cl-'A');
                    }else if (cl>='a' && cl<='f'){
                        il=10+(cl-'a');
                    }else if (cl>='0' && cl<='9'){
                        il=cl-'0';
                    }
                    v=((ih & 0x0f)<<4)|(il & 0x0f);
                    //赋值
                    ret[j++]=(byte)v;

                }
            }
        }
        return ret;
    }
}
