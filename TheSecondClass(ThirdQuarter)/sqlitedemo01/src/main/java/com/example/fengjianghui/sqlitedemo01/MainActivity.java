package com.example.fengjianghui.sqlitedemo01;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //每个程序都有自己的数据库，默认的情况下是各自互相不干扰
        //创建一个数据库，并且打开
        /***
         * 参数：
         * 第一个数据库名字，没有格式，后面的格式可以不写，通常写为.db
         *     有格式的话导出可以拿第三方软件打开，不然还得加格式
         * 第二个是访问方式
         * 第三个是实例化的查询游标
         */
        SQLiteDatabase db=openOrCreateDatabase("fjh.db",MODE_PRIVATE,null);
        //之后在ddms中的data目录相应路径下会有一张表出现，名为fjh.db
        db.execSQL("create table if not exists usertb(_id integer primary key autoincrement,name text not null,age integer not null,sex text not null)");//这样是为了防止多次创建
        db.execSQL("insert into usertb(name,sex,age) values('张三','女',18)");
        //数据库查询时，当要显示到listview里用适配器的话。。。。adapter的话如果主键不是_id将会出现问题
        db.execSQL("insert into usertb(name,sex,age) values('李四','女',19)");
        db.execSQL("insert into usertb(name,sex,age) values('王五','男',20)");
        //参数分别是查询语句和条件
        Cursor c=db.rawQuery("select * from usertb", null);//可以理解为把数据变为了list
        if (c!=null){//表示查询到了
            while (c.moveToNext()){
                Log.i("info","id:"+c.getInt(c.getColumnIndex("_id")));//c.getInt(0)表示取出第一列的数据
                Log.i("info","name:"+c.getString(c.getColumnIndex("name")));
                Log.i("info","age"+c.getInt(c.getColumnIndex("age")));
                Log.i("info","sex:"+c.getString(c.getColumnIndex("sex")));

            }
            //选对位置释放游标
            c.close();
        }
        //最后要把数据库对象释放掉，不然内存还是没有全部释放
        db.close();
    }
}
