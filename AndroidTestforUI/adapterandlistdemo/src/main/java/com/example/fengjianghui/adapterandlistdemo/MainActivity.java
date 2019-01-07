package com.example.fengjianghui.adapterandlistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 先实现在ArrayAdapter（数组实现）和SimpleAdapter（List实现）的适配器设置
 * 再实现监听器设置
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {

    private ListView listView;
    private ArrayAdapter<String> arr_adapter;
    private SimpleAdapter sim_adapter;
    private List<Map<String,Object>> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView= (ListView) findViewById(R.id.listView);

        //1、创建数据源
        //ArrayAdapter的数据源
        String[] arr_data={"慕课网1","慕课网2","慕课网3","慕课网4"};
        //SimpleAdapter的数据源
        dataList=new ArrayList<Map<String,Object>>();

        //1、新建一个数据适配器

        //2、适配器加载数据源
        //ArrayAdapter(上下文，当前listView所加载的每一个列表项所对应的布局文件，数据源)
        arr_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_data);
        /**
         * SimpleAdapter参数
         * 1、context:上下文
         * 2、data：特定泛型的集合组成List,数据源<?extends Map<String,?>>data
         *       一个map对应list列表中的一行
         *       每一个map（键值对）中的键必须包含所在from中所指定的键
         *3、resource：列表一项（item）的布局文件ID
         *4、from:记录map中的键名
         *5、to:绑定数据视图（item）中的ID，与from成对应关系
         */
        sim_adapter=new SimpleAdapter(this,getData(),R.layout.item,new String[]{"pic","text1"},new int[]{R.id.pic,R.id.text1});
        //3、视图加载适配器
//        listView.setAdapter(arr_adapter);
        listView.setAdapter(sim_adapter);

        //添加监听器
        //先实现监听器接口，再设置
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

    }
    private List<Map<String,Object>> getData(){
        for (int i=0;i<20;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("pic",R.drawable.b);
            map.put("text1","慕课网"+i);
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = listView.getItemAtPosition(position) + "";
        Toast.makeText(this, "position=" + position + " text=" + text, Toast.LENGTH_SHORT).show();
    }@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        switch (scrollState){
            case SCROLL_STATE_FLING://FLING滑动 投掷
                Log.i("Main", "用户在手指离开屏幕之前，由于用力滑了一下，视图仍依靠惯性继续滑动");
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("pic",R.drawable.b);
                map.put("text1","增加项");
                dataList.add(map);
                sim_adapter.notifyDataSetChanged();//数据的动态刷新，它可以动态更新视图中所包含的数据
                break;
            case SCROLL_STATE_IDLE://IDLE空闲
                Log.i("Main","视图已经停止滑动");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("Main","视图正在滑动，手指没有离开屏幕");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
