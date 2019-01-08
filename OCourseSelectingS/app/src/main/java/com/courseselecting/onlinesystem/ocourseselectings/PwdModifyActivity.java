package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Students;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PwdModifyActivity extends Activity implements View.OnClickListener{

    private AutoCompleteTextView actv_oldPwd;
    private AutoCompleteTextView actv_newPwd;
    private AutoCompleteTextView actv_reNewPwd;
    private Button bt_ok;

    private String mAddress;
    private int mPort;
    private Students student;

    //输入的密码值
    private String oldPwd;
    private String newPwd;
    private String reNewPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwdmodify_layout);
        actv_oldPwd = (AutoCompleteTextView) findViewById(R.id.actv_oldPwd);
        actv_newPwd = (AutoCompleteTextView) findViewById(R.id.actv_newPwd);
        actv_reNewPwd = (AutoCompleteTextView) findViewById(R.id.actv_reNewPwd);
        bt_ok = (Button) findViewById(R.id.bt_ok);

        getInfo();
        bt_ok.setOnClickListener(this);
    }

    private void getInfo() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mAddress=intent.getStringExtra("address");
        mPort = intent.getIntExtra("port",12344);
        List<Students> stuList= (List<Students>) bundle.getSerializable("student");
        student = stuList.get(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                oldPwd = String.valueOf(actv_oldPwd.getText());
                newPwd = String.valueOf(actv_newPwd.getText());
                reNewPwd = String.valueOf(actv_reNewPwd.getText());
                System.out.println("student.getPwd()"+student.getPwd());
                if (!(oldPwd.equals(student.getPwd()))) {
                    Toast.makeText(PwdModifyActivity.this, "原始密码不正确", Toast.LENGTH_SHORT).show();
                }else if (newPwd.length()<6) {
                    Toast.makeText(PwdModifyActivity.this, "新密码不能少于6位", Toast.LENGTH_SHORT).show();
                }else if (!(newPwd.equals(reNewPwd))) {
                    Toast.makeText(PwdModifyActivity.this, "两次新密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    MyModifyAsyncTask mmat=new MyModifyAsyncTask();
                    mmat.execute();
                }
                break;
        }
    }

    class MyModifyAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            Socket socket =null;
            ObjectOutputStream oos=null;
            BufferedReader br=null;
            try {
                socket=new Socket(mAddress, mPort);
                oos = new ObjectOutputStream(socket.getOutputStream());
                //map中有3个string
                Map<String, String> map = new HashMap<>();
                map.put("pwd", newPwd);
                map.put("stuId", student.getId());
                map.put("pwdnote", "pwdnote");
                List<Map<String, String>> list = new ArrayList<>();
                list.add(map);
                String jsonString = JSON.toJSONString(list);
                oos.writeObject(jsonString);
                oos.flush();
                socket.shutdownOutput();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String stringJson=null;
                while ((stringJson=br.readLine())!=null) {
                    return stringJson.equals("Y");
                }
                socket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
                
            }finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            boolean b=(boolean)o;
            if (b) {
                Toast.makeText(PwdModifyActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PwdModifyActivity.this, MainActivity.class);
                startActivity(intent);
                PwdModifyActivity.this.finish();
            }else {
                Toast.makeText(PwdModifyActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(o);
        }
    }
}
