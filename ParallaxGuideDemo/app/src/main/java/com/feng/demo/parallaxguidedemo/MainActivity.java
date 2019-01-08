package com.feng.demo.parallaxguidedemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private Button mbtGo;
    private ViewPagerAdapter vpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        mbtGo = (Button) findViewById(R.id.bt_go);

        //实例化各个界面的布局对象
        View view1 = View.inflate(this, R.layout.guide_view, null);
        View view2 = View.inflate(this, R.layout.guide_view, null);
        View view3 = View.inflate(this, R.layout.guide_view, null);
        ((ImageView) view1.findViewById(R.id.iv_pic)).setImageResource(R.drawable.page1);
        ((ImageView) view2.findViewById(R.id.iv_pic)).setImageResource(R.drawable.page2);
        ((ImageView) view3.findViewById(R.id.iv_pic)).setImageResource(R.drawable.page3);

        mbtGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "go home", Toast.LENGTH_SHORT).show();
            }
        });
        view1.findViewById(R.id.iv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        view2.findViewById(R.id.iv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        view3.findViewById(R.id.iv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
        ((TextView)view1.findViewById(R.id.tv_title)).setText("精彩首页");
        ((TextView)view1.findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#ff5000"));
        ((TextView)view2.findViewById(R.id.tv_title)).setText("发现定位");
        ((TextView)view2.findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#49ca65"));
        ((TextView)view3.findViewById(R.id.tv_title)).setText("欢乐互动");
        ((TextView)view3.findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#16c5c6"));

        ((TextView)view1.findViewById(R.id.tv_desc)).setText("第一时间为你奉上\n");
        ((TextView)view2.findViewById(R.id.tv_desc)).setText("给你最好的\n");
        ((TextView)view3.findViewById(R.id.tv_desc)).setText("精彩由你\n");

        ArrayList<View> views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        mViewPager.setOffscreenPageLimit(views.size());//设置viewpager保留多少个显示界面
        mViewPager.setPageMargin(-dip2px(135));//        mViewPager.setPageMargin();
        vpAdapter = new ViewPagerAdapter(views,this);
        mViewPager.setAdapter(vpAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position+1 != vpAdapter.getCount()) {
                    if (mbtGo.getVisibility() != View.GONE) {
                        mbtGo.setVisibility(View.GONE);
                        mbtGo.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
                    }
                } else {
                    if (mbtGo.getVisibility() != View.VISIBLE) {
                        mbtGo.setVisibility(View.VISIBLE);
                        mbtGo.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
                    }
                }
                mbtGo.invalidate();
            }
        });

        //设置换页时的不同表现形式
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE=0.85f;
            private static final float MIN_TXT_SCALE=0.0f;
            private static final float MIN_TXT_ALPHA=0.0f;

            @Override
            public void transformPage(View page, float position) {
                View mGuideImage = page.findViewById(R.id.iv_pic);
                View mDesc = page.findViewById(R.id.tv_title);
                View mTitle = page.findViewById(R.id.tv_desc);

                //int mViewWidth=mDesc.getWidth();
                if (position < -1) {
                    mTitle.setAlpha(0);
                    mDesc.setAlpha(0);
                }else if (position<=1) {
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float scaleTxtFactor = Math.max(MIN_TXT_SCALE, 1 - Math.abs(position));
                    mGuideImage.setScaleX(scaleFactor);
                    mGuideImage.setScaleY(scaleFactor);

                    mTitle.setScaleX(scaleTxtFactor);
                    mTitle.setScaleY(scaleTxtFactor);
                    mTitle.setAlpha(MIN_TXT_ALPHA + (scaleTxtFactor - MIN_TXT_SCALE) / (1 - MIN_TXT_SCALE) * (1 - MIN_TXT_ALPHA));
                    mDesc.setScaleX(scaleTxtFactor);
                    mDesc.setScaleY(scaleTxtFactor);
                    mDesc.setAlpha(mTitle.getAlpha());
                }else {
                    mTitle.setAlpha(0);
                    mDesc.setAlpha(0);

                }
            }
        });
    }

    /*dp转换为pixel的公式为：px=（int）（dp*density+0.5f）
    * px转换为dp的公式为：dp=(int)(px/density+0.5f)*/
    private int dip2px(float dpvalue) {//将dp单位转换为以pixel为单位的值
        float scale=getResources().getDisplayMetrics().density;
        System.out.println("密度为："+scale);
        return (int)(dpvalue*scale+0.5);
    }

}
