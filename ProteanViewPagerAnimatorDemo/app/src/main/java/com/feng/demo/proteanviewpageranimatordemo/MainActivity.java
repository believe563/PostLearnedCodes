package com.feng.demo.proteanviewpageranimatordemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private int[] mImageIds=new int[]{R.mipmap.page1,R.mipmap.page2,R.mipmap.page3};
    private List<ImageView> mImageViews=new ArrayList<>();
    private Map<Integer, View> viewsMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_viewpager);
        //为viewpager添加切换动画效果
        //这个方法专门为viewpager添加动画效果，但是只有在3.0之后才有效果
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
//        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
//        mViewPager.setPageTransformer(true,new RotateDownPageTransformer());
        //设置完adapter之后就可以显示viewpager了，但是没动画效果
        mViewPager.setAdapter(new PagerAdapter() {
            //instantiateItem是手动添加
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(mImageIds[position]);
                //设置图片的缩放类型，为了不让它变形
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mImageViews.add(imageView);
                return imageView;
            }



            //destroyItem是手动添加
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews.get(position));
            }


            @Override
            public int getCount() {
                return mImageIds.length;//需要改
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;//需要改
            }
        });
    }

}
