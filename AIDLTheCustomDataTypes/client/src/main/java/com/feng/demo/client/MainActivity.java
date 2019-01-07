package com.feng.demo.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.feng.demo.aidlthecustomdatatypes.Person;
import com.feng.demo.aidlthecustomdatatypes.IMyAidlInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_getperson;

    private IMyAidlInterface myAidlInterface;
    ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlInterface=null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_getperson = (Button) findViewById(R.id.bt_getperson);
        bt_getperson.setOnClickListener(this);
        bindingService();
    }

    private void bindingService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.feng.demo.aidlthecustomdatatypes", "com.feng.demo.aidlthecustomdatatypes.RemoteService"));
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getperson:
                try {
                    List<Person> list=myAidlInterface.add(new Person("feng", 21));
                    Log.e("Persons ", list.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
