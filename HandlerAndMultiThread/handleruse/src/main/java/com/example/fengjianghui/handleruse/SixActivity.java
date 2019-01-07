package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/11/5.
 * 更新UI的方式----第二种
 */
public class SixActivity extends Activity {
    private TextView textView;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText("the second methods to update UI");
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.six_layout);
        textView= (TextView) findViewById(R.id.textview);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
                super.run();
            }
        }.start();
    }
}
