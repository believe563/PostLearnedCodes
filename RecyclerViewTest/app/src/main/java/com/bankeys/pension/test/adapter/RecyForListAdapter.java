package com.bankeys.pension.test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bankeys.pension.test.R;


/**
 * Created by zhang on 2016/6/23.
 */
public class RecyForListAdapter extends RecyclerView.Adapter<RecyForListAdapter.RecyForList> implements View.OnClickListener {
    private OnItemclickListener onItemclickListener;

    @Override
    public RecyForList onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_item, parent, false);
        return new RecyForList(v);
    }

    @Override
    public void onBindViewHolder(final RecyForList holder, final int position) {
        //数据绑定
        if (onItemclickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    onItemclickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public void onClick(View v) {

    }


    //    自定义onclick事件
    public interface OnItemclickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemclickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
    }

    public static class RecyForList extends RecyclerView.ViewHolder {
        TextView tv_title, tv_content,tv_time;

        public RecyForList(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.textview1);
            tv_time = (TextView) itemView.findViewById(R.id.textview2);
            tv_content = (TextView) itemView.findViewById(R.id.textview3);
        }
    }


}

