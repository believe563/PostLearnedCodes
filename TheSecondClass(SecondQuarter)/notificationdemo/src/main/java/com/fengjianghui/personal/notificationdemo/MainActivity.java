package com.fengjianghui.personal.notificationdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btn_send;
    private Button btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_send.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send://发送通知
                Toast.makeText(MainActivity.this, "发送", Toast.LENGTH_SHORT).show();
                sendNotification();
                break;
            case R.id.btn_cancel://取消通知
                Toast.makeText(MainActivity.this, "取消按钮", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 构造notification并发送到通知栏
     */
    public void sendNotification() {
        //构造notification
        Notification.Builder builder = new Notification.Builder(this);
        //设置通知的内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置手机状态栏提示信息
        builder.setTicker("这是notificationDemo发来的状态栏信息");
        //拖拽开手机之后的内容
        // 设置当前时间
        builder.setWhen(System.currentTimeMillis());
        //设置通知栏标题
        builder.setContentTitle("这是通知标题");
        //设置通知内容
        builder.setContentText("这是通知内容");
        //设置点击之后进入的页面 或者点击后的意图
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pIntent);

        //设置声音 光和震动等信息
//        builder.setDefaults(Notification.DEFAULT_SOUND);//设置提示声音
//        builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
//        builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动
        builder.setDefaults(Notification.DEFAULT_ALL);//同时设置以上三项
        Notification notification=builder.build();//android4.1以上时用
//        Notification notification=builder.getNotification();//android4.1以下时用
        //通过manaegr发送通知
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,notification);
    }
}
