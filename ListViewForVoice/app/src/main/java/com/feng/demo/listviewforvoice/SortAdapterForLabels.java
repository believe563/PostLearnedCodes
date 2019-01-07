package com.feng.demo.listviewforvoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/16.
 */
public class SortAdapterForLabels extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> list;
    private LayoutInflater layoutInflater;
    private boolean isLabel;

    public SortAdapterForLabels(Context context, List<Map<String, String>> list, boolean isLabel) {
        super();
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.isLabel = isLabel;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (getItemViewType(position) == 1) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_sortlayout, null);
                holder = new ViewHolder();
                holder.tv1 = (TextView) convertView.findViewById(R.id.tv_1);
                holder.tv2 = (TextView) convertView.findViewById(R.id.tv_2);
                holder.tv3 = (TextView) convertView.findViewById(R.id.tv_3);
                holder.tv4 = (TextView) convertView.findViewById(R.id.tv_4);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Map<String, String> map = list.get(position);
            holder.tv1.setText(map.get("date"));
            holder.tv2.setText(map.get("timelong"));
            holder.tv3.setText(map.get("label"));
            holder.tv4.setText(map.get("detail"));
        } else if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_label_layout, null);
                holder = new ViewHolder();
                holder.tv5 = (TextView) convertView.findViewById(R.id.tv_label);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Map<String, String> map = list.get(position);
            holder.tv5.setText(map.get("label"));
        }
        return convertView;
    }

    class ViewHolder {
        //普通的item用
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

        //标签用
        TextView tv5;
    }


    //用于两种或两种以上不同类型的item重写的方法


    @Override
    public int getItemViewType(int position) {
        Map<String, String> map = list.get(position);
        if (map.containsKey("type")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

}


