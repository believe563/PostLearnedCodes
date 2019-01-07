package com.example.fengjianghui.baseadapterdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fengjianghui on 2015/9/30.
 */
public class MyAdapter extends BaseAdapter {
    private List<ItemBean>itemBeanList;
    private LayoutInflater layoutInflater;
    private long sumTime;

    public MyAdapter(Context context,List<ItemBean>itemBeanList) {
        this.itemBeanList = itemBeanList;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return itemBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //通过layoutInflater的方法就可以将xml文件转化为view
        //第一个 参数是要加载到每一项中的布局文件
        //逗比式，没有使用listView中的缓存机制》》》》》》》》》》
//        View view = layoutInflater.inflate(R.layout.item, null);
//        ImageView imageView= (ImageView) view.findViewById(R.id.imageView);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
//        ItemBean bean = itemBeanList.get(position);
//        imageView.setImageResource(bean.itemImageResId);
//        tvTitle.setText(bean.itemTitle);
//        tvContent.setText(bean.itemContent);
//        return view;

        //普通式》》》》》》》》》》如果convertview已缓存则不用创建新的view
        //使用系统的参数convertView
//        if (convertView==null) {//即没有被缓存过
//            convertView = layoutInflater.inflate(R.layout.item, null);
//        }
//        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView);
//        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
//        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
//        ItemBean bean = itemBeanList.get(position);
//        imageView.setImageResource(bean.itemImageResId);
//        tvTitle.setText(bean.itemTitle);
//        tvContent.setText(bean.itemContent);
//        return convertView;
        //文艺式 》》》》》》》》》》》》对findViewByid进行优化  用viewHolder
        long start=System.nanoTime();//获取系统的纳秒时间
        ViewHolder viewHolder;
        if (convertView==null){
            System.out.println("null-----null");
            viewHolder=new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.TvContent = (TextView) convertView.findViewById(R.id.tvContent);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ItemBean bean = itemBeanList.get(position);
        viewHolder.imageView.setImageResource(bean.itemImageResId);
        viewHolder.tvTitle.setText(bean.itemTitle);
        viewHolder.TvContent.setText(bean.itemContent);
        long end=System.nanoTime();
        long dValue=end-start;
        sumTime += dValue;
        Log.i("Main", String.valueOf(sumTime));
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView tvTitle;
        public TextView TvContent;
    }
}
