package com.fengjianghui.com.volleyimagedemo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by lenovo on 2017/6/7.
 * 整个的缓存
 */

public class BitmapCache implements ImageLoader.ImageCache {
    public LruCache<String,Bitmap> cache;//创建Lrucache对象
    public int max=10*1024*1024;//定义缓存最大为10M，如果内存缓存超过10M，会自动回收

    public BitmapCache(){
        cache=new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };

    }

    @Override
    public Bitmap getBitmap(String s) {
        return cache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        cache.put(s, bitmap);
    }
}
