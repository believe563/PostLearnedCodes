package com.example.fengjianghui.gallerydemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by fengjianghui on 2015/9/28.
 */
public class ImageAdapter extends BaseAdapter {
    private int[] res;
    private Context context;
    public ImageAdapter(int[] res,Context context){
        this.res=res;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return res[position];
    }

    @Override
    public long getItemId(int position) {//角标的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(res[position%res.length]);
        //所对应的缩略图的大小
        imageView.setLayoutParams(new Gallery.LayoutParams(200,150));
        //设置缩放,Fit_XY是横向拉伸
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
}
