package com.feng.demo.zidingyiviewpager;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2016/4/12.
 */
/*
* 自定义的viewpager切换动画
* */
public class RotateDownPageTransformer implements ViewPager.PageTransformer{
    private static final float MAX_ROTATE=20f;
    private float mRot;
    @Override
    public void transformPage(View page, float position) {
        int pageWidth=page.getWidth();
        if (position < -1) {
            ViewHelper.setRotation(page,0);
        }else if (position <= 0) {
            mRot=MAX_ROTATE*position;
            ViewHelper.setPivotX(page,pageWidth/2);
            ViewHelper.setPivotY(page, page.getMeasuredHeight());//设置原始测量的高度
            ViewHelper.setRotation(page,mRot);
        }else if (position <= 1) {
            mRot = position * MAX_ROTATE;
            ViewHelper.setPivotX(page, pageWidth / 2);
            ViewHelper.setPivotY(page, page.getMeasuredHeight());
            ViewHelper.setRotation(page, mRot);
        } else {
            ViewHelper.setRotation(page,0);
        }
    }
}
