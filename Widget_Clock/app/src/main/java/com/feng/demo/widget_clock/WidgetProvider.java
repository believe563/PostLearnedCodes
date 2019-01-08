package com.feng.demo.widget_clock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/4/27 0027.
 * 类似于广播
 */
public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        //widget被从屏幕移除
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //最后一个widget被从屏幕移除执行
        context.stopService(new Intent(context, TimerService.class));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        //第一个widget添加到屏幕上执行
        //启动服务
        context.startService(new Intent(context, TimerService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //这个方法一般不被重写，因为无论操作哪个方法，都会调用onReceive
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //刷新widget执行
        //通过remoteView和AppWidgetManager执行更新操作
    }
}
