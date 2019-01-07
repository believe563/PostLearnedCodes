package com.fengjianghui.com.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Person;
import greendao.PersonDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText age;
    private Button add;
    private ListView listView;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private PersonDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        openDb();
        getAll();
    }


    /**
     * 1-添加数据的初始步骤--打开数据库
     */
    private void openDb() {
        helper = new DaoMaster.DevOpenHelper(MainActivity.this, "Person.db", null);//helper创建的模式是固定的，环境变量、数据库名、游标名
        master = new DaoMaster(helper.getWritableDatabase());//参数是SQLiteDatabase，用helper获得
        session = master.newSession();
        dao = session.getPersonDao();
    }

    /**
     * 2-插入数据
     */
    public void save() {
        Person person = new Person(Long.valueOf(4), name.getText().toString(), null, Integer.valueOf(age.getText().toString()), new Date());
        long id=dao.insert(person);//将person插入数据库
        name.setText("");
        age.setText("");
        Log.i("number", "person id:" + id);
    }

    /**
     * 查询方法
     */
    public void getAll() {
        List<Person> list=dao.queryBuilder().list();//返回的list就是查询的对象
        Log.i("number", "all person" + list);
        for (Person person : list) {
            delete(person);
        }
        list=dao.queryBuilder().list();//返回的list就是查询的对象
        Log.i("number", "all person" + list);

    }


    /**
     * @param person
     * 修改数据库中的数据
     */
    public void update(Person person) {
        dao.insertOrReplace(person);
    }

    public void delete(Person person) {
        dao.delete(person);
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        add = (Button) findViewById(R.id.add);
        listView = (ListView) findViewById(R.id.listView);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        save();
    }
}
