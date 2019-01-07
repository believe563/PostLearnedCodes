package com.four.voicerecord.httpurlconnectiondemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fengjianghui on 2015/11/19.
 */
public class HttpThread extends Thread {
    private String url;
    private WebView webView;
    private Handler handler;
    private ImageView imageView;

    HttpThread(String url,WebView webView,Handler handler){
        this.url=url;
        this.webView=webView;
        this.handler=handler;
    }

    HttpThread(String url, ImageView imageView, Handler handler) {
        this.url=url;
        this.imageView=imageView;
        this.handler=handler;
    }
    @Override
    public void run() {
        URL httpUrl=null;
        File directory=null ;
        try {
            httpUrl = new URL(url);
            //拿到connection对象
            HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
            //超时等待的时间
//            conn.setReadTimeout(5000);
            //网页请求的方式
            conn.setRequestMethod("GET");
            //设置为true时就可以拿到一个输入流
            conn.setDoInput(true);
            InputStream in=conn.getInputStream();
            //把图片下载到本地,先判断有无id卡
            //内存卡是否挂载
            FileOutputStream out;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //拿到根目录
                File parent = Environment.getExternalStorageDirectory();
                String myDownLoadPicture=String.valueOf(System.currentTimeMillis());
                directory = new File(parent, myDownLoadPicture);
                out = new FileOutputStream(directory);
                byte[] bytes=new byte[2*1024];
                int len;
                if (out!=null){
                    while ((len=in.read(bytes))!=-1) {
                        out.write(bytes, 0, len);
                    }
                }
                final Bitmap bitmap = BitmapFactory.decodeFile(directory.getAbsolutePath());
                    handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }


//            //作为缓冲
//            final StringBuffer sb = new StringBuffer();
//            //创建一个包装类bufferedReader
//            //InputStreamReader把一个字符流转化成字节流
//            //bufferedReader就是页面信息的流的数据
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String string;
//            while ((string=reader.readLine())!=null) {
//                sb.append(string);
//            }
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    webView.loadData(sb.toString(),"text/html;charset=utf-8",null);
//                }
//            });
        } catch (MalformedURLException e) {//解析错误异常
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.run();
    }
}
