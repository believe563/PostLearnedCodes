package com.example.fengjianghui.scrollviewdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    private TextView textView;
    private ScrollView scrollView;
    private Button up;
    private Button down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.content);
        //先获取资源，再获取字符串
        textView.setText(getResources().getString(R.string.content));
        scrollView= (ScrollView) findViewById(R.id.scrollView);
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        /**
                         *(1) getScrollY---滚动条滑动的距离(从0开始)
                         * (2)getMesuredHeight所有内容高度
                         * (3)getHeight所显示内容的高度
                         */
                        //顶部状态
                        if (scrollView.getScrollY() <= 0) {
                            Log.i("Main", "滑动到顶部");
                        }
                        //底部状态
                        //textView的总高度=一屏幕的总高度+滚动条的滚动距离
                        if (scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getHeight() + scrollView.getScrollY()) {
                            Log.i("Main", "滑动到底部");
                            Log.i("Main", "scrollView.getChildAt(0).getMeasuredHeight()=" +
                                    scrollView.getChildAt(0).getMeasuredHeight() +
                                    ",scrollView.getHeight()=" + scrollView.getHeight() + "," +
                                    "scrollView.getScrollY()=" + scrollView.getScrollY());
                            textView.append(getResources().getString(R.string.content));
                        }
                        break;

                }
                return false;
            }
        });
    }


    /**
     * scrollTo以滚动视图起始位置开始计算
     * scrollBy是相对当前位置去滚动
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.up:
                scrollView.scrollBy(0,-30);//横坐标不动，纵坐标向上滚动30的距离
                break;
            case R.id.down:
//                scrollView.scrollTo(0,-30);//横坐标不动，纵坐标向下滚动30的距离
                scrollView.scrollBy(0,30);
                break;
        }
    }
}
