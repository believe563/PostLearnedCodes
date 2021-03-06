package com.example.dluadroid04;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//决定每行list有多少行数据，每行数据是什么
public class MYAdapter2 extends BaseAdapter{

	private Context context;
	private ArrayList<Object> list;
	
	public MYAdapter2(Context context, ArrayList<Object> list) {
		super();
		this.context = context;
		this.list = list;
	}
	@Override//首先调用getcount方法获得它的条数
	public int getCount() {//决定listview有多少条数据
		return list.size();
	}

	

	@Override
	public Object getItem(int position) {//暂时不用
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {//暂时不用
		// TODO Auto-generated method stub
		return 0;
	}

	@Override//position第几行
	public View getView(int position, View convertView, ViewGroup parent) {
		//通过xml文件创建一个view的对象
		View view=LayoutInflater.from(context).inflate(R.layout.item1, null);
		TextView tvTextView=(TextView) view.findViewById(R.id.tv1);
		//根据position确定当前应该显示哪条数据
		HashMap<String,Object> map=(HashMap<String, Object>) list.get(position);
		String title=(String) map.get("title");
		tvTextView.setText(title);
		return view;
	}
	
}
