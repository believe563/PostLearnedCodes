package com.fengjianghui.com.multiuploaddemo;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<File> files;
    private Map<String, String> params;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case UploadService.UPLOAD_SUCCESS:
                    Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        files=new ArrayList<File>();
        params=new HashMap<String, String>();
        Log.i("number","lujing"+ Environment.getExternalStorageDirectory());
    }

    public void upload(View v) {
        files.clear();
        params.clear();
        File file=new File(Environment.getExternalStorageDirectory(),"a.jpg");
        File file2=new File(Environment.getExternalStorageDirectory(),"b.jpg");
        File file3=new File(Environment.getExternalStorageDirectory(),"c.jpg");
        files.add(file);
        files.add(file2);
        files.add(file3);
        Log.i("number","三个文件分别是："+files);
        StringBuffer sbFileTypes=new StringBuffer();
        for (File tempFile:files) {
            String fileName=tempFile.getName();
            sbFileTypes.append(getFileType(fileName));
        }
        Log.i("number", "fileTypes:" + sbFileTypes.toString());
        params.put("fileTypes",sbFileTypes.toString());
        params.put("method", "upload");
        UploadService uploadService=new UploadService(mHandler);
        uploadService.uploadFileToServer(params, files);
    }
    /**
     * 获取文件的类型
     * @param fileName ：文件名
     * @return 文件类型
     */
    private String getFileType(String fileName) {
        // TODO Auto-generated method stub
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }

    public void outputtoast(String string) {
        Toast.makeText(MainActivity.this, string,Toast.LENGTH_SHORT).show();
    }
}
