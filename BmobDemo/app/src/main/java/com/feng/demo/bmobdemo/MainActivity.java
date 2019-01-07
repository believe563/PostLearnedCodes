package com.feng.demo.bmobdemo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/*数据的增删改查使用方法*/

public class MainActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mFeedback;

    private EditText mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化sdk,第二个参数是applicationid
        Bmob.initialize(this,"19e982bdbfc26c4f8e1b122671f9c1f1");

        mName = (EditText) findViewById(R.id.name);
        mFeedback = (EditText) findViewById(R.id.feedback);

        mQuery = (EditText) findViewById(R.id.et_query);
        //接下来做一个反馈问卷的提交例子
        //将数据提交到服务器上

    }

    /*提交按钮的点击事件
    * 向表中插入Feedback表和数据
    * */
    public void submit(View view) {
        String name=mName.getText().toString();
        String feedback=mFeedback.getText().toString();
        if (("").equals(name) || ("").equals(feedback)) {
            return;
        }
        Feedback fbObj=new Feedback();
        fbObj.setName(name);
        fbObj.setFeedback(feedback);
        //将这行增加了的数据写到服务器上
        fbObj.save(MainActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "submit success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "submit fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*查询多条数据*/
    public void queryAll(View view) {
        BmobQuery<Feedback> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(MainActivity.this, new FindListener<Feedback>() {
            @Override
            public void onSuccess(List<Feedback> list) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Query");
                String str = "";
                for (Feedback feedback : list) {
                    str += feedback.getName() + ":" + feedback.getFeedback()+"\n";

                }
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /*查询单条数据*/
    public void queryFeedback(View view) {
        String str=mQuery.getText().toString();
        if ("".equals(str)) {
            return;
        }
        BmobQuery<Feedback> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("name", str);//和查询多条数据相比只增加了这句话
        bmobQuery.findObjects(MainActivity.this, new FindListener<Feedback>() {
            @Override
            public void onSuccess(List<Feedback> list) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Query");
                String str = "";
                for (Feedback feedback : list) {
                    str += feedback.getName() + ":" + feedback.getFeedback()+"\n";

                }
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
