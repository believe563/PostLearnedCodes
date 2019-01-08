package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import entity.Courses;
import entity.Students;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SelectingCourseActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private Button btSave;
    private Button btCancel;

    private ArrayList<String> listCoursesName;
    private ArrayList<String> listCoursesDetail;

    private Intent intent;
    private Bundle bundle;
    private List<Students> listStu;
    private List<Courses> listCourses;
    private Students student;
    private String mAddress;
    private int mPort;

    private ListSelectingAdapter listSelectingAdapter;
    private ProgressBar progressBar;
    private List<String> selectedCourseIds =new ArrayList<>();

    public <T> List<T> getObjList(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString, cls);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectingcourse_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置状态栏半透明
        listView = (ListView) findViewById(R.id.lv_selectingCourse);
        btSave = (Button) findViewById(R.id.bt_save);
        btCancel = (Button) findViewById(R.id.bt_cancel);//返回按钮，会返回到菜单页面
        progressBar = (ProgressBar) findViewById(R.id.pb_wait);
        progressBar.setVisibility(View.GONE);

        btSave.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        intent = getIntent();
        listCoursesName = new ArrayList<>();
        listCoursesDetail = new ArrayList<>();
        //获得学生信息，地址信息和端口信息
        getStuInfo();
        //获得该学生已经选的课程的id
        GetSelectedCourseAsyncTask gscat=new GetSelectedCourseAsyncTask();
        gscat.execute(student);
        System.out.println("stulist" + listStu);
        System.out.println("courselist" + listCourses);
        System.out.println("courseNamelist" + listCoursesName);
        System.out.println("courseDetaillist" + listCoursesDetail);

    }

    public void getStuInfo() {
        bundle = intent.getExtras();
        listStu = (List<Students>) bundle.getSerializable("student");
        listCourses = (List<Courses>) bundle.getSerializable("courses");
        mAddress = intent.getStringExtra("address");
        mPort = intent.getIntExtra("port", 12344);
        student = listStu.get(0);
        Courses courses = null;
        for (int i = 0; i < listCourses.size(); i++) {
            courses = listCourses.get(i);
            listCoursesName.add(courses.getName());
            listCoursesDetail.add(courses.getContents());
        }
        Toast.makeText(SelectingCourseActivity.this, "地址和端口为" + mAddress + "," + mPort, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_save:

                showSaveAlertDialog();

                break;
            case R.id.bt_cancel:
                listSelectingAdapter.clearSelectedCourses();
                SelectingCourseActivity.this.finish();
                break;
        }
    }

    public void showSaveAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(SelectingCourseActivity.this).create();
        dialog.setTitle("确认");
        dialog.setMessage("确认保存吗？");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(SelectingCourseActivity.this, "确定", Toast.LENGTH_SHORT).show();
                List<Courses> list = listSelectingAdapter.getListCoursesSelected();
                MyProgressAsynctask mpa = new MyProgressAsynctask();
                mpa.execute(mAddress, mPort, listStu, listSelectingAdapter.getListCoursesSelected(),selectedCourseIds);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SelectingCourseActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    class MyProgressAsynctask extends AsyncTask {//向服务器提交选课数据

        private String mAddress;
        private int mPort;
        private List<Students> listStu;
        private List<Courses> listCourses;//课程是刚刚被选上的课程
        private List<String> selectedCourseIds;

        private Socket socket = null;
        private ObjectOutputStream oos = null;
        private BufferedReader br = null;

        @Override
        protected Object doInBackground(Object[] params) {

            mAddress = (String) params[0];
            mPort = (int) params[1];
            listStu = (List<Students>) params[2];
            listCourses = (List<Courses>) params[3];
            selectedCourseIds= (List<String>) params[4];

            try {
                socket = new Socket(mAddress, mPort);
                oos = new ObjectOutputStream(socket.getOutputStream());
                List<Map<String, List>> lists = new ArrayList<>();
                Map<String, List> map = new HashMap<>();
                List<String> list = new ArrayList<>();
                list.add("saveallselecting");
                map.put("stulist", listStu);
                map.put("courseslist",listSelectingAdapter.getListCoursesSelected());
                System.out.println("getlistCoursesSelected():"+listSelectingAdapter.getListCoursesSelected());
                map.put("note", list);
                lists.add(map);
                String jsonString = JSON.toJSONString(lists);
                oos.writeObject(jsonString);
                oos.flush();
                socket.shutdownOutput();
                System.out.println("doinbackground" + "完成写入");
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String jsonStringGet = null;
                while ((jsonStringGet = br.readLine()) != null) {
                    System.out.println("doinbackground" + jsonStringGet);
                    return ("Y").equals(jsonStringGet);
                }
                socket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {//线程执行完之后的操作
            if ((boolean) o) {
                System.out.println("保存成功");
                Toast.makeText(SelectingCourseActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("保存失败");
                Toast.makeText(SelectingCourseActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
            try {
                if (oos!=null){
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
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

        }
    }

    class GetSelectedCourseAsyncTask extends AsyncTask{//根据学生id获得已选课程的id

        @Override
        protected Object doInBackground(Object[] params) {
            String strStuId=student.getId();
            Socket socket =null;
            ObjectOutputStream oos=null;
            ObjectInputStream ois =null;
            try {
                socket = new Socket(mAddress, mPort);
                oos = new ObjectOutputStream(socket.getOutputStream());
                List<Map<String, String>> list = new ArrayList<>();
                Map<String, String> map = new HashMap<>();
                map.put("stuid", strStuId);
                map.put("note", "getCourseIdList");
                list.add(map);
                String jsonString = JSON.toJSONString(list);
                oos.writeObject(jsonString);
                oos.flush();
                socket.shutdownOutput();
                ois = new ObjectInputStream(socket.getInputStream());
                String jsonString1=null;
                while ((jsonString1= (String) ois.readObject()) != null) {
                    List<String> courseIdList = getObjList(jsonString1, String.class);
                    selectedCourseIds =courseIdList;
                    System.out.println("被选课程的id有：" + selectedCourseIds);
                }
                socket.shutdownInput();
                return true;//返回值的设定
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            listSelectingAdapter = new ListSelectingAdapter(SelectingCourseActivity.this, listCoursesName, listCoursesDetail, (ArrayList<Courses>) listCourses, selectedCourseIds);
            listView.setAdapter(listSelectingAdapter);
            System.out.println("adapter加载成功");
            super.onPostExecute(o);
        }
    }
}


