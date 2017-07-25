package com.enation.javashop.connectparent;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.enation.javashop.connectview.logic.UmengLogin;
import com.enation.javashop.connectview.logic.UmengShare;
import com.enation.javashop.connectview.utils.ConnectViewLogger;
import com.enation.javashop.connectview.utils.Utils;
import com.enation.javashop.connectview.widget.ConnectView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ConnectView connectView;
    private TextView button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView = (ConnectView) findViewById(R.id.connectview);
        connectView.initConnect(this);
        connectView.setConnectListener(UmengLogin.QQ, new UmengLogin.ConnectListener() {
            @Override
            public void success(Map<String, String> data) {

            }
        });
        connectView.setConnectListener(UmengLogin.WEIBO, new UmengLogin.ConnectListener() {
            @Override
            public void success(Map<String, String> data) {

            }
        });
        connectView.setConnectListener(UmengLogin.WECHAT, new UmengLogin.ConnectListener() {
            @Override
            public void success(Map<String, String> data) {

            }
        });

        button = (TextView) findViewById(R.id.add);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UmengShare.init(MainActivity.this,SHARE_MEDIA.SMS)
                        .setUrl("http://www.baidu.com")
                        .setImage(BitmapFactory.decodeResource(getResources(),R.drawable.umeng_socialize_qq))
                        .setText("ada")
                        .setTitle("sada")
                        .go();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        connectView.initResult(this,requestCode,resultCode,data);
    }
}
