package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/11/5.
 * 第三种方法更新UI
 */
public class SeventhActivity extends Activity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seventh_activity);
        textView = (TextView) findViewById(R.id.textview);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("the third way to update UI");
                    }
                });
                super.run();
            }
        }.start();
    }
}
