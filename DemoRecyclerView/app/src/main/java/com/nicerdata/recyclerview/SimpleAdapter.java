package com.nicerdata.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 传统的BaseAdapter会在gridView中去进行创建ViewHolder和对应的View进行赋值，
 * 而在该Adapter中，将这个过程分为了2步，由该两个方法完成：onCreateViewHolder，onBindViewHolder
 * （可以看出Google开始强制开发者使用ViewHolder模式了）
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    protected List<String> mDatas;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //构造方法
    public SimpleAdapter(Context context, List<String> datas) {
        //成员变量进行赋值
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    /*
    创建ViewHolder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.item_single_textview, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /*
    绑定ViewHolder的数据
     */
    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int pos) {
        myViewHolder.tv.setText(mDatas.get(pos));

        setUpitemEvent(myViewHolder);
    }

    protected void setUpitemEvent(final MyViewHolder myViewHolder) {

        if (mOnItemClickListener != null) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = myViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(myViewHolder.itemView, layoutPosition);
                }
            });

            //long click
            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = myViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(myViewHolder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int pos) {
        mDatas.add(pos, "Insert One");
//        notifyDataSetChanged();
        notifyItemInserted(pos);
    }

    public void deleteData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

}

class MyViewHolder extends ViewHolder {

    //当前item的所有的控件
    TextView tv;

    public MyViewHolder(View v) {
        super(v);
        tv = (TextView) v.findViewById(R.id.id_tv);
    }
}
