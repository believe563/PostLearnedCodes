package com.example.fengjianghui.asyncloadingdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by fengjianghui on 2015/10/7.
 */
public class ImageLoader {

    //这里的初始化将imageView和mUrl保存起来，便于以后验证
    private ImageView imageView;//初始化在showImageByThread里
    private String mUrl;//初始化在showImageByThread里

    //定义一个lruCache，类型是一个键值对，键是缓存图像的名字url(string),值是图像，用bitmap保存
    private LruCache<String,Bitmap> mlruCache;//接下来创建构造方法初始化cache
    //创建一个成员变量来存储listView
    private ListView mListView;
    //创建一个集合去管理asyncTask的task
    private Set<NewsAsyncTask1> mtask;

    public ImageLoader(){

    }
    public ImageLoader(ListView listView){

        //对listView进行初始化
        mListView=listView;
        mtask = new HashSet<>();

        //将一部分内存转化为我们需要的一部分缓存空间
        //获取当前应用所可用的内存的大小,即获取最大可用内存
        int maxMemory= (int) Runtime.getRuntime().maxMemory();
        //设定所需要的缓存的大小
        int cacheSize=maxMemory/4;
        //对LruCache进行初始化
        mlruCache = new LruCache<String,Bitmap>(cacheSize){//将缓存大小作为参数设定
            //匿名方式去重写方法sizeOf
            //用于获取每个持内存对象的大小
            //必须重写这个方法去加载正确的内存大小，不然的话它默认返回的是元素的个数
            //sizeOf在每次加入内存缓存时调用，所以就要在每次加入缓存的时候给它一个准确的大小
            //即bitmap的大小
            @Override   //。。。以内存看效率。。。。。
            protected int sizeOf(String key, Bitmap value) {//有图片等存入缓存时
//                return super.sizeOf(key, value);
                return value.getByteCount();
            }
        };
    }


    //将内容保存到cache中和从cache获取内容
    public void addBitmapToCache(String url,Bitmap bitmap){
        //增加之前先校验一下当前缓存是存在
        if(getBitmapFromCache(url)==null) {
            mlruCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        //lruCache底层是通过hashmap实现的
        return mlruCache.get(url);//通过key获得

    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //将bitmap取出来
            //if语句判断标志位是否相同，若相同，则将
            if (imageView.getTag().equals(mUrl)) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    //使用多线程的方式实现异步加载
    public void showImageByThread(ImageView imageView, final String url){
        this.imageView=imageView;
        mUrl=url;
        //一个简单的线程
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromUrl(url);
                //非主线程不能在主线程中更新UI，所以要通过handler做一个消息的传递
                //handler在上面定义
                //接下来将bitmap以消息的方式传送出去，让handler进行处理
                Message message=Message.obtain();
                //通过上述方式创建的message可以使用现有的以及回收掉的message，提高message的使用效率
                message.obj=bitmap;
                //用handler将带有bitmap的message发送出去，
                mHandler.sendMessage(message);
                // 然后在handler中将message中包含的bitmap取出来，然后将bitmap设置为所对应的imageview
            }
        }.start();
    }

    //从url获取bitmap
    public Bitmap getBitmapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            //凡是跟网络相关的大部分都要用try-catch去捕获异常
            URL url = new URL(urlString);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            //将connection包装一下，这样来获取一个inputStream对象
            is= new BufferedInputStream(connection.getInputStream());
            //将inputStream转化为解析为bitmap
            bitmap = BitmapFactory.decodeStream(is);
            //将资源释放
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //将inputStream释放
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public void showImageByAsyncTask(ImageView imageView,String url) {
        //先判断缓存中是否已经有了url所对应的图片
        Bitmap bitmap = getBitmapFromCache(url);
        //若为空，则缓存中没有这张图片，进行下载
        if (bitmap == null) {
            //加入滚动监听后要改成
            imageView.setImageResource(R.mipmap.ic_launcher);
//            new NewsAsyncTask1(imageView, url).execute(url);
        } else {
            //直接使用bitmap（在主线程，可以直接写），提高用户体验
            imageView.setImageBitmap(bitmap);
        }

    }
    private  class NewsAsyncTask1 extends AsyncTask<String,Void,Bitmap>{

        private ImageView imageView;
        private String url;

        public NewsAsyncTask1(String url){
            this.url=url;
        }
        public NewsAsyncTask1(ImageView imageView,String url){
            this.imageView=imageView;
            this.url=url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            //从网络上获取图片
            Bitmap bitmap = getBitmapFromUrl(params[0]);
            //这里需要将bitmap保存到cache
            if (bitmap!=null) {//确实下载到图片
                String url=params[0];
                //将不在缓存的图片加入缓存
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {//将bitmap设置给imageView
            super.onPostExecute(bitmap);
            imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
            //从mtask中remove掉当前的asyncTask
            mtask.remove(this);
                //这个是没有一次加载图片时的加载方法
//            if (imageView.getTag().equals(url)) {
//                imageView.setImageBitmap(bitmap);
//            }
        }
    }

    //用来加载start到end中的所有图片
    public void loadImages(int start,int end){
        for (int i=start;i<end;i++) {
            String url = NewsAdapter.URLS[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap==null) {
                //第一个参数不能是imageView了，因为加载的是一个整体，所以将imageView参数去掉
//                new NewsAsyncTask1(imageView, url).execute(url);
                NewsAsyncTask1 task = new NewsAsyncTask1(url);
                task.execute(url);
                mtask.add(task);//将task存到集合里 用来做统一管理，内容滚动时设置为统一停止
            }
            else {
                //通过listViewde的findViewwithTag从listView中找到imageview
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    public void cancelAllTasks(){
        if (mtask!=null){
            for (NewsAsyncTask1 task:mtask
                 ) {
                task.cancel(false);
            }
        }
    }
}
