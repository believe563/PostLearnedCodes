package com.fengjianghui.com.volleydemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * step 1:建立一个全局的请求队列
 * Created by lenovo on 2017/5/25.
 */

public class MyApplication extends Application {

    public static RequestQueue queues;//创建一个请求队列

    /**
     * 重写这个方法
     */
    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());//实例化请求队列
    }

    /**
     * 创建一个“暴露”方法，用于外界方便地获取请求队列
     * 要记得在Manifest清单文件中重新注册Application(重新写name属性)
     * 和加入Internet权限（网络权限）
     * @return 全局请求队列
     */
    public static RequestQueue getHttpQueues() {
        return queues;
    }
}
