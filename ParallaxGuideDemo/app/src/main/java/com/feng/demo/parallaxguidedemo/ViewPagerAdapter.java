package com.feng.demo.parallaxguidedemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/4.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> views;
    private Context context;

    public ViewPagerAdapter(ArrayList<View> views,Context context){
        this.views=views;
        this.context=context;
    }
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }
}
