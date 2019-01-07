package com.fengjianghui.personal.gaodeditudemo;

import android.app.Activity;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;

/**
 * Created by fengjianghui on 2016-01-28.
 */
public class AmapNaviViewActivity extends Activity {
    private AMapNaviView aMapNaviView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aMapNaviView = (AMapNaviView) findViewById(R.id.map);
        aMapNaviView.onCreate(savedInstanceState);

    }
}
