package com.four.voicerecord.httpclientdemo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by fengjianghui on 2015/11/23.
 */
public class HttpClientThread extends Thread {
    private String url;
    private String name;
    private String age;

    public HttpClientThread(String url) {
        this.url=url;
    }

    public HttpClientThread(String url, String name, String age) {
        this.url=url;
        this.name=name;
        this.age=age;
    }
    private void doHttpClientGet(){
        HttpGet httpGet=new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        try {
            //发送get请求，并得到一个httpResponse对象
            HttpResponse response=client.execute(httpGet);
            //首先判断一下请求的结果码
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //得到服务器的实体数据回来
                String content = EntityUtils.toString(response.getEntity());
                System.out.println("content------->"+content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doHttpClientPost() {

        HttpPost post = new HttpPost(url);
        HttpClient client = new DefaultHttpClient();
        //通过namevaluePair去存储数据
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("name",name));
        list.add(new BasicNameValuePair("age", age));
        try {
            post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            HttpResponse response=client.execute(post);
            if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
//        doHttpClientGet();
        doHttpClientPost();
    }
}
