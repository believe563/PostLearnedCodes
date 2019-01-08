package com.example.fengjianghui.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by fengjianghui on 2015/10/18.
 */
public class BroadcastReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getStringExtra("msg");
        System.out.println("receiver2收到消息：" + s);
//        abortBroadcast();普通广播不能被截断  所以这里截断广播没有用
        //getresultextras()的参数是是否要得到返回值
        Bundle bundle=getResultExtras(true);
        //得到字符串的值
        String s1 = bundle.getString("test");
        System.out.println("得到的处理结果是："+s1);
        //因为是普通广播，所以处理结果是null
    }
}
