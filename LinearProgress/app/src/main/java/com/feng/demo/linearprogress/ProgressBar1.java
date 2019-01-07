package com.feng.demo.linearprogress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;


public class ProgressBar1 extends Activity implements Callback {

    private ProgressView pv_linear_colors;

    private Handler mHandler;

    private static final int MSG_START_PROGRESS = 1000;
    private static final int MSG_STOP_PROGRESS = 1001;
    private static final int MSG_UPDATE_PROGRESS = 1002;
    private static final int MSG_UPDATE_QUERY_PROGRESS = 1003;
    private static final int MSG_UPDATE_BUFFER_PROGRESS = 1004;

    private static final long PROGRESS_INTERVAL = 7000;
    private static final long START_DELAY = 2000;
    private static final long PROGRESS_UPDATE_INTERVAL = PROGRESS_INTERVAL / 100;
    private static final long START_QUERY_DELAY = PROGRESS_INTERVAL / 2;
    //    private static final long QUERY_PROGRESS_UPDATE_INTERVAL = (PROGRESS_INTERVAL - START_QUERY_DELAY) / 100;
    private static final long BUFFER_PROGRESS_UPDATE_INTERVAL = (PROGRESS_INTERVAL - START_QUERY_DELAY) / 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_progress);
        pv_linear_colors = (ProgressView) findViewById(R.id.progress_pv_linear_colors);
        mHandler = new Handler(this);
    }


    @Override
    public void onPause() {
        super.onPause();

        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(MSG_START_PROGRESS, START_DELAY);
    }

    @Override
    public boolean handleMessage(Message msg) {

        pv_linear_colors.start();

        mHandler.sendEmptyMessageDelayed(MSG_STOP_PROGRESS, PROGRESS_INTERVAL);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, PROGRESS_UPDATE_INTERVAL);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_QUERY_PROGRESS, START_QUERY_DELAY);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_BUFFER_PROGRESS, BUFFER_PROGRESS_UPDATE_INTERVAL);

        return false;
    }

}
