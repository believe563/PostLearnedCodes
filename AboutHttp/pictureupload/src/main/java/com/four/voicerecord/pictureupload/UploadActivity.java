package com.four.voicerecord.pictureupload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * 上传图片到服务器端
 */
public class UploadActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                File file=Environment.getExternalStorageDirectory();
                File fileAbs = new File(file, "a3.jpg");
                //转换成字符串的路径
                String fileName = fileAbs.getAbsolutePath();
                //311
                String url = "http://192.168.3.101:8080/test/Upload";
                //寝室
//                String url = "http://192.168.1.101:8080/PictureEstablishWithProtocolDescribe/servlet/Upload";
                //i-liaoning
//                String url="http://172.42.205.182:8080/PictureEstablishWithProtocolDescribe/servlet/Upload";
                UploadThread ut = new UploadThread(fileName,url);
                ut.start();
            }
        });
    }
}