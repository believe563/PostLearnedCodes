package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Courses;
import entity.Students;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MenuActivity  extends Activity{
    private ListView listView;
    private ArrayList<String> mList_menu;
    private List<Students> listStu = new ArrayList<>();
    private List<Courses> listCourses=new ArrayList<>();


    //从主菜单传过来的信息
    private Students student;//当前学生信息
    private String mAddress="192.168.1.102";
    private int mPort=12344;
    private Intent intent ;
    private Bundle bundle;

    private ProgressBar progressBar;

    public <T> List<T> getObjList(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString, cls);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                listCourses = (List<Courses>) msg.obj;
                System.out.println("selectingcourse--handler--listCourse:" + listCourses);
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        listView= (ListView) findViewById(R.id.lv_menu);
        progressBar = (ProgressBar) findViewById(R.id.pb_wait);
        progressBar.setVisibility(View.GONE);
        mList_menu=new ArrayList<>();
        mList_menu.add("个人信息");
        mList_menu.add("密码修改");
        mList_menu.add("学生选课");
        mList_menu.add("学生个人课表");
        mList_menu.add("选课情况查询");

        intent=getIntent();
        bundle =intent.getExtras();//bundle里放的是学生信息
        //bundle.putSerializable("student",(Serializable)listStu);

        getStudentInfo();//获取登录学生的信息
        getAddressAndPort();
        progressBar.setVisibility(View.VISIBLE);

        getCourses();
        progressBar.setVisibility(View.GONE);

//        Toast.makeText(MenuActivity.this, "成功", Toast.LENGTH_SHORT).show();
        listView.setAdapter(new ListMenuAdapter(mList_menu, this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent  intent=new Intent(MenuActivity.this,PersonalInfoActivity.class);
                        intent.putExtras(bundle);
                        intent.putExtra("address", mAddress);
                        intent.putExtra("port", mPort);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("student",(Serializable)listStu);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1://密码修改界面
                        Intent  intent1=new Intent(MenuActivity.this,PwdModifyActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("student",(Serializable)listStu);
                        intent1.putExtras(bundle1);
                        intent1.putExtra("address", mAddress);
                        intent1.putExtra("port", mPort);
                        startActivity(intent1);
                        break;
                    case 2://进入选课界面
                        Toast.makeText(MenuActivity.this, "进入选课界面", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent();//将当前学生信息，可选课程、地址和端口号传过去
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("courses",(Serializable)listCourses);
//                        Toast.makeText(MenuActivity.this,"id="+listCourses.get(0).getId(), Toast.LENGTH_SHORT).show();
                        bundle2.putSerializable("student", (Serializable) listStu);
                        intent2.putExtras(bundle2);
//                        Toast.makeText(MenuActivity.this, "正确将学生信息存入bundle中"+((List<Students>)bundle.getSerializable("student")).get(0).getId(), Toast.LENGTH_SHORT).show();
                        intent2.putExtra("address", mAddress);
                        intent2.putExtra("port", mPort);
//                        Toast.makeText(MenuActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        intent2.setClass(MenuActivity.this, SelectingCourseActivity.class);

                        startActivity(intent2);
                        break;
                    case 3:
                        Toast.makeText(MenuActivity.this, "case 3", Toast.LENGTH_SHORT).show();
                        Intent  intent3=new Intent(MenuActivity.this,PersonalScheduleActivity.class);
                        //intent3.putExtras(bundle);
                        intent3.putExtra("address", mAddress);
                        intent3.putExtra("port", mPort);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MenuActivity.this, SelectingConditionActivity.class);
                        //intent4.putExtras(bundle);
                        intent4.putExtra("address", mAddress);
                        intent4.putExtra("port", mPort);
                        startActivity(intent4);
                        break;
                }
            }
        });
    }

    private void getAddressAndPort() {
        mAddress = intent.getStringExtra("address");
        mPort = intent.getIntExtra("port", 12344);
    }

    private void getStudentInfo() {
        Bundle bundle =intent.getExtras();
        listStu = (List<Students>) bundle.getSerializable("student");
        student = listStu.get(0);
//        Students stu = (Students) intent.getSerializableExtra("student");
        System.out.println("学生list信息：" + listStu);

    }

    //获取年级对应的课程
    private void getCourses() {
//        MyAsyncTask ma=new MyAsyncTask(SelectingCourseActivity.this,mAddress,mPort,student,listView);
//        ma.execute();

        new Thread(new Runnable() {
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            Socket socket = null;
            String jsonString;

            @Override
            public void run() {
                try {
                    socket = new Socket(mAddress, mPort);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ArrayList<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> map = new HashMap();
                    map.put("grade", student.getGrade());
                    map.put("name", student.getName());
                    map.put("id", student.getId());
                    map.put("note","getcourseforgrade");
                    list.add(map);
                    jsonString = JSON.toJSONString(list);
                    System.out.println("获取课程的maplist" + list);
                    oos.writeObject(jsonString);
                    oos.flush();
                    socket.shutdownOutput();

                    ois = new ObjectInputStream(socket.getInputStream());
                    List<Courses> listC = null;
                    while ((jsonString = (String) ois.readObject()) != null) {
                        listC = getObjList(jsonString, Courses.class);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = listC;
                        handler.sendMessage(msg);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
