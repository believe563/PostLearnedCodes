package com.example.fengjianghui.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by fengjianghui on 2015/10/22.
 */
public class MyStartService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info", "Service------onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("info", "Service------onStartCommand()");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("info", "Service------onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("info", "Service------onBind()");
        return null;
    }
}
