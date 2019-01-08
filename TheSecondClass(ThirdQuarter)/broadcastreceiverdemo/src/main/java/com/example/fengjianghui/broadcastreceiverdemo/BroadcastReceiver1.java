package com.example.fengjianghui.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by fengjianghui on 2015/10/18.
 */
public class BroadcastReceiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //做说明
        //直接用参数的intent
        String s=intent.getStringExtra("msg");
        System.out.println("receiver1收到消息："+s);
        //截断数据的传输，则在有序广播中receive2不能收到广播
        abortBroadcast();
        //用bundle进行数据的传输
        Bundle bundle=new Bundle();
        bundle.putString("test","广播处理的数据");
        //设置一个返回的值
        setResultExtras(bundle);
    }
}
