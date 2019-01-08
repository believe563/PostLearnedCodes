package com.example.fengjianghui.contentproviderdemo1;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;

/**
 * 查询所有联系人的cursor并通过查询到的id查询电话号码和email
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //因为contentProvider是现成提供过的，并且uri也是写死的，
        //用的时候将会非常的受限，必须根据现成提供的api进行操作
        //查询联系人
        //在contentProvider系统已经写过(不是自己写的)的前提下，直接写下述语句
        ContentResolver cr = getContentResolver();
        //执行contentResolver里的相关方法：insert、update、delete、query
        /**
         * 第一个参数是固定的uri书写参数，uri从contacts找，contacts类被ContactsContract类取代
         * ContactsContract把contacts做成了自己的内部类
         * ContactsContract.Contacts.CONTENT_URI表示要查询联系人的uri地址
         * 第二个参数表示查询的列，是列名，要写一个数组，把要查的字符都存在第二个函数，
         *     直接写一个数组，把要查的字段直接写上去
         * 第三个参数查询条件selection
         * 第四个条件selectionArgs
         * 第五个参数是排列方式order
         */
        Cursor c = cr.query(Contacts.CONTENT_URI, new String[]{Contacts.
                _ID,Contacts.DISPLAY_NAME}, null, null, null);
        //接下来跟sqlite的操作完全一致
        if (c != null) {
            while (c.moveToNext()) {
//                int id = c.getInt(c.getColumnIndex("_id"));//写成_id有一定的风险性，如果_id写错了将会非常麻烦
                int id = c.getInt(c.getColumnIndex("_id"));
                //可以直接写成ContactsContract.Contacts._IDr
                Log.i("info", "id:" + id);
//                Log.i("info", "name" + c.getString(c.getColumnIndex("display_name")));
                Log.i("info", "name：" + c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME)));
                //display_name与ContactsContract.Contacts.DISPLAY_NAME效果是一样的，display_name必须要翻过数据库才会知道

                //根据人的id把所有的电话号码查出来
                //游标里又套游标
                /**
                 * 第一个参数是查询所有联系人的电话号码
                 //第二个参数是projection是电话号码和电话号码的类型

                 */
                Cursor c1=cr.query(Phone.CONTENT_URI,new String[]{Phone.NUMBER, Phone.TYPE},Phone.CONTACT_ID + "=" +id, null, null);
                if (c1!=null){
                    while (c1.moveToNext()) {
                        //取出电话号码的类型，用0、1、2表示 0--座机 1--移动电话
                        int type=c1.getInt(c1.getColumnIndex(Phone.TYPE));
                        Log.i("info", ""+type);
                        if (type==Phone.TYPE_HOME) {
                            Log.i("info", "家庭电话：" + c1.getString(c1.getColumnIndex(Phone.NUMBER)));
                        }else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                            Log.i("info", "手机号：" + c1.getString(c1.getColumnIndex(Phone.NUMBER)));
                        }else{
                            break;
                        }
                        //Log.i("info", "电话号码：" + c1.getString(c1.getColumnIndex(Phone.NUMBER)));
                    }
                }
                c1.close();


                //根据联系人的id查询出联系人的邮箱地址
                //第二个参数：所有联系人的邮箱和邮箱的类型
                Cursor c2 = cr.query(Email.CONTENT_URI, new String[]{Email.DATA,Email.TYPE},Email.CONTACT_ID + "=" + id, null, null);
                //一个人可能有多个邮箱
                if (c2!=null){
                    while (c2.moveToNext()) {
                        int type=c2.getInt(c2.getColumnIndex(Email.TYPE));
                        if (type==Email.TYPE_HOME) {
                            Log.i("info", "家庭邮箱"+c2.getString(c2.getColumnIndex(Email.DATA)));
                        }else{
                            break;
                        }
                    }
                }
                c2.close();
            }
        }
        c.close();
    }
}
