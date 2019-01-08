package com.example.fengjianghui.contentproviderdemo2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

/**
 *用系统自带的contentProvider进行联系人的插入和数据的更新
 * MIMETYPE：通用因特网邮件扩充(Multipurpose Internet Mail Extensions type)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到contentResolver对象
        ContentResolver cr=getContentResolver();
        //向联系人中插入一行数据
        ContentValues contentValues = new ContentValues();
        //根据第一个参数的uri插入一个第二个参数的contentvalues对象
        //以后通过这个返回的uri获取数据
        Uri uri = cr.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        //向上述返回的uri插入数据
        //解析uri得到一个long值
        Long raw_contact_id=ContentUris.parseId(uri);

        //将数据加入到contentValues中
        //先清空一下
        contentValues.clear();
        //所对应的联系人
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID, raw_contact_id);
        //向里面追加信息
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,"章三三");
        //指定好MIME类型为一条记录
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        //把数据插入到Data.CONTENT_URI这个id
        uri=cr.insert(ContactsContract.Data.CONTENT_URI,contentValues);//即插入姓名成功
        //插入电话信息
        contentValues.clear();

        contentValues.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, raw_contact_id);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER,"18842648396");
        contentValues.put(ContactsContract.CommonDataKinds.Phone.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        //根据uri插入所对应的数据
        uri = cr.insert(ContactsContract.Data.CONTENT_URI, contentValues);
    }
}

