package com.fengjianghui.com.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volley_Get();
//        volley_Post();
    }

    private void volley_Post() {
        String url = "http://10.0.2.2:8080/VolleyDemoTest/Test?";
        //post不能在这里传递消息，需要单独实现传递参数的方法

//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener() {
//
//            @Override
//            public void onResponse(Object o) {
//                Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("number", volleyError.toString());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> hashMap = new HashMap<String,String>();
//                hashMap.put("action", "123");
//                return hashMap;
//            }
//        };


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashmap = new HashMap<>();
                hashmap.put("action", "123");
                return hashmap;
            }
        };
        request.setTag("abcPost");
        MyApplication.getHttpQueues().add(request);

    }

    private void volley_Get() {
//        String url = "http://10.0.2.2:8080/ShortRentServer/LoginVertifyServlet?phoNum=123456789";
        String url = "http://10.0.2.2:8080/VolleyDemoTest/Test?action=123";

//        1
//        //参数：请求方式、请求的接口地址、请求成功的回调、请求失败的回调
//        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//        //为该请求对象设置一个Tag标签，这样在加入全局队列后，可以通过tag标签对该请求进行寻找
//        request.setTag("abcGet");
//        MyApplication.getHttpQueues().add(request);

//        2
//        JsonObjectRequest有五个参数，相比上面的StringRequest多了中间的JsonObject（请求的参数），
//        因为是使用get方式传递，字段都在url中传递，所以中间的参数就为null
//        使用post时需要使用附带请求参数的object
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getApplicationContext(), volleyError.toString() + "!!!!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        request.setTag("abcGet");
//        MyApplication.getHttpQueues().add(request);
        VolleyRequest.RequestGet(this, url, "abcGet", new VolleyInterface(this, VolleyInterface.listener, VolleyInterface.errorListener) {
            @Override
            public void onMySuccess(String result) {

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMyError(VolleyError volleyError) {

                Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("abcPost");//volley生命周期的联动
    }
}
