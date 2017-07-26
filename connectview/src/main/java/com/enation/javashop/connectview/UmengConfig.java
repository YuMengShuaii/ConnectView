package com.enation.javashop.connectview;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by LDD on 17/7/26.
 */

public class UmengConfig {

    /**
     * 初始化微信
     * @param key   微信ID
     * @param secret 微信Secret
     */
    public static void initWechat(String key,String secret){
        PlatformConfig.setWeixin(key,secret);
    }

    /**
     * 初始化QQ
     * @param key   QQID
     * @param secret QQSecret
     */
    public static void initQQ(String key,String secret){
        PlatformConfig.setQQZone(key,secret);
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

}
