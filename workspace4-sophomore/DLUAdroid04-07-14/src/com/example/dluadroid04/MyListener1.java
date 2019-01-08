package com.example.dluadroid04;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MyListener1 implements OnItemClickListener{

	private Context context;
	private ArrayList<Object> list;
	
	public MyListener1(Context context, ArrayList<Object> list) {
		super();
		this.context = context;
		this.list = list;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//获得当前点击的省对应的map
		HashMap<String,Object> cityMap=(HashMap<String, Object>) list.get(position);
		//获得对应的城市数组
		ArrayList<Object> areaList=(ArrayList<Object>) cityMap.get("sub");//备用
		//创建一个intent对象,由哪个activity跳到哪个activity
		Intent intent=new Intent(context,ThirdActivity.class);
		//将城市数组存入intent中
		intent.putParcelableArrayListExtra("data",(ArrayList<? extends Parcelable>) areaList);
		context.startActivity(intent);
	}
	
}
