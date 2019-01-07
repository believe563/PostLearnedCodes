package com.example.bmobpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化BmobSDK
        Bmob.initialize(this,"19e982bdbfc26c4f8e1b122671f9c1f1");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);
//        调用上述两句就会在Bmob开放平台上的installation表中生成对应设备的信息
    }

    public void pushAll(View view) {
        BmobPushManager pushManager = new BmobPushManager(MainActivity.this);
        pushManager.pushMessageAll("test");//将test的这个文字推送给所有用户
    }

}
