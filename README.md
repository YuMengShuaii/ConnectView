# ConnectView
##前言
`
本库内置了QQ，微信，微博，三个平台的分享以及第三方登录，以及分享，并提供了扩展接口。
    1.Power：本库内部使用的友盟，是对友盟的二次封装，Key，Secret，Manifest等相关配置直接参照友盟。
    2.平台：本库内置有微信，QQ，微博，可以根据需求进行相关配置，如果需要除去内置平台之外的其他平台，那么你只需要在自己项目中加入友盟该平台的相关sdk，比如我要支付宝分享或者三方登录，那么需要在自己项目中加入SocialSDK_alipay.jar，与libapshare.jar，如果需要短信分享，那么需要加入SocialSDK_sms.jar，以此类推。
`
## Install

### Grade

```bash
compile 'com.enation.geamtear.widget:ConnectView:1.0.4'
```
### Maven
```bash
<dependency>
  <groupId>com.enation.geamtear.widget</groupId>
  <artifactId>ConnectView</artifactId>
  <version>1.0.4</version>
  <type>pom</type>
</dependency>
```

### Lvy
```bash
<dependency org='com.enation.geamtear.widget' name='ConnectView' rev='1.0.4'>
<artifact name='ConnectView' ext='pom'/>
</dependency>
```


## 第三方登录组件

#### ConnectView基本使用

##### Xml配置
1. useQQ：       是否开启QQ第三方登录 
2. useWeChat：   是否开启了微信第三方登录
3. useSina：     是否开启了微博第三方登录
4. debugLogger： 是否开启日志打印
5. qqSrc:        是否自定义QQlogo
6. wechatSrc：   是否自定义微信Logo
7. sinaSrc：     是否自定义微博Logo

    
```
<com.enation.javashop.connectview.widget.ConnectView
        android:layout_width="match_parent"
        android:layout_height="70dp"
        app:useQQ="true"
        app:useSina="true"
        app:useWechat="true"
        app:qqSrc="@drawable/umeng_socialize_copyurl"
        app:wechatSrc="@drawable/umeng_socialize_copyurl"
        app:sinaSrc="@drawable/umeng_socialize_copyurl"
        android:layout_marginBottom="20dp"
        app:debugLogger="true"
        android:id="@+id/connectview"
        android:layout_alignParentBottom="true"
        >
    </com.enation.javashop.connectview.widget.ConnectView>
```
##### 代码配置
首先初始化上下文

```
/**初始化三方登录控件*/
 connectView.initConnect(this);
```
设置三方登陆监听


```
 /**设置登录监听，type为登录方式，内置三个平台分别使用，UmengLogin.QQ，UmengLogin.WEIBO，UmengLogin.WECHAT*/
connectView.setConnectListener(type, new UmengLogin.ConnectListener() {
            @Override
            public void success(Map<String, String> data) {

            }
            
            @Override
            public void error(String message) {
                                
            }
            
            @Override
            public void cancel() {

            }
        });
```

如果在Activity中调用该方法，则在onActivityResult方法中执行initResult方法，如果在Fragment方法中调用该方法那么需要在fragment的宿主Activity中的onActivityResult方法执行initResult方法。


```
    /**接收Activity返回数据*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        connectView.initResult(this, requestCode, resultCode, data);
    }
```

扩展：如果你需要除内置平台外其他平台的三方登录，可以使用以下方法向ConnectView中添加自定义Item，并添加自己的响应事件。


```
/**
 * 参数依次是：LOGO，提示文字，监听事件
 * UmengLogin是封装的第三方登录类
 */
connectView.addConnectItem(R.drawable.umeng_socialize_copy, "测试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UmengLogin.login(MainActivity.this, SHARE_MEDIA.ALIPAY, new UmengLogin.ConnectListener() {
                            @Override
                            public void success(Map<String, String> data) {
                                
                            }
                            
                            @Override
                            public void error(String message) {
                                                
                            }
                            
                            @Override
                            public void cancel() {
                
                            }
                            
                        });
                    }

                });
```

## 分享

**原本是不准备加分享的，但是UmengSDK中带有分享功能，顺便封装了一个工具类**


* 使用自定义分享列表
   
```
 UmengShare.Init(MainActivity.this, SHARE_MEDIA.SMS, SHARE_MEDIA.ALIPAY)
```

* 使用默认分享列表

```
UmengShare.Init(MainActivity.this)
```

* 使用web分享


```
                      UmengShare.Init(MainActivity.this)
                                .web("http://www.baidu.com")
                                .setWebImage(BitmapFactory.decodeResource(getResources(), R.drawable.umeng_socialize_qq))
                                .setWebDescription("test")
                                .setWebTitle("test")
                                .webShare();
```


* 使用video分享


```
                      UmengShare.Init(MainActivity.this)
                                .video("videourl")
                                .setVideoTitle("title")
                                .setVideoImage("imageurl")
                                .setVideoDescription("miaoshu")
                                .videoShare();

```

* 使用文字分享


```
                      UmengShare.Init(MainActivity.this)
                                .textShare("我是文字");

```

* 使用图片分享


```
                      UmengShare.Init(MainActivity.this)
                                .image(new UMImage(MainActivity.this,"url"))
                                .setImageDescription("测试")
                                .imageShare();
```

* 使用音乐分享

    
```
                      UmengShare.Init(MainActivity.this)
                                .muisc("muiscurl")
                                .setMuiscTargetUrl("url")
                                .setMusicDescription("miaoshu")
                                .setMusicImage("imageurl")
                                .setMusicTitle("title")
                                .musicShare();

```
## 第三方登录工具类

如果用户不想使用ConnectView又想使用三方登录，那么配置完平台参数后使用以下方法


```
 UmengLogin.login(MainActivity.this, SHARE_MEDIA.ALIPAY, new UmengLogin.ConnectListener() {
                            @Override
                            public void success(Map<String, String> data) {
                                
                            }
                            
                            @Override
                            public void error(String message) {
                                                
                            }
                            
                            @Override
                            public void cancel() {
                
                            }
                            
                        });

```
## 自定义Umeng配置类

不强制建议使用，只是添加了自己的配置入口，且只有内置平台


```

        /**初始化QQ参数*/
        UmengConfig.initQQ("QQkey","QQsec");
        /**初始化微信参数*/
        UmengConfig.initWechat("WecahtID","WechatSec");
        /**初始化微博参数*/
        UmengConfig.initWeiBo("SinaKey","Sinasec","Authurl");

```


