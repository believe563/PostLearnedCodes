package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/11/5.
 * 第四种更新UI的方式
 */
public class EighthActivity extends Activity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eighth_layout);
        textView = (TextView) findViewById(R.id.textview);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("the fourth way to update UI");
                    }
                });
            }
        }.start();
    }
}
