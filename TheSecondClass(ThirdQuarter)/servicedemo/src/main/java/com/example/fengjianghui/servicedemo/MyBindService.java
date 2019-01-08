package com.example.fengjianghui.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by fengjianghui on 2015/10/22.
 * 继承service
 */
public class MyBindService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info","onCreate()------MyBindService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("info", "onDestroy()------MyBindService");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("info", "onUnbind()------MyBindService");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{
        //写想要返回的参数
        //把整个服务对象返回过去
        public MyBindService getService(){
            return MyBindService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("info", "onBind()------MyBindService");
        return new MyBinder();//返回一个binder类的数据
    }
    public void Play(){
        Log.i("info", "play()------MyBindService");
    }
    public void Pause(){
        Log.i("info", "Pause()------MyBindService");
    }
    public void Previous(){
        Log.i("info", "Previous()------MyBindService");
    }
    public void Next(){
        Log.i("info", "Next()------MyBindService");
    }
}
