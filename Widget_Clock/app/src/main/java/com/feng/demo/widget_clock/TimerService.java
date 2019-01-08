package com.feng.demo.widget_clock;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/4/27 0027.
 * 当把widget添加到桌面时，启动服务，动态更改时间
 */
public class TimerService extends Service {
    private Timer timer;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            /*在这里更新widget这个View*/
            @Override
            public void run() {
                updateViews();
            }
        },0,1000);//period周期
    }

    private void updateViews() {
        String time = sdf.format(new Date());
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget);
        rv.setTextViewText(R.id.tv,time);

        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn = new ComponentName(getApplicationContext(), WidgetProvider.class);//这里的WidgetProvider是想要通知的类
        manager.updateAppWidget(cn,rv);//执行这个方法的时候就可以通知appwidgetprovider去执行刷新操作
        //就会去实时去刷新节目更新时间
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        timer=null;
    }
}
