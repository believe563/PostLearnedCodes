package com.fengjianghui.com.volleyimagedemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String url="https://www.sogou.com/images/baidu/baiduicon.gif";

        ImageLoader loader=new ImageLoader(MyApplication.getQueue(),new BitmapCache());
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        networkImageView.setImageUrl(url, loader);

//        2 普通imageview设置缓冲和实时监听
//        //第一参数是请求队列，第二参数是imagecache，imagecache单独使用时起不到缓存效果，
//        //需要结合LruCache使用，先去定义一个实现ImageCache的类，在其中定义LruCache
//        ImageLoader loader=new ImageLoader(MyApplication.getQueue(),new BitmapCache());
//
//        //监听整个图片的加载过程
//        ImageLoader.ImageListener imageListener = loader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//        loader.get(url, imageListener,400,400);//有两个参数的和三个参数的，这里没有第三个控制长宽的变量，表示按原来的尺寸显示



        ////1 普通imageview的请求
////        String url="https://www.baidu.com/img/bdlogo.png";
//        String url = "https://www.sogou.com/images/baidu/baiduicon.gif";
//        //参数依次是url、图片解析成功的回调、允许的最大宽度和高度(设置为0时图片会以原图的方式展示出来)、
////        加载的图片的格式、加载失败的回调
//        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);
//            }
//        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                imageView.setImageResource(R.mipmap.ic_launcher);
//            }
//        });
//        MyApplication.getQueue().add(request);
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.iv_img);
        networkImageView = (NetworkImageView) findViewById(R.id.networkimageview);
    }

    public void outPutToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
