package com.imooc.tab04;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment
{
	private int pos;

	@SuppressLint("ValidFragment")
	public TabFragment(int pos)
	{
		this.pos = pos;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.frag, container, false);
		TextView tv = (TextView) view.findViewById(R.id.id_tv);
		tv.setText(TabAdapter.TITLES[pos]);
		return view;
	}
}
