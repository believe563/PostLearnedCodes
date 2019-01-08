package com.feng.demo.proteanviewpageranimatordemo;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
/*
* 一种切换样式
* */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        //通过打印log日志找到position的规律
        Log.e("Tag","view="+view+",position="+position);
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);//这时的页面是看不见的，所以透明度设置为0

        } else if (position <= 0) { // [-1,0]//可以看见
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);//第一页没有透明度的变化
            view.setTranslationX(0);//第一页没有任何的偏移
            view.setScaleX(1);//第一页没有任何的缩放
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]//可以看见
            // Fade the page out.
            view.setAlpha(1 - position);//alpha是从透明到不透明的变化

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);//position一开始是1，然后就相当于一开始就向左移动了一个屏幕的宽度
            //然后偏移量逐渐减小，page逐渐向左移，相当于第二页一直在屏幕下方

            // Scale the page down (between MIN_SCALE and 1)//MIN_SCALE是0.75
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));//缩放从0.75到1的转变
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}