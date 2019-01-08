package com.example.dluadroid04;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

public class SecondActivity extends Activity {
	private ListView lv;
	private ArrayList<?extends Parcelable> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.lv);
		list=getIntent().getParcelableArrayListExtra("data");
		lv.setAdapter(new MYAdapter1(this, (ArrayList<Object>)list));
		lv.setOnItemClickListener(new MyListener1(this,(ArrayList<Object>) list));
	}
}
