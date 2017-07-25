package com.enation.javashop.connectview.logic;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.enation.javashop.connectview.utils.Utils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * 第三方登录工具类
 */

public class UmengLogin {

    /**
     * QQ标记
     */
    public static final int QQ = 1;

    /**
     * 微博标记
     */
    public static final int WEIBO = 2;

    /**
     * 微信标记
     */
    public static final int WECHAT = 3;

    /**
     * 授权监听
     */
    private static ConnectListener Blistener;

    /**
     * 调用页面上下文
     */
    private static Activity myActivity;

    /**
     * UmengApi
     */
    private static UMShareAPI shareAPI;

    /**
     * 限制Type输入
     */
    @IntDef({QQ, WECHAT,WEIBO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectType {
    }

    /**
     *  第三方登录
     * @param mActivity  调用Activity
     * @param type       登录方式
     * SHARE_MEDIA.QQ
     * SHARE_MEDIA.WEIXIN
     * SHARE_MEDIA.SINA
     * @param listener   监听接口
     */
    public static void login(Activity mActivity, SHARE_MEDIA type, ConnectListener listener){
        UmengLogin.myActivity=mActivity;
        Blistener = listener;
        shareAPI = UMShareAPI.get( myActivity );
        shareAPI.getPlatformInfo(myActivity,type, umAuthListener);
    }

    /**
     *  第三方登录监听
     */
    public static UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String,String> data) {

            Utils.toastS(myActivity,"授权成功");
            Blistener.success(data);
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Utils.toastS(myActivity,"授权失败");
            Log.e("data",t.toString()+"");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Utils.toastS(myActivity,"授权取消");
            Log.e("data","关闭授权");
        }
    };

    /**
     * 监听接口
     */
    public interface ConnectListener{
        void success(Map<String, String> data);
    }
}
