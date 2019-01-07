package com.fengjianghui.com.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.greenrobot.dao.DaoMaster;
import com.greenrobot.dao.DaoSession;
import com.greenrobot.dao.Father;
import com.greenrobot.dao.FatherDao;
import com.greenrobot.dao.Son;
import com.greenrobot.dao.SonDao;

import java.util.List;
import java.util.ListIterator;

import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.daogenerator.Query;


public class Main2Activity extends AppCompatActivity {

    private DaoMaster master;
    private DaoSession session;
    private SQLiteDatabase db;

    private SonDao sonDao;
    private FatherDao fatherDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        openDb();

        //greendao拼装sql语句的log标签
        QueryBuilder.LOG_SQL=true;
        QueryBuilder.LOG_VALUES=true;

//        addPerson();
//        queryAll();
//        queryEq();
//        queryLike();
//        queryBetween();
//        queryOrder();
//        queryFather();
//        queryThread();
        queryOneToOne();
    }

    private void openDb() {
        db = new DaoMaster.DevOpenHelper(Main2Activity.this, "person1.db", null).getWritableDatabase();
        master = new DaoMaster(db);
        session = master.newSession();
        sonDao = session.getSonDao();
        fatherDao = session.getFatherDao();
    }

    private void addPerson() {
        Son son = new Son();
        son.setName("zhangsan_lisi");
        son.setAge(18);
        Father father = new Father();
        father.setName("Tom_lisi");
        father.setAge(45);
        long fatherId = fatherDao.insert(father);
        son.setFatherId(fatherId);
        sonDao.insert(son);

        Son son1 = new Son();
        son1.setName("wangwu");
        son1.setAge(26);
        Father father1 = new Father();
        father1.setName("Tom");
        father1.setAge(45);
        long fatherId1 = fatherDao.insert(father1);
        son1.setFatherId(fatherId1);
        sonDao.insert(son1);

        Son son2 = new Son();
        son2.setName("wangwu");
        son2.setAge(27);
        Father father2 = new Father();
        father2.setName("Tom");
        father2.setAge(45);
        long fatherId2 = fatherDao.insert(father2);
        son2.setFatherId(fatherId2);
        sonDao.insert(son2);
    }

    public void queryAll() {
        //1
//        List<Son> list = sonDao.queryBuilder().list();
        //list()会将所有相关的属性都加载成对象，而listLazy()只会在需要调用相关属性时才会将其转化成对象

        //2
//        LazyList<Son> list = sonDao.queryBuilder().listLazy();
//        list.close();//lazylist需要手动关闭数据库，通过游标关闭
//        for (Son son : list) {
//            Log.i("number","123456"+son);
//        }

        //3
        ListIterator<Son> list = sonDao.queryBuilder().listIterator();
        while (list.hasNext()) {
            Son son=list.next();
            Log.i("number" ,"son:"+son );


        }
    }

    public void queryEq() {
//        Son zhangsan=sonDao.queryBuilder().where(SonDao.Properties.Name.eq("zhangsan")).unique();//查询名字唯一的
        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Name.eq("zhangsan")).list();//查询名字不唯一的
        Log.i("number", "name is equals to" + list.get(0).getName());
    }

    public void queryLike() {
//        Son zhangsan=sonDao.queryBuilder().where(SonDao.Properties.Name.eq("zhangsan")).unique();
        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Name.like("zhangsan%")).list();
        Log.i("number", "name is like zhangsan:" + list);
    }


    public void queryBetween() {
//        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Age.between(20,30)).list();
//        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Age.gt(20)).list();//great than
//        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Age.lt(20)).list();//less than
//        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Age.notEq(18)).list();//不等于18岁的
//        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Age.ge(18)).list();//greater or equals
        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Age.le(18)).list();//less or equals to
        Log.i("number", "age is between 20-30:" + list);
    }

    public void queryOrder() {
        List<Son> list=sonDao.queryBuilder().where(SonDao.Properties.Name.notEq("*")).orderAsc(SonDao.Properties.Age).list();
        Log.i("number", "order asc:" + list);
    }

    /**
     * 用来查询sql语句复杂的情况
     */
    public void queryFather() {//原生sql语句查询
//        de.greenrobot.dao.query.Query<Son> query=sonDao.queryBuilder().where(new WhereCondition.StringCondition("_ID IN"+"SELECT USER_ID FROM_USER_MESSAGE READ FLAG=0")).build();
//        List<Son> list=sonDao.queryBuilder().where(new WhereCondition.StringCondition("FATHER_ID IN "+"(SELECT _ID FROM FATHER WHERE AGE > 20)")).list();
        List<Father> list=fatherDao.queryBuilder().where(new WhereCondition.StringCondition("_ID IN"+"(SELECT FATHER_ID FROM SON WHERE AGE > 25)")).list();
        Log.i("number","原生sql"+list);
    }

    /**
     * 多线程查询
     * 要保证每个线程中定义一个对应的查询对象，用query.forCurrentThread()得到一个具体的query来保证得到具体的线程
     */
    public void queryThread(){
        final de.greenrobot.dao.query.Query<Son> query=sonDao.queryBuilder().build();
        new Thread(){
            @Override
            public void run() {
                List data=query.forCurrentThread().list();
//                query=query.forCurrentThread();
                Log.i("number","thread");
            }
        }.start();
    }

    public void queryOneToOne() {
        List<Son> sons=sonDao.queryBuilder().where(SonDao.Properties.Name.notEq("*")).orderAsc(SonDao.Properties.Age).list();
        for (Son son : sons) {
            String fathername = son.getFather().getName();
            Log.i("number", "get father's name from son:" + fathername);
        }
    }

}
