package com.feng.demo.valueanimatordemo1;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TypeEvaluator是一个数值计算器
        //fraction：分数、小部分。这里指时间因子
        //这里可以自定义许多动画的移动和轨迹变换
        ValueAnimator animator=ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            @Override  //evaluate估价、求值  PointF()点坐标
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                return null;
            }
        });
    }
}
