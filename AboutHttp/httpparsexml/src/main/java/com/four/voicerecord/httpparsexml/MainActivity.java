package com.four.voicerecord.httpparsexml;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * 解析从客户端获得的xml文件
 */
public class MainActivity extends Activity {

    private TextView textView;
    private String url;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        url="http://192.168.3.107:8080/HttpParseXml/test.xml";
        XmlThread thread = new XmlThread(url,handler,textView);
        thread.start();
    }

}
