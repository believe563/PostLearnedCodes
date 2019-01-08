package com.example.fengjianghui.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fengjianghui on 2015/10/21.
 */
public class BroadcastReceiver3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("收到了异步广播");
    }
}
