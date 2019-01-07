package com.four.voicerecord.httpclientuploaddemo;

import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by fengjianghui on 2015-12-14.
 */
public class UploadThread extends Thread {
    private String url;
    private String fileName;

    public UploadThread(String url, String fileName) {
        this.url=url;
        this.fileName=fileName;
    }
    @Override
    public void run() {
        //用来执行
        HttpClient client = new DefaultHttpClient();
        //相当于以post方法传递参数
        HttpPost post = new HttpPost(url);
        //将要传递的part放在一个实体中
        MultipartEntity multipartEntity = new MultipartEntity();
        //获得要上传图片的路径
        File parent = Environment.getExternalStorageDirectory();
        File fileAbs=new File(parent,"a.jpg");
        //相当于一个part，封装了要上传的图片的信息
        FileBody fileBody = new FileBody(fileAbs);
        //将part放到实体中
        multipartEntity.addPart("file",fileBody);
        //说明post方法将要上传的实体
        post.setEntity(multipartEntity);
        
        try {
            HttpResponse response = client.execute(post);
            //如果上传成功就返回服务器返回的信息
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
