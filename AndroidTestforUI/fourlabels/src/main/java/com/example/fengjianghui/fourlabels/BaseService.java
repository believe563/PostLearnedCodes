package com.example.fengjianghui.fourlabels;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by fengjianghui on 2015/9/9.
 */
public class BaseService extends Service {
    @Override
    public IBinder onBind(Intent intent) {//bind����
        return null;
    }
}
