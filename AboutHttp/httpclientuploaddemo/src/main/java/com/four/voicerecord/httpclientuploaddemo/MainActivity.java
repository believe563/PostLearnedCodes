package com.four.voicerecord.httpclientuploaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends Activity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.145:8080/httpUpload/Upload";
                File parent= Environment.getExternalStorageDirectory();
                File fileAbs = new File(parent, "a.jpg");
                String fileName=fileAbs.getAbsolutePath();
                UploadThread thread = new UploadThread(url,fileName);
                thread.start();
            }
        });
    }
}
