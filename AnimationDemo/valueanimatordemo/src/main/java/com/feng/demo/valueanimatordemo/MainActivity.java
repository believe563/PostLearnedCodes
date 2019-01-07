package com.feng.demo.valueanimatordemo;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.bt_num);
        bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_num:
                final Button button = (Button) v;
                ValueAnimator animator = ValueAnimator.ofInt(0, 100);
                animator.setDuration(5000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();//因为这个方法返回的是object值，所以要用integer包装类接收
                        button.setText("" + value);
                        System.out.println("" + value);
                    }
                });
                animator.start();
                break;
        }

    }
}
