package com.enation.javashop.connectview.logic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import com.enation.javashop.connectview.utils.Utils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Umeng分享工具类
 */

public class UmengShare implements UmengControl.UmengWebInit,UmengControl.UmengConfig{

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
    private UMWeb web ;

    /**
     * 调用者上下文
     */
    private Activity activity;

    /**
     * 初始化微信
     * @param id   微信ID
     * @param scre 微信Secret
     */
    public static void initWechat(String id,String scre){
        PlatformConfig.setWeixin(id,scre);
    }

    /**
     * 初始化QQ
     * @param id   QQID
     * @param scre QQSecret
     */
    public static void initQQ(String id,String scre){
        PlatformConfig.setQQZone(id,scre);
    }

    /**
     * 初始化微博
     * @param key    微博Key
     * @param setret 微博Secret
     * @param url    微博AuthUrl
     */
    public static void initWeiBo(String key,String setret,String url){
        PlatformConfig.setSinaWeibo(key,setret,url);
    }

    /**
     * 初始化分享对象
     * @param activity 调用者上下文
     * @return self
     */
    public static UmengControl.UmengWebInit init(Activity activity){
        if (umeng==null){
            umeng = new UmengShare();
        }
        umeng.config(activity);
        return umeng;
    }

    /**
     * 初始化分享对象 并配置分享列表
     * @param activity   调用者Activity
     * @param shareList  分享配置列表
     * @return self
     */
    public static UmengControl.UmengWebInit init(Activity activity,SHARE_MEDIA... shareList){
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
     * 配置分享，添加监听 此方法采用默认分享列表
     * @param activity 调用者Activity
     */
    private void config(Activity activity){
        config(activity,new SHARE_MEDIA[0]);
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
     * 设置标题
     * @param text 标题
     * @return self
     */
    @Override
    public UmengControl.UmengConfig setTitle(String text) {
        web.setTitle(text);
        return umeng;
    }

    /**
     * 设置简介
     * @param text  简介
     * @return     self
     */
    @Override
    public UmengControl.UmengConfig setText(String text){
        web.setDescription(text);
        return umeng;
    }

    /**
     * 设置缩略图
     * @param bitmap 图片Bitmap
     * @return self
     */
    @Override
    public UmengControl.UmengConfig setImage(Bitmap bitmap){
        UMImage umImage = new UMImage(activity,bitmap);
        web.setThumb(umImage);
        return umeng;
    }

    /**
     * 设置Url
     * @param url 分享网址
     * @return self
     */
    @Override
    public UmengControl.UmengConfig setUrl(String url){
        web = new UMWeb(url);
        return umeng;
    }

    /**
     * 开启分享
     */
    @Override
    public void go(){
        shareAction.withMedia(web);
        shareAction.open();
    }

}
