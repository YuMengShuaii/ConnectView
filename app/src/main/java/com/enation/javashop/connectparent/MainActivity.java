package com.enation.javashop.connectparent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.enation.javashop.connectview.logic.UmengLogin;
import com.enation.javashop.connectview.utils.ConnectViewLogger;
import com.enation.javashop.connectview.utils.Utils;
import com.enation.javashop.connectview.widget.ConnectView;

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
                connectView.addConnectItem(R.drawable.umeng_socialize_qq, "测试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.toastS(getBaseContext(),"sadads");
                    }
                });
            }
        });
    }
}
