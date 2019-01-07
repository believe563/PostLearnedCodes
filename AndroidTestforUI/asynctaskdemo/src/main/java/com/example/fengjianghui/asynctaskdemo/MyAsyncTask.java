package com.example.fengjianghui.asynctaskdemo;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by fengjianghui on 2015/10/1.
 */
public class MyAsyncTask extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... params) {
        Log.d("fjh", "doInBackground");
        publishProgress();
        return null;
    }

    @Override
    protected void onPreExecute() {
        Log.d("fjh", "onPreExecute");
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("fjh", "onPostExecute");
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Log.d("fjh", "onProgressUpdate");
        super.onProgressUpdate(values);
    }
}
