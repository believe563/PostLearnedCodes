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
import android.widget.Toast;

/**
 * Created by fengjianghui on 2015/9/23.
 */
public class MyFragment5 extends Fragment {
    MyListener myListener;

    //定义一个接口，为了让Activity实现它从而接收从这边传过去的数据
    public interface MyListener{
        public void thank(String code);
    }

    @Override
    public void onAttach(Activity activity) {
//        Log.i("log","on attach Fragment5");
        myListener= (MyListener) activity;//创建一个Activity对像，并获得宿主activity的对象
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        TextView textView2= (TextView) view.findViewById(R.id.textView2);
        //不设置文本内容，让文本内容正好等于传过来的数据
        String text=getArguments().get("name").toString();
        textView2.setText(text);
        Toast.makeText(getActivity(),"已成功接收到"+text, Toast.LENGTH_SHORT).show();
        String code="Thank you,Activity!";//在上面写一个内部接口
        Toast.makeText(getActivity(),"向activity发送"+code, Toast.LENGTH_SHORT).show();
        //将数据传回activity
        myListener.thank(code);
        return view;
    }
}
