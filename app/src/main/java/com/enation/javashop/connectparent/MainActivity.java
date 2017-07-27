package com.enation.javashop.connectparent;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.enation.javashop.connectview.UmengConfig;
import com.enation.javashop.connectview.logic.UmengLogin;
import com.enation.javashop.connectview.logic.UmengShare;
import com.enation.javashop.connectview.utils.ConnectViewLogger;
import com.enation.javashop.connectview.utils.Utils;
import com.enation.javashop.connectview.widget.ConnectView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ConnectView connectView;
    private Toolbar  toolbar;
    private TextView content;
    private TextView addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        /**初始化QQ参数*/
//        UmengConfig.initQQ("QQkey","QQsec");
//        /**初始化微信参数*/
//        UmengConfig.initWechat("WecahtID","WechatSec");
//        /**初始化微博参数*/
//        UmengConfig.initWeiBo("SinaKey","Sinasec","Authurl");
        initView();
        BindEvent();
    }

    private void initView() {
        connectView = (ConnectView) findViewById(R.id.connectview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        content = (TextView) findViewById(R.id.content);
        addItem = (TextView) findViewById(R.id.addItem);
    }

    private void BindEvent() {
        initToolBar();
        initConnectView();
        /**自定义第三方登录Item，可以在OnClick中调用 UmengLogin.Login方法实现三方登录，
         * 或者使用用户自己的逻辑，当用户的所有Item的宽度之和即将超过ConnectView的宽度时，Item添加方法将失效！*/
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        content.setText(readText("doc.txt"));
    }


    private void initToolBar() {
        ToorBarUtils.setToolbar(MainActivity.this, toolbar, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        /**使用自定义参数列表分享*/
                        UmengShare.Init(MainActivity.this, SHARE_MEDIA.SMS, SHARE_MEDIA.ALIPAY)
                                .web("http://www.baidu.com")
                                .setWebImage(BitmapFactory.decodeResource(getResources(), R.drawable.umeng_socialize_qq))
                                .setWebDescription("ada")
                                .setWebTitle("sada")
                                .webShare();
                        break;
                    case R.id.share2:
                        /**使用默认分享参数列表*/
                        UmengShare.Init(MainActivity.this)
                                .web("http://www.baidu.com")
                                .setWebImage(BitmapFactory.decodeResource(getResources(), R.drawable.umeng_socialize_qq))
                                .setWebDescription("ada")
                                .setWebTitle("sada")
                                .webShare();
                        break;
                }
                return false;
            }
        });
    }

    private void initConnectView() {
        /**初始化三方登录控件*/
        connectView.initConnect(this);

        /**设置QQ登录监听*/
        connectView.setConnectListener(UmengLogin.QQ, new UmengLogin.ConnectListener() {
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
        /**设置微博登录监听*/
        connectView.setConnectListener(UmengLogin.WEIBO, new UmengLogin.ConnectListener() {
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
        /**设置微信登录监听*/
        connectView.setConnectListener(UmengLogin.WECHAT, new UmengLogin.ConnectListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    /**接收Activity返回数据*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        connectView.initResult(this, requestCode, resultCode, data);
    }

    /**读取text文本*/
    private String readText(String filename){
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
}
