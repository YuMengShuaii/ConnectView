package com.enation.javashop.connectview.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Utils工具类
 */

public class Utils {
    /**
     * 屏幕密度
     */
    public static float SCREEN_DENSITY = 0.0F;
    /**
     * 屏幕高度
     */
    public static int SCREEN_HEIGHT = 0;
    /**
     * 屏幕宽度
     */
    public static int SCREEN_WIDTH = 0;

    /**
     * Toast提示 3秒
     * @param context 上下文
     * @param message 提示信息
     */
    public static void toastS(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param context 上下文
     * @param dpValue dp值
     * @return        px值
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue  sp值
     * @param context  上下文
     * @return         px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param context    上下文
     * @param pxValue    px值
     * @return           dip值
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue   px值
     * @param context   上下文
     * @return          sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * dip转换sp
     * @param context 上下文
     * @param dpValue dp值
     * @return        sp值
     */
    public static int dip2sp(Context context, float dpValue){
        return Utils.px2sp(context,dip2px(context,dpValue))-10;
    }

    /**
     * 初始化屏幕信息
     * @param context 上下文
     */
    public static void initScreenInfo(Context context){
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(localDisplayMetrics);
        SCREEN_DENSITY = localDisplayMetrics.density;
        SCREEN_HEIGHT = localDisplayMetrics.heightPixels;
        SCREEN_WIDTH = localDisplayMetrics.widthPixels;
    }
}
