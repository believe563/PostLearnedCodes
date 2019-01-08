package com.fengjianghui.com.volleyimagedemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lenovo on 2017/6/7.
 */

public class MyApplication extends Application {
    public static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());

    }

    public static RequestQueue getQueue() {
        return queue;
    }
}
