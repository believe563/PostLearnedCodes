package com.example.fengjianghui.asynctaskdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * Created by fengjianghui on 2015/10/2.
 * 进度条的异步加载
 */
public class ProgressBarTest extends Activity {
    private ProgressBar progressBar2;
    private MyAsyncTask1 mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mTask = new MyAsyncTask1();
        mTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //异步任务不为空且状态为加载状态时，要将mTask cancel掉
        if (mTask!=null&&mTask.getStatus()==AsyncTask.Status.RUNNING) {
            //cancel方法只是将对应的AsyncTask方法标记为cancel状态，并不是真正的取消
            //需要在doInBackground方法中判断一下isCanceled（），如果true则break
            //还要在onProgressUpdate中判断一下iscancelled（）如果true，则return
            mTask.cancel(true);
        }
    }

    //第二个返回加载的进度，因为这里需要实时返回进度，所以用Integer参数
    class MyAsyncTask1 extends AsyncTask<Void,Integer,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //for循环是用来模拟进度的更新
            for (int i = 0; i < 100; i++) {
                if (isCancelled()){
                    break;
                }
                //更新进度，将更新进度的参数传进去
                publishProgress(i);
                try {
                    //增加睡眠时间来延缓更新的时间
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         *
         * @param values 承接publishProgress里的参数
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (isCancelled()){
                return;
            }
            //获取进度更新值，这样就可以实时获取到异步线程中所传递进来的进度更新值
            progressBar2.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }
}
