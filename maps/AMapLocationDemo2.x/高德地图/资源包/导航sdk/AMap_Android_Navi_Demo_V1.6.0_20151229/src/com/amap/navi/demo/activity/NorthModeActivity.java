package com.amap.navi.demo.activity;

import android.os.Bundle;
import android.view.View;

import com.amap.api.navi.AMapNaviView;
import com.amap.navi.demo.R;

/**
 * 创建时间：15/12/22 15:29
 * 项目名称：newNaviDemo
 *
 * @author lingxiang.wang
 * @email lingxiang.wang@alibaba-inc.com
 * 类说明：
 */

public class NorthModeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_north_mode);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);


        mAMapNaviView.getViewOptions().setLayoutVisible(false);
    }

    public void north(View view) {
        mAMapNaviView.openNorthMode();
    }

    public void lock(View view) {
        mAMapNaviView.recoverLockMode();
    }
}
