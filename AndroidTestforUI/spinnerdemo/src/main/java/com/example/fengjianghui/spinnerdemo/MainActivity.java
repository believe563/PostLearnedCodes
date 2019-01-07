package com.example.fengjianghui.spinnerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private TextView textView;
    private Spinner spinner;
    private Spinner spinner1;
    private List<String> list;
    private List<Map<String,Object>> listForSimpleAdapter;
    private ArrayAdapter<String> arrayAdapter;
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView= (TextView) findViewById(R.id.textView);
        spinner= (Spinner) findViewById(R.id.spinner);
        spinner1= (Spinner) findViewById(R.id.spinner1);
        textView.setText("您选择的城市是北京");
        //1、设置数据源
        list=new ArrayList<String>();
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");
        //2、新建ArrayAdapter数组适配器,,下面的第二个参数是系统自带的布局
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //3、为Adapter设置一个下拉列表的样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //4、加载适配器
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        listForSimpleAdapter=new ArrayList<Map<String, Object>>();
        simpleAdapter=new SimpleAdapter(this,getData(),R.layout.itemforsimpleadapter,new String[]{"image","text"},new int[]{
            R.id.imageView,R.id.textViewForItem});
        simpleAdapter.setDropDownViewResource(R.layout.itemforsimpleadapter);
        spinner1.setAdapter(simpleAdapter);
        spinner1.setOnItemSelectedListener(this);

    }
    public List<Map<String,Object>> getData(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("text","北京");
        map.put("image", R.mipmap.a);
        listForSimpleAdapter.add(map);
        Map<String,Object> map1=new HashMap<String, Object>();
        map1.put("text","上海");
        map1.put("image",R.mipmap.a);
        listForSimpleAdapter.add(map1);
        Map<String,Object> map2=new HashMap<String, Object>();
        map2.put("text","广州");
        map2.put("image",R.mipmap.a);
        listForSimpleAdapter.add(map2);
        Map<String,Object> map3=new HashMap<String, Object>();
        map3.put("text","深圳");
        map3.put("image",R.mipmap.a);
        listForSimpleAdapter.add(map3);
        return listForSimpleAdapter;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cityName=arrayAdapter.getItem(position);//或者用list.get(position)的方法
        //textView.setText("您选择的城市是"+cityName);
        textView.setText("您选择的是"+simpleAdapter.getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
