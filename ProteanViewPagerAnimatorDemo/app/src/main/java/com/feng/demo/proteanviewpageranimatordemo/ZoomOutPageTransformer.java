package com.feng.demo.proteanviewpageranimatordemo;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/*
* 一种切换效果
* 所有的属性动画都被ViewHelper替代，实现了ViewPagerCompAT，ViewPagerCompAT，
* 它是从ViewPager源码拷过来的
* 在ViewPagerCompat中还要去掉一个关于版本的条件，
* 然后在布局文件中将ViewPager换成ViewPagerCompat
* 在mainActivity中也要将相应的改为ViewPagerCompat
* 因为版本过低 androidstudio不能提供运行低版本的ViewPagerCompat。。
* */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
//            view.setAlpha(0);
            ViewHelper.setAlpha(view,0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
//                view.setTranslationX(horzMargin - vertMargin / 2);
                ViewHelper.setTranslationX(view,horzMargin - vertMargin / 2);
            } else {
//                view.setTranslationX(-horzMargin + vertMargin / 2);
                ViewHelper.setTranslationX(view,-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
//            view.setScaleX(scaleFactor);
            ViewHelper.setScaleX(view,scaleFactor);
//            view.setScaleY(scaleFactor);
            ViewHelper.setScaleY(view,scaleFactor);

            // Fade the page relative to its size.
//            view.setAlpha(MIN_ALPHA +
//                    (scaleFactor - MIN_SCALE) /
//                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            ViewHelper.setAlpha(view,MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
//            view.setAlpha(0);
            ViewHelper.setAlpha(view,0);
        }
    }
}