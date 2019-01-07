package com.four.voicerecord.jsondemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fengjianghui on 2015/11/26.
 */
public class HttpImageView extends Thread {

    private ImageView imageView;
    private String url;
    private Handler handler;
    public HttpImageView(ImageView imageView,String url,Handler handler) {
        this.imageView=imageView;
        this.url = url;
        this.handler=handler;
    }
    @Override
    public void run() {
        InputStream in=null;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            in = conn.getInputStream();
            conn.setRequestMethod("POST");
//            conn.setReadTimeout(5000);
            final Bitmap bitmap = BitmapFactory.decodeStream(in);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.run();
    }
}
