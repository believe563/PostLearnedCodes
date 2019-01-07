package com.example.fengjianghui.viewflipperdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

    private ViewFlipper viewFlipper;
    //手指落下时的坐标
    private float startX;
    //写好资源
    private int[] resId={R.mipmap.a,R.mipmap.a1,R.mipmap.a4,R.mipmap.a6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFlipper= (ViewFlipper) findViewById(R.id.flipper);
        //动态倒入的方式为ViewFlipper加入一些子view
        for (int i=0;i<resId.length;i++){
            viewFlipper.addView(getImageView(resId[i]));
        }
        viewFlipper.setBackgroundColor(Color.BLACK);

//        //为viewFlipper添加动画效果(自动播放效果)
//        viewFlipper.setInAnimation(this, R.anim.left_in);
//        viewFlipper.setOutAnimation(this,R.anim.left_out);
//        //设定视图切换的时间间隔
//        viewFlipper.setFlipInterval(3000);
//        //开始播放
//        viewFlipper.startFlipping();

    }
    private ImageView getImageView(int resId){
        ImageView imageView=new ImageView(this);

//        //ImageView的宽高完全取决于图片的宽高
//        imageView.setImageResource(resId);

        //铺满整个屏幕
        imageView.setBackgroundResource(resId);
        return imageView;
    }

    /**
     * 监控手势滑动事件的处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            //手指落下
            case MotionEvent.ACTION_DOWN:
                startX=event.getX();
                break;
            //手指滑动
            case MotionEvent.ACTION_MOVE:

                break;
            //手指离开
            case MotionEvent.ACTION_UP:
                //向右滑的时候,数值小于100时为微小滑动，视为点击
                if (event.getX()-startX>100){
                    viewFlipper.setInAnimation(this, R.anim.left_in);
                    viewFlipper.setOutAnimation(this,R.anim.left_out);
                    //向右滑看前一页
                    viewFlipper.showPrevious();
                }
                //向左划
                if(startX-event.getX()>100){
                    viewFlipper.setInAnimation(this,R.anim.right_in);
                    viewFlipper.setOutAnimation(this,R.anim.right_out);
                    //显示后一页
                    viewFlipper.showNext();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
