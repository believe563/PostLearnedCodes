package com.four.voicerecord.multithreaddownload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button button;
    private TextView textView;
//    private String url;
    private int count=0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int result=msg.what;
            count +=result;
            if (count == 3) {
                textView.setText("download succeed");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这些操作不允许在主线程中进行，所以需要添加一个线程
                Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DownLoad1 d1 = new DownLoad1(handler);
                        d1.downLoadFile("http://192.168.3.101:8080/multiThreadDownload/a3.jpg");
                    }
                }).start();
            }
        });
    }

}
