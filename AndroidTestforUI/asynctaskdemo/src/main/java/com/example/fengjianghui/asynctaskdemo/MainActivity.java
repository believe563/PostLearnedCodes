package com.example.fengjianghui.asynctaskdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyAsyncTask myAsyncTask = new MyAsyncTask();
//        myAsyncTask.execute();
        button = (Button) findViewById(R.id.imgbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ImageTest.class);
                startActivity(intent);
            }
        });
    }
    public void onLoadProgressBar(View view){
        startActivity(new Intent(MainActivity.this,ProgressBarTest.class));
    }
}