package com.example.fengjianghui.asynctaskdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by fengjianghui on 2015/10/1.
 */
public class ImageTest extends Activity {
    private ImageView imageView;
    private ProgressBar progressBar;
    //图片地址
    private static String URL="http://desk.3987.com/wall/31987_7.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //设置床底进去的参数
        new MyAsyncTask().execute(URL);
    }
    //第一个参数是传入参数URL，
    //第二个参数  不需要返回一个进度，所以为void
    //第三个参数，返回的参数 为Bitmap
    class MyAsyncTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //在异步加载之前显示进度条
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         *
         * @param params 是一个可变长的数组
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            //获取传递进来的参数
            String URL=params[0];
            Bitmap bitmap=null;
            //定义网络连接对象
            URLConnection urlConnection;
            //用于获取数据的输入流
            InputStream is;
            //访问网络的操作，是一个耗时操作
            try {
                urlConnection=new URL(URL).openConnection();
                //获取输入流
                is=urlConnection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                //将输入流解析成bitmap
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将bitmap作为返回值
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //doInBackground返回的Bitmap到了这里的参数
            //在这里可以操作UI，把图像设置给view
            progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
        }
    }
}
