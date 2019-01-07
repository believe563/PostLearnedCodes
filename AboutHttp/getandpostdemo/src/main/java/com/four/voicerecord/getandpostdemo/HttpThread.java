package com.four.voicerecord.getandpostdemo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by fengjianghui on 2015/11/20.
 */
public class HttpThread extends Thread {
    String url;
    String name;
    String age;

    public HttpThread(String url, String name, String age) {
        this.url = url;
        this.name = name;
        this.age = age;
    }

    private void doGet() {//访问网络请求的代码
        //get方式只能通过url去传参,重新构造一个url才能重新去传参
        try {
            url = url + "?name=" + URLEncoder.encode(name,"utf-8")+ "&age=" + age;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //创建一个url
        try {
            Log.e("fengjianghui",""+name) ;
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            //读取服务器返回的信息
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            System.out.println("result!!!:" + sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void doPost() {
        try {
            //拿到当前系统默认的属性配置文件
            Properties properties=System.getProperties();
            //可以看到当前系统默认的配置信息
            properties.list(System.out);
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setReadTimeout(5000);
            OutputStream out = conn.getOutputStream();
            String content = "name=" +URLEncoder.encode(name,"utf-8")+ "&age=" + age;
            out.write(content.getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String result;
            while ((result = reader.readLine()) != null) {
                sb.append(result);
            }
            System.out.println("result=" + sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        doGet();
        doPost();
    }
}
