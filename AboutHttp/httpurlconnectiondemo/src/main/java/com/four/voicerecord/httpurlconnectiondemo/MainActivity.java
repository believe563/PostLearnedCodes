package com.four.voicerecord.httpurlconnectiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private WebView webView;
    private ImageView imageView;
    String url="http://www.baidu.com";
    String str="http://mg.soupingguo.com/bizhi/big/10/055/499/10055499.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imageView);
//        new HttpThread(url, webView, new Handler()).start();
        new HttpThread(str, imageView, new Handler()).start();
    }
}
