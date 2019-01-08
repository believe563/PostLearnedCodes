package com.bankeys.pension.test.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bankeys.pension.test.R;


/**
 * Created by zhang on 2016/6/23.
 */
public class RecyForStaggeredAdapter extends RecyclerView.Adapter<RecyForStaggeredAdapter.RecyForList> implements View.OnClickListener {
    private RecyForListClickListener listener;
    Context context;

    private int[] dd = new int[]{R.drawable.h1438342866771, R.drawable.h1453679686433_1459497492682, R.drawable.h1453679686433_1459497492682,
            R.drawable.h1453679686433_1459497492682
            , R.drawable.h1465209662128, R.drawable.h1463889990992};

    public void setListener(RecyForListClickListener listener) {
        this.listener = listener;
    }

    public RecyForStaggeredAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyForList onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_item, parent, false);
        int height = (int) (300 + Math.random() * 50);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 20;
        layoutParams.topMargin = 20;
        layoutParams.leftMargin = 20;
        layoutParams.rightMargin = 20;
        v.setLayoutParams(layoutParams);
        return new RecyForList(v);
    }
    @TargetApi(16)
    @Override
    public void onBindViewHolder(RecyForList holder, int position) {
        //数据绑定
        holder.itemView.setBackground(context.getResources().getDrawable(dd[position]));
    }

    @Override
    public int getItemCount() {
        return dd.length;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRecyForListClickListener();
        }
    }

    public static class RecyForList extends RecyclerView.ViewHolder {
        TextView tv_title, tv_content;
        View itemView;

        public RecyForList(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public interface RecyForListClickListener {
        public void onRecyForListClickListener();
    }
}
