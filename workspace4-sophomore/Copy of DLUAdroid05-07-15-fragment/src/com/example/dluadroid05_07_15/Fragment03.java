package com.example.dluadroid05_07_15;
import com.example.dluadroid05_07_15.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 创建一个fragment
 *
 */
public class Fragment03 extends Fragment{

	private View view;
	private TextView textView;
	private ImageView iv;
	//覆写父类的oncreateview方法
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment03, container,false);
		textView=(TextView) view.findViewById(R.id.textview);
		textView.setText("fragment03");
		iv=(ImageView) view.findViewById(R.id.iv);
		iv.setBackgroundResource(R.drawable.fragment3);
		return view;
	}
}
