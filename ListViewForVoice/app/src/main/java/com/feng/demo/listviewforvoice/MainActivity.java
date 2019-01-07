package com.feng.demo.listviewforvoice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends Activity {
    private ListView lv_home;
    private SoundAndTextAdapter soundAndTextAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置透明状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        lv_home = (ListView) findViewById(R.id.lv_home);
        String[] strs = {"隐约雷鸣，阴霾天空，但盼风雨来，能留你在此",
                "三人行，必有我师焉，择其善者而从之，其不善者而改之",
                "每当吹风，我就随着梦想，放声歌唱，一直一直，朝往天空的彼方",
                "不要错过这街景，马上就到日暮，可以看到夕阳了，就等到太阳落山"};

        soundAndTextAdapter = new SoundAndTextAdapter(this, strs);
        lv_home.setAdapter(soundAndTextAdapter);
    }

}
