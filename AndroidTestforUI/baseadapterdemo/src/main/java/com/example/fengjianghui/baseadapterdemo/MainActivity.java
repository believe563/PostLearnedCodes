package com.example.fengjianghui.baseadapterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<ItemBean> itemBeanList = new ArrayList<>();
        for (int i=0;i<20;i++) {
            itemBeanList.add(new ItemBean(R.mipmap.ymr,"我是标题"+i,"我是内容"+i));
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(this,itemBeanList));
    }


}
