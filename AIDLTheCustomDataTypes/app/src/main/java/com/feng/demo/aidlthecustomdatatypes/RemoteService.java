package com.feng.demo.aidlthecustomdatatypes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/24.
 */
public class RemoteService extends Service {
    List<Person> list;


    @Override
    public IBinder onBind(Intent intent) {
        list = new ArrayList<>();
        return iBinder;
    }

    IBinder iBinder = new IMyAidlInterface.Stub() {

        @Override
        public List<Person> add(Person person) throws RemoteException {
            list.add(person);
            return list;
        }
    };
}
