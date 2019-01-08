package com.courseselecting.onlinesystem.ocourseselectings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ListMenuAdapter extends BaseAdapter {
    private ArrayList<String> mList_menu;
    private Context context;
    private LayoutInflater inflater;

    public ListMenuAdapter(ArrayList<String> listmenu, Context context) {
        this.mList_menu = listmenu;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList_menu.size();
    }

    @Override
    public Object getItem(int position) {
        return mList_menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.menuitem_layout, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList_menu.get(position));
//        viewHolder.textView.setBackgroundResource();
//        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
//            }
//        });
//        http://blog.csdn.net/listening_music/article/details/6965755
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
    }
}

//instanceof是Java的一个二元操作符，和==，>，<是同一类东东。由于它是由字母组成的，所以也是Java的保留关键字。它的作用是测试它左边的对象是否是它右边的类的实例，返回boolean类型的数据。举个例子：
//
//        　　String s = "I AM an Object!";
//        　　boolean isObject = s instanceof Object;
//
//        　　我们声明了一个String对象引用，指向一个String对象，然后用instancof来测试它所指向的对象是否是Object类的一个实例，显然，这是真的，所以返回true，也就是isObject的值为True。
