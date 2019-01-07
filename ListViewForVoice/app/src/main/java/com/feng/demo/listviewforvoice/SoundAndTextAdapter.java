package com.feng.demo.listviewforvoice;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/6.
 */
public class SoundAndTextAdapter extends BaseAdapter {
    private Context context;
    private String[] strs;
    private LayoutInflater layoutInflater;

    public SoundAndTextAdapter(Context context, String[] strs) {
        this.context = context;
        this.strs = strs;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_sound);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.bt_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.sound1);
        viewHolder.textView.setText(strs[position]);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ValueAnimator animator = ValueAnimator.ofInt(1, 32);
                animator.setDuration(10000);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                        Integer value = (Integer) animation.getAnimatedValue();
                        switch (value % 3) {
                            case 0:
                                ((ImageView) v).setImageResource(R.drawable.sound1);
                                break;
                            case 1:
                                ((ImageView) v).setImageResource(R.drawable.sound2);
                                break;
                            case 2:
                                ((ImageView) v).setImageResource(R.drawable.sound1);
                                break;
                        }
                    }
                });
                animator.start();
            }
        });
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("文字信息");
                TextView textView = (TextView) dialog.findViewById(R.id.dialog_showText);
                textView.setText(((TextView) v).getText());
                dialog.show();
            }
        });
        return convertView;
    }
}

class ViewHolder {
    ImageView imageView;
    TextView textView;
}