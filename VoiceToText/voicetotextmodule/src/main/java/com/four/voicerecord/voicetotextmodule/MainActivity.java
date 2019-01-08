package com.four.voicerecord.voicetotextmodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Toast mToast;

    @SuppressLint("ShowToast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
//		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        SimpleAdapter listitemAdapter = new SimpleAdapter();
        ((ListView) findViewById(R.id.listview_main)).setAdapter(listitemAdapter);
    }

    @Override
    public void onClick(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        Intent intent = null;
        switch (tag) {
            case 0:
                // 语音转写
			intent = new Intent(MainActivity.this, IatDemo.class);
                mToast = Toast.makeText(this, "在线语音识别", Toast.LENGTH_SHORT);
                break;
            case 1:
                // 语音合成
//			intent = new Intent(MainActivity.this, TtsDemo.class);
                mToast = Toast.makeText(this, "文本转化为语音", Toast.LENGTH_SHORT);
                break;
            case 2:
                // 语音评测
//			intent = new Intent(MainActivity.this, IseDemo.class);
                mToast = Toast.makeText(this, "语音评测", Toast.LENGTH_SHORT);
                break;
        }
        mToast.show();
        if (intent != null) {
            startActivity(intent);
        }
    }

    // Menu 列表
    String items[] = {"在线语音识别", "文本转化为语音", "语音评测"};

    private class SimpleAdapter extends BaseAdapter {
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View mView = factory.inflate(R.layout.list_items, null);
                convertView = mView;
            }

            TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            tv_item.setOnClickListener(MainActivity.this);
            tv_item.setTag(position);
            tv_item.setText(items[position]);

            return convertView;
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

}
