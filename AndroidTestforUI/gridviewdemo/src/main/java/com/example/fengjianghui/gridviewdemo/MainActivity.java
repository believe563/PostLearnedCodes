package com.example.fengjianghui.gridviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener{//实现监听器

    private GridView gridView;
    //封装gridview的
    private List<Map<String,Object>> dataList;
    //图标
    private int[] icon={R.mipmap.a,
            R.mipmap.b,R.mipmap.c,R.mipmap.d,
            R.mipmap.e,R.mipmap.f,R.mipmap.g,
            R.mipmap.h,R.mipmap.i};
    //图标信息
    private String[] iconName={"a","b","c","d","e","f","g","h","i"};
    //新建适配器
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gridView = (GridView)findViewById(R.id.gridView);
        //1、准备数据源
        //2、新建适配器SimpleAdapter
        //3、让gridview加载适配器
        //4、给gridview配置事件监听器（onItemClickListener，接收单个条目事件的点击）
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this,getData(),R.layout.item,new String[]{"image","text"},new int[]{R.id.image,R.id.textView});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }
    private List<Map<String,Object>> getData(){

        for (int i=0;i<icon.length;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("image",icon[i]);
            map.put("text",iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //podition与iconName的下标是吻合的
        Toast.makeText(this,"我是"+iconName[position],Toast.LENGTH_SHORT).show();
    }
}
