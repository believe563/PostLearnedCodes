package com.multiphotoupload.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.four.voicerecord.pictureupload.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiUploadActivity extends AppCompatActivity {

    private ArrayList<File>files;
    private Map<String, String>params;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case UploadService.UPLOAD_SUCCESS:
                    Toast.makeText(MultiUploadActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiupload);
        files=new ArrayList<File>();
        params=new HashMap<String, String>();
        Log.i("number","lujing"+Environment.getExternalStorageDirectory());
    }

    public void upload(View v) {
        files.clear();
        params.clear();
        File file=new File(Environment.getExternalStorageDirectory(),"kaola.jpg");
        File file2=new File(Environment.getExternalStorageDirectory(),"test.docx");
        File file3=new File(Environment.getExternalStorageDirectory(),"test.jpg");
        files.add(file);
        files.add(file2);
        files.add(file3);
        StringBuffer sbFileTypes=new StringBuffer();
        for (File tempFile:files) {
            String fileName=tempFile.getName();
            sbFileTypes.append(getFileType(fileName));
        }
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
        Toast.makeText(MultiUploadActivity.this, string,Toast.LENGTH_SHORT).show();
    }

//    /**
//     * 用Volley实现的文件上传方法，只需调用该方法即可
//     * @param context
//     * @param url
//     * @param fileName
//     * @param listFile
//     * @param params
//     * @param listener
//     */
//    public static void uploadMultipart(Context context, String url, String fileName,
//                                       List<File> listFile, Map<String, String> params,
//                                       VolleyInterface listener) {
//        MultipartRequestUpload request = new MultipartRequestUpload(url,fileName, listFile, params, listener);
//        MyApplication.getQueue().add(request);
//        MyApplication.getQueue().start();
//    }
}
