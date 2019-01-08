package com.example.dluadroid04;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MyListener implements OnItemClickListener{

	private Context context;
	private ArrayList<Object> list;
	
	public MyListener(Context context, ArrayList<Object> list) {
		super();
		this.context = context;
		this.list = list;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//获得当前点击的省对应的map
		HashMap<String,Object> proMap=(HashMap<String, Object>) list.get(position);
		//获得对应的城市数组
		ArrayList<Object> cityList=(ArrayList<Object>) proMap.get("sub");//备用
		//创建一个intent对象,由哪个activity跳到哪个activity
		Intent intent=new Intent(context,SecondActivity.class);
		//将城市数组存入intent中
		intent.putParcelableArrayListExtra("data",(ArrayList<? extends Parcelable>) cityList);
		context.startActivity(intent);
	}
	
}
