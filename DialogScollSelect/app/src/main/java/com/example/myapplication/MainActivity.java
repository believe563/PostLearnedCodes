package com.example.myapplication;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RoomTypeHeightWheelDialog mHeight_Dialog;
    private RoomTypeHeightWheelDialog.HeightWheelDialogcallback mCallback_height;
    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);
        mTv = (TextView) findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height_Dialog_Display();
            }
        });

        mCallback_height = new RoomTypeHeightWheelDialog.HeightWheelDialogcallback() {
            @Override
            public void dialogdo(String string, Dialog dialog) {
                mTv.setText(string);
            }
        };
    }

    public void height_Dialog_Display(){
        if(mHeight_Dialog ==null){
            mHeight_Dialog = new RoomTypeHeightWheelDialog(this,getWindowManager());
            mHeight_Dialog.setCanceledOnTouchOutside();
        }
        mHeight_Dialog.setDialogCallback(mCallback_height);
        mHeight_Dialog.show();
    }
}
