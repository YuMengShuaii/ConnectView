# ConnectView
## Install

### Grade

```bash
compile 'com.enation.geamtear.widget:ConnectView:1.0.3'
```
### Maven
```bash
<dependency>
  <groupId>com.enation.geamtear.widget</groupId>
  <artifactId>ConnectView</artifactId>
  <version>1.0.3</version>
  <type>pom</type>
</dependency>
```

### Lvy
```bash
<dependency org='com.enation.geamtear.widget' name='ConnectView' rev='1.0.3'>
<artifact name='ConnectView' ext='pom'/>
</dependency>
```


## 第三方登录组件

#### ConnectView基本使用
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

