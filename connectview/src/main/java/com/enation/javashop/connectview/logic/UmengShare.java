package com.enation.javashop.connectview.logic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import com.enation.javashop.connectview.utils.Utils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

/**
 * Umeng分享工具类
 */

public class UmengShare implements UmengControl.UmengInit,UmengControl.UmengWebConfig,UmengControl.UmengImageConfig,UmengControl.UmengVideoShare,UmengControl.UmengMusicShare{

    /**
     * 分享行为
     */
    private ShareAction shareAction;

    /**
     * UmengApi
     */
    private static  UmengShare umeng;

    /**
     * Web分享对象
     */
    private UMWeb   web ;

    /**
     * Video分享对象
     */
    private UMVideo video;

    /**
     * 音乐分享对象
     */
    private UMusic music;

    /**
     * 调用者上下文
     */
    private Activity activity;

    /**
     * 初始化分享对象
     * @param activity 调用者上下文
     * @return self
     */
    public static UmengControl.UmengInit Init(Activity activity){
       return Init(activity,new SHARE_MEDIA[0]);
    }

    /**
     * 初始化分享对象 并配置分享列表
     * @param activity   调用者Activity
     * @param shareList  分享配置列表
     * @return self
     */
    public static UmengControl.UmengInit Init(Activity activity,SHARE_MEDIA... shareList){
        if (umeng==null){
            umeng = new UmengShare();
        }
        umeng.config(activity,shareList);
        return umeng;
    }

    /**
     * 私有构造方法，防止该类被实例化
     */
    private UmengShare() {

    }

    /**
     * 分享配置，此方法可以配置参数列表
     * @param act        用户上下文
     * @param shareList  分享列表配置
     */
    private void config(Activity act,SHARE_MEDIA... shareList){
        if (shareList.length==0){
            shareList = new SHARE_MEDIA[]{SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA};
        }
        activity = act;
        shareAction = new ShareAction(activity)
                .setDisplayList(shareList)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        onUiToast("分享中！");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        onUiToast("分享成功！");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        onUiToast("分享失败！");
                        Log.e("ConnectView_ShareError","Error:"+throwable.toString()+throwable.getMessage()+throwable.getCause());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        onUiToast("分享取消！");
                    }
                });
    }

    /**
     * Toast提示，运行在UI线程
     * @param message
     */
    private void onUiToast(final String message){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.toastS(activity,message);
            }
        });
    }

    /**
     * 设置web分享标题
     * @param text 标题
     * @return self
     */
    @Override
    public UmengControl.UmengWebConfig setWebTitle(String text) {
        web.setTitle(text);
        return umeng;
    }

    /**
     * 设置web分享简介
     * @param text  简介
     * @return     self
     */
    @Override
    public UmengControl.UmengWebConfig setWebDescription(String text){
        web.setDescription(text);
        return umeng;
    }

    /**
     * 设置web分享缩略图
     * @param bitmap 图片Bitmap
     * @return self
     */
    @Override
    public UmengControl.UmengWebConfig setWebImage(Bitmap bitmap){
        UMImage umImage = new UMImage(activity,bitmap);
        web.setThumb(umImage);
        return umeng;
    }

    /**
     * 设置web分享缩略图
     * @param image 缩略图
     * @return self
     */
    @Override
    public UmengControl.UmengWebConfig setWebImage(UMImage image) {
        web.setThumb(image);
        return umeng;
    }

    /**
     * web分享初始化方式
     * @param url 分享网址
     * @return self
     */
    @Override
    public UmengControl.UmengWebConfig web(String url){
        web = new UMWeb(url);
        return umeng;
    }

    /**
     * 图片分享的初始化方式
     * @param image  图片
     * @return      self
     */
    @Override
    public UmengControl.UmengImageConfig image(UMImage image) {
        shareAction.withMedia(image);
        return umeng;
    }

    /**
     * 视频分享初始化
     * @param videoUrl 视频Url
     * @return self
     */
    @Override
    public UmengControl.UmengVideoShare video(String videoUrl) {
        video = new UMVideo(videoUrl);
        return umeng;
    }

    /**
     * 音乐分享初始化
     * @param muiscUrl 音乐url
     * @return  self
     */
    @Override
    public UmengControl.UmengMusicShare muisc(String muiscUrl) {
        music = new UMusic(muiscUrl);
        return umeng;
    }

    /**
     * 分享文字
     * @param text  文字
     */
    @Override
    public void textShare(String text) {
        new ShareAction(activity).withText(text).share();
    }

    /**
     * 开启web分享
     */
    @Override
    public void webShare(){
        shareAction.withMedia(web);
        shareAction.open();
    }

    /**
     * 设置图片描述
     * @param describe 描述
     * @return  self
     */
    @Override
    public UmengControl.UmengImageConfig setImageDescription(String describe) {
        shareAction.withText(describe);
        return umeng;
    }

    /**
     * 开启图片分享
     */
    @Override
    public void imageShare() {
        shareAction.open();
    }

    /**
     * 设置视频分享标题
     * @param title 标题
     * @return self
     */
    @Override
    public UmengControl.UmengVideoShare setVideoTitle(String title) {
        video.setTitle(title);
        return umeng;
    }

    /**
     * 设置视频描述
     * @param description 描述
     * @return self
     */
    @Override
    public UmengControl.UmengVideoShare setVideoDescription(String description) {
        video.setDescription(description);
        return umeng;
    }

    /**
     * 设置视频缩略图
     * @param url 图片url
     * @return self
     */
    @Override
    public UmengControl.UmengVideoShare setVideoImage(String url) {
        UMImage image = new UMImage(activity,url);
        video.setThumb(image);
        return umeng;
    }

    /**
     * 设置视频缩略图
     * @param image 图片
     * @return self
     */
    @Override
    public UmengControl.UmengVideoShare setVideoImage(UMImage image) {
        video.setThumb(image);
        return umeng;
    }

    /**
     * 开启视频分享
     */
    @Override
    public void videoShare() {
        shareAction.withMedia(video).share();
    }

    /**
     * 设置音乐标题
     * @param title 标题
     * @return self
     */
    @Override
    public UmengControl.UmengMusicShare setMusicTitle(String title) {
        music.setTitle(title);
        return umeng;
    }

    /**
     * 设置音乐缩略图
     * @param url 图片url
     * @return self
     */
    @Override
    public UmengControl.UmengMusicShare setMusicImage(String url) {
        music.setThumb(new UMImage(activity,url));
        return umeng;
    }

    /**
     * 设置音乐缩略图
     * @param image 图片
     * @return self
     */
    @Override
    public UmengControl.UmengMusicShare setMusicImage(UMImage image) {
        music.setThumb(image);
        return umeng;
    }

    /**
     * 设置音乐描述
     * @param text 描述
     * @return self
     */
    @Override
    public UmengControl.UmengMusicShare setMusicDescription(String text) {
        music.setDescription(text);
        return umeng;
    }

    /**
     * 设置音乐跳转网址
     * @param url 网址
     * @return self
     */
    @Override
    public UmengControl.UmengMusicShare setMuiscTargetUrl(String url) {
        music.setmTargetUrl(url);
        return umeng;
    }

    /**
     * 开启音乐分享
     */
    @Override
    public void musicShare() {
        shareAction.withMedia(music).open();
    }
}

