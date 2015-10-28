package com.android.liuhuan.utilsclass.log;

import android.util.Log;

/**
 * Created by LiuHuan on 2015/10/25.
 */
public final class MyLog {
    private static final boolean LOG_ON = false;
    private  MyLog(){

    }
    public static void d(String tag,String mag){
        if (LOG_ON){
            Log.d(tag, mag);
        }
    }

    public static void e(String tag,String mag){
        if (LOG_ON){
            Log.e(tag, mag);
        }
    }
    public static void i(String tag,String mag){
        if (LOG_ON){
            Log.i(tag, mag);
        }
    }
    public static void w(String tag,String mag){
        if (LOG_ON){
            Log.w(tag, mag);
        }
    }
}
