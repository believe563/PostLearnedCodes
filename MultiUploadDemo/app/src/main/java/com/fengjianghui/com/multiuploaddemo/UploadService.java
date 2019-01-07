package com.fengjianghui.com.multiuploaddemo;

import android.os.Handler;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

/**
 * 采用HttpClient上传文件,支持多文件上传
 * @author jph
 * Date:2014.10.09
 */
public class UploadService {
    private static String url="http://192.168.43.145:8080/MultiUploadDemo/MultiUpload";
    //  private static String url="http://10.110.6.58:8080/ServerForUpload/ServletForUpload";
    public static final int UPLOAD_SUCCESS=0x123;
    public static final int UPLOAD_FAIL=0x124;
    private Handler handler;
    public UploadService(Handler handler) {
        this.handler=handler;
    }
    /**
     * @param params 请求参数，包括请求的的方法参数method如：“upload”，
     * 请求上传的文件类型fileTypes如：“.jpg.png.docx”
     * @param files 要上传的文件集合
     */
    public void uploadFileToServer(final Map<String, String> params, final ArrayList<File> files) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("number","进入run方法开头");
                try {
                    if (uploadFiles(url,params,files)) {
                        handler.sendEmptyMessage(UPLOAD_SUCCESS);//通知主线程数据发送成功
                    }else {
                        //将数据发送给服务器失败
                        Log.i("number","数据发送失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * @param url servlet的地址
     * @param params 要传递的参数
     * @param files 要上传的文件
     * @return true if upload success else false
     * @throws IOException
     */
    private boolean uploadFiles(String url, Map<String, String> params, ArrayList<File> files) {
        HttpClient client=new DefaultHttpClient();// 开启一个客户端 HTTP 请求
//        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost post = new HttpPost(url);//创建 HTTP POST 请求


        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//      builder.setCharset(Charset.forName("uft-8"));//设置请求的编码格式


//        ContentType contentType = ContentType.create("multipart/form-data", Charset.forName("UTF-8"));



        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
//        MultipartEntity multipartEntity = new MultipartEntity();

        int count=0;
        for (File file:files) {
//          FileBody fileBody = new FileBody(file);//把文件转换成流对象FileBody
//          builder.addPart("file"+count, fileBody);
            builder.addBinaryBody("file"+count, file);
//            multipartEntity.addPart("file"+count, new FileBody(file));
            count++;
        }
        builder.addTextBody("method", params.get("method"));//设置请求参数
        builder.addTextBody("fileTypes", params.get("fileTypes"));//设置请求参数
//        multipartEntity.addPart("method", params.get("method"));//设置请求参数
//        multipartEntity.addPart("fileTypes", new StringBody(params.get("fileTypes")));//设置请求参数

        //另一种打包方式
//        ArrayList<BasicNameValuePair> list=new ArrayList<>();
//        list.add(new BasicNameValuePair("method", params.get("method")));
//        list.add(new BasicNameValuePair("fileTypes",params.get("fileTypes")));




        HttpEntity entity = builder.build();// 生成 HTTP POST 实体


        Log.i("number","得到的method为："+params.get("method"));
        Log.i("number","得到的fileTypes为："+params.get("fileTypes"));
        Log.i("number","得到的builder为："+builder);
//        Log.i("number","得到的entity为："+multipartEntity);
//        post.setEntity(multipartEntity);//设置请求参数
        HttpResponse response = null;// 发起请求 并返回请求的响应
        try {
//        post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
        post.setEntity(entity);
         response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode()==200) {
            return true;
        }
        return false;
    }
}