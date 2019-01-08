package com.example.fengjianghui.gesturedetector;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity{

    private ImageView imageView;
    GestureDetector myGestureDetector;
    class myOnGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override//识别滑动，event事件是由gesturedetector转发过来的，event1是开始事件，event2是结束事件
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX()-e2.getX()>50) {
                Toast.makeText(MainActivity.this, "从右往左滑", Toast.LENGTH_SHORT).show();
            }else if (e2.getX()-e1.getX()>50) {
                Toast.makeText(MainActivity.this, "从左往右滑", Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        myGestureDetector=new GestureDetector(new myOnGestureListener());
        //MotionEvent event是手指触摸屏幕的事件
        //让GestureDetector去转发event事件给onGestureListener接口
        //onGestureListener根据得到的event对象封装的信息做出合适的反馈
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //用GestureDetector的onTouchEvent()方法转发给onGestureListener的对象实例
                myGestureDetector.onTouchEvent(event);
                return true;//返回要改为true才可以
            }
        });
    }

}
