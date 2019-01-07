package com.four.voicerecord.jsondemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ListView listView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        String url="http://192.168.3.107:8080/JsonServer/servlet/JsonServerServlet";
        handler = new Handler();
        new HttpJson(url,handler,MainActivity.this,listView).start();
    }

}
