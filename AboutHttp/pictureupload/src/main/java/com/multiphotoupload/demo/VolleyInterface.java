package com.multiphotoupload.demo;

/**
 * Created by lenovo on 2017/6/16.
 */

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;


public abstract class VolleyInterface {
    //封装了Volley发送请求的两个Listener
    private Listener<String> listener;
    private ErrorListener errorListener;
    public VolleyInterface() {
    }
    //提供抽象方法作为Listener<String>中的回调方法
    public abstract void onMySuccess(String result);
    //提供抽象方法作为ErrorListener中的回调方法
    public abstract void onMyError(VolleyError arg0);

    //对外提供获取Listener<String>对象
    public Listener<String> loadingListener() {
        listener = new Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                // TODO Auto-generated method stub
                onMySuccess(arg0);
            }
        };
        return listener;
    }
    //对外提供获取ErrorListener对象
    public ErrorListener errorListener() {
        errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                onMyError(arg0);
            }
        };
        return errorListener;
    }
}