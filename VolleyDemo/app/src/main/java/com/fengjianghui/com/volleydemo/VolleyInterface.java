package com.fengjianghui.com.volleydemo;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by lenovo on 2017/6/7.
 */

public abstract class VolleyInterface {

    public static Context context;
    public static Response.Listener<String> listener;
    public static Response.ErrorListener errorListener;

    public VolleyInterface(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this.context=context;
        this.listener=listener;
        this.errorListener=errorListener;
    }

    public Response.Listener<String> loadingListener() {
        listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                onMySuccess(s);
            }
        };
        return listener;
    }

    public Response.ErrorListener errorListener() {
        errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyError(volleyError);
            }
        };
        return errorListener;
    }

//    因为输出回调应该是在调用的时候输出，
//    而不是在自定义类的时候输出，
//    所以需要创建两个抽象类，
//    用来在请求的时候输出

    public abstract void onMySuccess(String result);
    public abstract void onMyError(VolleyError volleyError);
}
