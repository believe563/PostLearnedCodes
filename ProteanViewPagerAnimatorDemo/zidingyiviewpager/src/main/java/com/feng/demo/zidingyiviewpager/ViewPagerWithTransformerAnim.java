package com.feng.demo.zidingyiviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ViewPagerWithTransformerAnim extends ViewPager {
    private View mLeft;
    private View mRight;
    private float mTrans;
    private float mScale;
    private final float MIN_SCALE=0.5f;//最大值是1


    //装载每一个子view的位置和内容的map
    private Map<Integer, View> mChildren = new HashMap<>();
    private float MIN_ALPHA=0.5f;

    ViewPagerWithTransformerAnim(Context context) {
        super(context);
    }
    public ViewPagerWithTransformerAnim(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        //打印当前position寻找获得left子view和right子view的方法
        Log.e("LOG", "position=" + position + ",offset=" + offset + ",offsetPixels=" + offsetPixels);
        //通过获得当前item来寻找获得left子view和right子view的方法
//        Log.e("LOG", "getCurrentItem()=" + getCurrentItem() + ",getChildCount()=" + getChildCount());

        mLeft = mChildren.get(position);
        mRight = mChildren.get(position + 1);

        //为leftview添加动画效果
        animStack(mLeft,mRight,offset,offsetPixels);


        super.onPageScrolled(position, offset, offsetPixels);//这个不能少
    }

    private void animStack(View left, View right, float offset, int offsetPixels) {
        if (right != null) {//left的view不用添加动画效果，所以只给right的view添加
            //offset从0~1（从左向右滑动）
            mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;
            //这个页面一开始要在左边的view的下方
            //所以偏移量一开始是页面宽度，向左偏移所以是负的，接下来有margin的话要把margin减掉才能到正中间
            //再缓缓的增大，相当于向左的偏移逐渐减小，然后第二个页面随着viewpager的特性自然地过渡到屏幕正中间
            mTrans = -getWidth() - getPageMargin() + offsetPixels;
            //用viewhelper给right设置缩放动画
            ViewHelper.setScaleX(right, mScale);
            ViewHelper.setScaleY(right, mScale);
            ViewHelper.setTranslationX(right, mTrans);
            ViewHelper.setAlpha(right,offset);
        }
        if (left != null) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(offset));
            //保证left始终在right的上面，不然left就可能被遮盖住
            //alpha值越小越透明
            ViewHelper.setAlpha(left, MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            left.bringToFront();
        }
    }

    public void getViewForPosition(View view, int position) {
        mChildren.put(position, view);
    }

    public void removeViewFromPosition(int position) {
        mChildren.remove(position);
    }
}
