package com.example.fengjianghui.webview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private String url="http://2014.qq.com/";
    private WebView webView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        //通过Intent调用系统浏览器
//        Uri uri=Uri.parse(url);//url为要链接的地址
//        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);
        webView= (WebView) findViewById(R.id.webView);
//        //webView加载本地资源
//        webView.loadUrl("file:///android_asset/bendi.html");
        //webView加载web资源
        webView.loadUrl(url);
        webView.requestFocus();

//        if (Build.VERSION.SDK_INT >= 19) {
//            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }//解决net::ERR_CACHE_MISS这个方法木有用

        //覆盖webView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在webView中打开
        //webviewClient帮助WebView去处理一些页面控制和请求通知
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //返回值为true时，控制网页在webView中打开，如果为false调用系统浏览器或第三方浏览器去打开链接
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        //启用支持javaScript
        WebSettings webSettings=webView.getSettings();
        webSettings.getJavaScriptEnabled();

        //有限使用缓存加载页面，明显提升速度
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        //显示加载中的进度条
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {//newProgress是1~100之间的整数，对应百分比
                if(newProgress==100){
                    //页面加载完毕
                    closeDialog();//关闭progressDialog
                }else{
                    //页面正在加载
                    openDialog(newProgress);//打开progressDialog
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    public void openDialog(int newProgress){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("正在加载");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(newProgress);
            progressDialog.show();
        }else{
            progressDialog.setProgress(newProgress);
            progressDialog.show();
        }
    }

    public void closeDialog(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();//取消显示
            progressDialog=null;
        }
    }
    //改写物理按键返回的信息，避免按返回时全部退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
//            Toast.makeText(MainActivity.this,webView.getUrl(),Toast.LENGTH_SHORT).show();
            webView.goBack();
            return true;
        }else{
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

}
