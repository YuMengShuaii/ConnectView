package com.enation.javashop.connectview.logic;

import android.graphics.Bitmap;

import com.umeng.socialize.media.UMImage;

/**
 * Umeng工具类，接口
 */

public interface UmengControl {

    /**
     * 初始化接口
     */
    interface UmengInit{
        UmengWebConfig   web(String url);
        UmengImageConfig image(UMImage image);
        UmengVideoShare  video(String videoUrl);
        UmengMusicShare  muisc(String muiscUrl);
        void             textShare(String text);
    }

    /**
     * web分享配置接口
     */
    interface UmengWebConfig{
        UmengWebConfig setWebTitle(String text);
        UmengWebConfig setWebDescription(String text);
        UmengWebConfig setWebImage(Bitmap bitmap);
        UmengWebConfig setWebImage(UMImage image);
        void webShare();
    }

    /**
     * 图片分享接口
     */
    interface UmengImageConfig{
        UmengImageConfig setImageDescription(String describe);
        void imageShare();
    }

    /**
     * 视频分享接口
     */
    interface UmengVideoShare{
        UmengVideoShare setVideoTitle(String title);
        UmengVideoShare setVideoDescription(String description);
        UmengVideoShare setVideoImage(String url);
        UmengVideoShare setVideoImage(UMImage image);
        void videoShare();
    }

    /**
     * 音乐分享接口
     */
    interface UmengMusicShare{
        UmengMusicShare setMusicTitle(String title);
        UmengMusicShare setMusicImage(String url);
        UmengMusicShare setMusicImage(UMImage image);
        UmengMusicShare setMusicDescription(String text);
        UmengMusicShare setMuiscTargetUrl(String url);
        void  musicShare();
    }
}
