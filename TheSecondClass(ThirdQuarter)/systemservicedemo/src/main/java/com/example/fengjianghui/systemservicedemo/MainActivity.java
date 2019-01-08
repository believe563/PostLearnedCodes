package com.example.fengjianghui.systemservicedemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_main, null);
        setContentView(view);
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.network:
                if (getNetwork(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "网络已经打开", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"网络未连接",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.enableOrDisableWIFI://加获取和改变wifi两个状态
                WifiManager wifiManager = (WifiManager) MainActivity.this.getSystemService(WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }else {
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(MainActivity.this, "wifi已经打开", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.getvoice:
                AudioManager audioManager = (AudioManager) MainActivity.this.getSystemService(AUDIO_SERVICE);
                //得到系统音量值
                int maxAudio = audioManager.getStreamMaxVolume(audioManager.STREAM_SYSTEM);
                //得到当前音量值
                //系统音量指的是铃声音量
                int current = audioManager.getStreamVolume(audioManager.STREAM_SYSTEM);
                Toast.makeText(MainActivity.this, "系统的最大音量为：" + maxAudio + ",当前音量是：" + current,Toast.LENGTH_SHORT).show();
//                if (audioManager.)
                break;
            case R.id.getPackageName:
                ActivityManager activityManager = (ActivityManager) MainActivity.this.getSystemService(ACTIVITY_SERVICE);
                //获得当前进程的名字
                String packageName =activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
                Toast.makeText(MainActivity.this, "当前运行的activity的包名：" + packageName,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public boolean getNetwork(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) MainActivity.this.getSystemService(CONNECTIVITY_SERVICE);
            //获取当前网络的活动状态
            NetworkInfo info=manager.getActiveNetworkInfo();
            if (info!=null){
                return info.isAvailable();
            }
        }
        return false;
    }
}