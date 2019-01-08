package com.example.dluadroid05_07_15;
import java.util.ArrayList;

import baoxue.MyAdapter;
import db.SQLiteReadHelper02;
import entity.BaoXueYouXi;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 创建一个fragment
 *
 */
public class Fragment02 extends Fragment{
	private ListView lv;
	private ArrayList<BaoXueYouXi> dataList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment02, container,false);
		lv=(ListView) view.findViewById(R.id.lv);
        SQLiteReadHelper02 helper = new SQLiteReadHelper02(getActivity(), "neteasygame.db",R.raw.neteasygame);
		dataList = (ArrayList<BaoXueYouXi>) helper.getDatabaseData();
		lv.setAdapter(new MyAdapter(getActivity(),dataList));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 Uri uri = Uri.parse(dataList.get(position).getImgsrc());  
				 Intent it = new Intent(Intent.ACTION_VIEW, uri);  
				 startActivity(it);
			}
		});
		return view;
	}
}
