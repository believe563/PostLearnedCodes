package com.fengjianghui.personal.the1stdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 扇形菜单的例子
 * 用到差值器，可以为弹出的按钮设置速度变换，比如加速度，先加速后减速等
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private int[] idRes = {R.id.iv_a, R.id.iv_b, R.id.iv_c, R.id.iv_d, R.id.iv_e, R.id.iv_f, R.id.iv_g, R.id.iv_h, R.id.iv_i};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private boolean openFlag=true;
    private double angle=Math.PI/(2*(idRes.length-1));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //先实例化每个按钮并将其添加到list中，并设置监听事件
        for (int i = 0; i < idRes.length; i++) {
            ImageView imageView = (ImageView) findViewById(idRes[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //如果是最上面的按钮则点击之后判断是否弹出，若未弹出，点击弹出，
            //若已弹出，则点击收回
            case R.id.iv_a:
                if (openFlag) {
                    //点击弹出
                    startAnimation();
                    openFlag = false;
                } else {
                    //点击收回
                    closeAnimation();
                    openFlag=true;
                }

                break;
            default:
                Toast.makeText(MainActivity.this, "click" + v.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void closeAnimation() {
        float translationX=0;
        float translationY=0;
        for (int i =1; i<idRes.length; i++) {
            translationX= (float) (600*Math.sin(angle*i));
            translationY = (float) (600 * Math.cos(angle*i));
            ObjectAnimator animator= ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",translationY,0F);
            ObjectAnimator animator2= ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationX",translationX,0F);
            AnimatorSet set=new AnimatorSet();
            set.playTogether(animator,animator2);
            set.setDuration((idRes.length-i)*200);
            set.setInterpolator(new AccelerateInterpolator());
            set.setStartDelay(i*300);
            set.start();
        }
    }

    private void startAnimation() {
        float translationX=0;
        float translationY=0;
        for (int i = 1; i < idRes.length; i++) {
            translationX= (float) (600*Math.sin(angle*i));
            translationY = (float) (600 * Math.cos(angle*i));
            ObjectAnimator animator= ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",0F,translationY);
            ObjectAnimator animator2= ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationX",0F,translationX);
            AnimatorSet set=new AnimatorSet();
            set.playTogether(animator,animator2);
            set.setDuration((idRes.length-i)*200);
            set.setInterpolator(new BounceInterpolator());//差值器，反弹
            set.setStartDelay(i*300);
            set.start();
        }
    }
}