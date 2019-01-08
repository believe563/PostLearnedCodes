package com.feng.demo.viewpagerindicatorfortab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SearchFragment extends Fragment {
    private int position;
    public SearchFragment(int position) {
        this.position=position;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01_fragment, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab01);
        tv.setText(TabAdapter.TITLES[position]);
        return view;
    }
}
