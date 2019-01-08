package com.example.fengjianghui.broadcastreceiverdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    BroadcastReceiver3 bc3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void doClick(View v){
        switch (v.getId()){
            case R.id.send1://发送一条普通广播
                Intent intent=new Intent();
                intent.putExtra("msg","这是一条普通广播");
                //要配合action或者是catagory进行过滤
                intent.setAction("fengjianghui");
                //发送广播
                sendBroadcast(intent);
                break;
            case R.id.send2://发送一条有序广播
                Intent intent2=new Intent();
                intent2.putExtra("msg","这是一条有序广播");
                //要配合action或者是catagory进行过滤
                intent2.setAction("fengjianghui");
                //发送有序广播，第二个参数是权限
                sendOrderedBroadcast(intent2, null);
                break;
            case R.id.send3://发送一条异步广播
                Intent intent3=new Intent();
                intent3.putExtra("msg", "这是一条异步广播");
                //要配合action或者是catagory进行过滤
                intent3.setAction("stickyBroadcast");
                //发送有序广播，第二个参数是权限
                sendOrderedBroadcast(intent3, null);
                IntentFilter intentFilter = new IntentFilter("stickyBroadcast");
                bc3=new BroadcastReceiver3();
                registerReceiver(bc3, intentFilter);
                //还要加一个BROADCAST_STICKY的用户权限
                //因为消息长期粘滞没有销毁所以每点一次按钮就会增加一条广播，多显示一条消息
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bc3);
    }
}
