package com.enation.javashop.connectview.utils;

import android.util.Log;

import com.umeng.socialize.Config;

/**
 * ConnectView Logger辅助类
 */

public class ConnectViewLogger {

    /**
     * Log标记
     */
    private static boolean isLog = false;

    /**
     * 开启Log
     */
    public static void openConnectDebugLogger(){
        isLog = true;
    }

    /**
     * Log日志
     * @param message 日志信息
     */
    public static void logE(String message){
        if (isLog){
            Log.e("ConnectView",message);
        }
    }

    /**
     * 开启友盟日志
     */
    public static void openUmengDebugLogger(){
        Config.DEBUG = true;
    }
}
