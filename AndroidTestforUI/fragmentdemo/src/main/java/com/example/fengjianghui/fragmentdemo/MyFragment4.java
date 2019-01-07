package com.example.fengjianghui.fragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/9/21.
 */
public class MyFragment4 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Main","MyFragment4-----onCreateView()");
        View view=inflater.inflate(R.layout.fragment2,container,false);
        TextView textView2= (TextView) view.findViewById(R.id.textView2);
        textView2.setText("第四个Fragment");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Main", "MyFragment4-----onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Main", "MyFragment4-----onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Main", "MyFragment4-----onActivityCreated()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Main", "MyFragment4-----onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Main", "MyFragment4-----onPause()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Main", "MyFragment4-----onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Main", "MyFragment4-----onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Main", "MyFragment4-----onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Main", "MyFragment4-----onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Main", "MyFragment4-----onDetach()");
    }
}
