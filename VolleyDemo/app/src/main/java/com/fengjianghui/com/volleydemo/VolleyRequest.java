package com.fengjianghui.com.volleydemo;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by lenovo on 2017/6/7.
 * 对volley的简单的二次回调封装
 * 封装的是请求过程中请求成功或请求失败的回调
 * <p>
 * 自定义的请求管理
 */

public class VolleyRequest {
    public static StringRequest stringRequest;
    public static Context context;

    public VolleyRequest(StringRequest stringRequest,Context context) {
        this.stringRequest=stringRequest;
        this.context=context;
    }

    //自定义的get方式
    public static void RequestGet(Context context, String url, String tag, VolleyInterface volleyInterface) {
        MyApplication.getHttpQueues().cancelAll(tag);//首先把tag标签的请求取消掉，防止重复地请求
//        两个关键的监听方法通过创建抽象类来实现
        stringRequest = new StringRequest(Request.Method.GET, url, volleyInterface.loadingListener(), volleyInterface.errorListener());
        stringRequest.setTag(tag);
        MyApplication.getHttpQueues().add(stringRequest);
        MyApplication.getHttpQueues().start();
    }

    public static void RequestPost(Context context, String url, String tag, final Map<String, String> params, VolleyInterface volleyInterface) {
        MyApplication.getHttpQueues().cancelAll(tag);
        stringRequest=new StringRequest(url,volleyInterface.loadingListener(),volleyInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);//设置标签
        MyApplication.getHttpQueues().add(stringRequest);//添加队列
        MyApplication.getHttpQueues().start();//启动队列
    }
}
