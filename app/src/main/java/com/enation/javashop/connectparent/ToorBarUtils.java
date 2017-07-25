package com.enation.javashop.connectparent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by LDD on 17/7/25.
 */

public class ToorBarUtils {
    public static void setToolbar(final AppCompatActivity activity, Toolbar toolbar, Toolbar.OnMenuItemClickListener listener){
        initStatusBar(activity);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });
        if (listener!=null){
            toolbar.setOnMenuItemClickListener(listener);
        }
        activity.getSupportActionBar().setHomeButtonEnabled(true); //显示小箭头
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayUseLogoEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public static String getRunningActivityName(Activity activity) {
        String contextString = activity.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    private static void initStatusBar(AppCompatActivity activity){
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperAPI21(activity);
        }else{
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                setStatusBarUpperAPI19(activity);
            }
        }
    }

    // 5.0版本以上
    private static void setStatusBarUpperAPI21(AppCompatActivity activity) {
        Window window = activity.getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewGroup mContentView = (ViewGroup)activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }
    // 4.4 - 5.0版本
    private static void setStatusBarUpperAPI19(AppCompatActivity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        //移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null &&
                statusBarView.getLayoutParams().height == getStatusHeight(activity)) {
            mContentView.removeView(statusBarView);
        }
        //不预留空间
        if (mContentView.getChildAt(0) != null) {
            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
        }
    }

    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
}
