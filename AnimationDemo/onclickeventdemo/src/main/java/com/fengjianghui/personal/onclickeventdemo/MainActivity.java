package com.fengjianghui.personal.onclickeventdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view) {
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",0F,1F);

        animator.setDuration(1000);
        //AnimatorListenerAdapter是一个抽象类，实现了AnimatorListener这个接口
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(MainActivity.this, "animator end", Toast.LENGTH_SHORT).show();
            }
        });
        //如果是new Animator.AnimatorListener()的话需要写出所有的方法，而大多数情况下不是所有方法都需要有监听事件，
        //所以用new Animator.AnimatorListenerAdapter()
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Toast.makeText(MainActivity.this, "animator end", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        animator.start();

    }
}
