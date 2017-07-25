package com.enation.javashop.connectview.logic;

import android.graphics.Bitmap;

/**
 * Umeng工具类，接口
 */

public interface UmengControl {

    /**
     * 初始化接口
     */
    interface UmengWebInit{
        UmengControl.UmengConfig setUrl(String url);
    }

    /**
     * 配置接口
     */
    interface UmengConfig{
        UmengControl.UmengConfig setTitle(String text);
        UmengControl.UmengConfig setText(String text);
        UmengControl.UmengConfig setImage(Bitmap bitmap);
        void go();
    }
}
