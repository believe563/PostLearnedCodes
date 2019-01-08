package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import entity.Students;

/**
 * 学生登录页面
 */
public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //三个下拉菜单分别对应年级、班级和姓名
    private Spinner spinner_grades;
    private Spinner spinner_classes;
    private Spinner spinner_name;

    //用于存放年级、班级、姓名信息的数组
    private String[] strGrades;
    private String[] strClasses;
    private String[] strNames;
    AutoCompleteTextView actv_Login;
    private Button btLogin;
    private ProgressBar progressBar;

    //spinner的适配器
    private ArrayAdapter<String> adapter_grades;
    private ArrayAdapter<String> adapter_classes;
    private ArrayAdapter<String> adapter_names;

    //当前被选中的年级、班级和姓名
    private String grade;
    private String clazz;
    private String name;
    private String pwd;

//    private String mAddress = "192.168.3.102";
//    private String mAddress = "10.0.2.2";
    private String mAddress = "42.96.197.81";

    private int mPort = 12344;

    //将json字符串解析为List
    public <T> List<T> getObjList(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString, cls);
    }

    //用于将子线程的数据返回到主线程
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String stringGrades = (String) msg.obj;
                //这里必须是List类型
                List<String> strGrades1 = getObjList(stringGrades, String.class);
                strGrades = new String[strGrades1.size()];
                for (int i = 0; i < strGrades1.size(); i++) {
                    strGrades[i] = strGrades1.get(i);
                    System.out.println(strGrades[i]);
                }
                setAdapterForGradeSpinner();
                spinner_grades.invalidate();
            } else if (msg.what == 2) {
                String stringClasses = (String) msg.obj;
                List<String> strClasses1 = getObjList(stringClasses, String.class);
                strClasses = new String[strClasses1.size()];
                for (int i = 0; i < strClasses1.size(); i++) {
                    strClasses[i] = strClasses1.get(i);
                    System.out.println(strClasses[i]);
                }
                setAdapterForClassSpinner();
                spinner_classes.invalidate();
            } else if (msg.what == 3) {
                String stringNames = (String) msg.obj;
                List<String> strNames1 = getObjList(stringNames, String.class);
                System.out.println("msg.what" + 3);
                strNames = new String[strNames1.size()];
                for (int i = 0; i < strNames1.size(); i++) {
                    strNames[i] = strNames1.get(i);
                    System.out.println(strNames[i]);
                }
                setAdapterForNameSpinner();
                spinner_name.invalidate();
            } else if (msg.what == 4) {
                Toast.makeText(MainActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 5) {//验证成功，执行登录操作
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //要先把自己定义的类装在list里才能转化成Serializable类型放在bundle里
                List<Students> stuList = new ArrayList<>();
                stuList.add((Students) msg.obj);
//                intent.putParcelableArrayListExtra("student", stuList);
                Bundle bundle = new Bundle();
                bundle.putSerializable("student",(Serializable)stuList);
//                bundle.putSerializable("student", (Students) msg.obj);

                intent.putExtras(bundle);
                intent.putExtra("address", mAddress);
                intent.putExtra("port",mPort);
                intent.setClass(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
            progressBar.setVisibility(View.GONE);
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置状态栏半透明


        actv_Login = (AutoCompleteTextView) findViewById(R.id.actv_pwd);
        actv_Login.setOnFocusChangeListener(new View.OnFocusChangeListener() {//让键盘回去的操作，没有用。。。
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!hasFocus) {//获取v依附在activity的令牌
                    manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    manager.showSoftInput(v, 1);
                }
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.pb_wait);
        progressBar.setVisibility(View.GONE);

        //对spinner的布局
        spinner_grades = (Spinner) findViewById(R.id.spinner_grades);
        spinner_classes = (Spinner) findViewById(R.id.spinner_classes);
        spinner_name = (Spinner) findViewById(R.id.spinner_names);

        btLogin = (Button) findViewById(R.id.bt_login);

        //设置网络在无连接下的情况
        setSpinnerAdapter_nonet();

        //判断网络是否连接
        if (isNetworkConnected(this)) {
            //连接的话获取网络上的年级信息
            getAllGrades();
        } else {
            //提示用户网络未连接
            Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
        }
        //设置监听事件
        spinner_grades.setOnItemSelectedListener(this);
        spinner_classes.setOnItemSelectedListener(this);
        spinner_name.setOnItemSelectedListener(this);

        btLogin.setOnClickListener(this);
    }


    //获得对应年级和班级的所有姓名
    private void getAllNames() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Socket socket = null;
                ObjectInputStream ois = null;
                ObjectOutputStream oos = null;
                try {
                    socket = new Socket(mAddress, mPort);
                    //请求班级信息
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    Map<String, String> map = new HashMap<>();
                    List<Map<String, String>> list = new ArrayList<>();
                    map.put("grade", grade);
                    map.put("clazz", clazz);
                    list.add(map);

                    String jsonStringlist = JSON.toJSONString(list);
                    System.out.println(jsonStringlist);
                    oos.writeObject(jsonStringlist);
                    oos.flush();
                    socket.shutdownOutput();

                    //获得服务器返回的班级信息
                    ois = new ObjectInputStream(socket.getInputStream());
                    String stringNames = null;

                    while ((stringNames = (String) ois.readObject()) != null) {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = stringNames;
                        handler1.sendMessage(msg);
                    }
                    socket.shutdownInput();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (ois!=null) {
                            ois.close();
                        }if(oos!=null) {
                            oos.close();
                        }if(socket!=null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //获得对应年级对应的班级
    private void getAllClasses() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Socket socket = null;
                ObjectInputStream ois = null;
                ObjectOutputStream oos = null;
                try {
                    socket = new Socket(mAddress, mPort);
                    //请求班级信息
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    Map<String, String> map = new HashMap<>();
                    List<Map<String, String>> list = new ArrayList<>();
                    map.put("grade", grade);
                    list.add(map);

                    String jsonStringlist = JSON.toJSONString(list);
                    System.out.println(jsonStringlist);
                    oos.writeObject(jsonStringlist);
                    oos.flush();
                    socket.shutdownOutput();

                    ois = new ObjectInputStream(socket.getInputStream());
                    String stringClasses = null;

                    while ((stringClasses = (String) ois.readObject()) != null) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = stringClasses;
                        handler1.sendMessage(msg);
                    }
                    socket.shutdownInput();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (ois != null) {
                            ois.close();
                        } else if (oos != null) {
                            oos.close();
                        } else if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setAdapterForGradeSpinner() {
        adapter_grades = new ArrayAdapter<String>(this, R.layout.spinner_display_style, R.id.textView, strGrades);
        adapter_grades.setDropDownViewResource(R.layout.spinner_dropdown_style);
        spinner_grades.setAdapter(adapter_grades);
    }

    public void setAdapterForClassSpinner() {
        adapter_classes = new ArrayAdapter<String>(this, R.layout.spinner_display_style, R.id.textView, strClasses);
        adapter_classes.setDropDownViewResource(R.layout.spinner_dropdown_style);
        spinner_classes.setAdapter(adapter_classes);
    }

    public void setAdapterForNameSpinner() {
        adapter_names = new ArrayAdapter<String>(this, R.layout.spinner_display_style, R.id.textView, strNames);
        adapter_names.setDropDownViewResource(R.layout.spinner_dropdown_style);
        spinner_name.setAdapter(adapter_names);
    }

    //网络未连接情况下的adapter
    private void setSpinnerAdapter_nonet() {//没有网的情况下为设置spinner显示情况
        strGrades = getApplicationContext().getResources().getStringArray(R.array.grades_detail);
        strClasses = getApplicationContext().getResources().getStringArray(R.array.classes_detail);
        strNames = getApplicationContext().getResources().getStringArray(R.array.names_detail);

        adapter_grades = new ArrayAdapter<String>(this, R.layout.spinner_display_style, R.id.textView, strGrades);
        adapter_classes = new ArrayAdapter<String>(this, R.layout.spinner_display_style, R.id.textView, strClasses);
        adapter_names = new ArrayAdapter<String>(this, R.layout.spinner_display_style, R.id.textView, strNames);


        adapter_grades.setDropDownViewResource(R.layout.spinner_dropdown_style);
        adapter_classes.setDropDownViewResource(R.layout.spinner_dropdown_style);
        adapter_names.setDropDownViewResource(R.layout.spinner_dropdown_style);

        spinner_grades.setAdapter(adapter_grades);
        spinner_classes.setAdapter(adapter_classes);
        spinner_name.setAdapter(adapter_names);

    }


    //判断网络是否连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //获得所有的年级
    private void getAllGrades() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                ObjectInputStream ois = null;
                ObjectOutputStream oos = null;
                try {
                    socket = new Socket(mAddress, mPort);
                    //请求年级信息
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject("1");
                    oos.flush();
                    socket.shutdownOutput();

                    //返回年级信息
                    ois = new ObjectInputStream(socket.getInputStream());
                    String stringGrades = null;
                    while ((stringGrades = (String) ois.readObject()) != null) {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = stringGrades;
                        handler1.sendMessage(msg);
                    }
                    socket.shutdownInput();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (ois != null) {
                            ois.close();
                        } else if (oos != null) {
                            oos.close();
                        } else if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner_grades:
                grade = spinner_grades.getSelectedItem().toString();
//                Toast.makeText(MainActivity.this, "grade" + grade, Toast.LENGTH_SHORT).show();
                if (isNetworkConnected(this)) {
                    //连接的话获取网络上的年级信息
                    progressBar.setVisibility(View.VISIBLE);
                    getAllClasses();
                    //setSpinnerAdapter_net();
                } else {
                    //提示用户网络未连接
                    Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                    //setSpinnerAdapter_nonet();
                }
                break;
            case R.id.spinner_classes:
                clazz = spinner_classes.getSelectedItem().toString();
//                Toast.makeText(MainActivity.this, "clazz" + clazz, Toast.LENGTH_SHORT).show();
                if (isNetworkConnected(this)) {
                    progressBar.setVisibility(View.VISIBLE);
                    //连接的话获取网络上的年级信息
                    getAllNames();
                    //setSpinnerAdapter_net();
                } else {
                    //提示用户网络未连接
                    Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
                    //setSpinnerAdapter_nonet();
                }
                break;
            case R.id.spinner_names:
                name = spinner_name.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, "name" + name, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                checkLoginInfo();
                break;
        }
    }

    //如果密码正确就获取学生的全套信息，如果不正确就重新输密码
    private void checkLoginInfo() {
        pwd = String.valueOf(actv_Login.getText());
        if (pwd.length() == 0 || pwd == null) {
            Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (pwd.length() < 6) {
            Toast.makeText(MainActivity.this, "密码至少为6位", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            checkPwd();
        }
    }

    private boolean checkPwd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;
                String jsonStringLogin = null;
                String jsonStringStu = null;
                try {
                    Socket socket = new Socket(mAddress, mPort);
                    List<Map<String, String>> list = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
                    map.put("grade", grade);
                    map.put("clazz", clazz);
                    map.put("name", name);
                    map.put("pwd", pwd);
                    map.put("note","getOneStuInfo");
                    list.add(map);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    jsonStringLogin = JSON.toJSONString(list);
                    oos.writeObject(jsonStringLogin);
                    oos.flush();
                    socket.shutdownOutput();

                    ois = new ObjectInputStream(socket.getInputStream());
                    while ((jsonStringStu = (String) ois.readObject()) != null) {
                        Message msg = new Message();
                        Students stu = JSON.parseObject(jsonStringStu, Students.class);
                        if (stu!=null) {
                            System.out.println("Students" + stu.getId());
                            msg.what = 5;
                            msg.obj = stu;
                        }else{
                            msg.what=4;
                        }
                        handler1.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return false;
    }
}
