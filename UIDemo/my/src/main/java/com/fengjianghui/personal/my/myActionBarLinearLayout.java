package com.fengjianghui.personal.my;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fengjianghui on 2016-01-13.
 */
public class myActionBarLinearLayout extends LinearLayout {

    private ImageView imageView_right;
    private TextView textView_center;
    private TextView textView_right;


    public myActionBarLinearLayout(Context context) {
        super(context);
    }

    public myActionBarLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //将actionbar_linearlayout这个布局放在this所指的actionbarlinearlayout中
        inflater.inflate(R.layout.actionbar_linearlayout, this);
        imageView_right = (ImageView) findViewById(R.id.imageView_right);
        textView_center = (TextView) findViewById(R.id.textView_middle);
        textView_right = (TextView) findViewById(R.id.textView_right);

        textView_center.setText("");
        textView_right.setText("");
        imageView_right.setImageResource(0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.actionbarstyle, 0, 0);
        Toast.makeText(context, "调用了2个参数的", Toast.LENGTH_SHORT).show();
        for (int i=0;i<a.length();i++){

            if(a.getString(R.styleable.actionbarstyle_text_center)!=null){
                textView_center.setText(a.getString(R.styleable.actionbarstyle_text_center));
                Toast.makeText(context, "text_center", Toast.LENGTH_SHORT).show();
                break;
            }
            if (a.getString(R.styleable.actionbarstyle_text_right)!=null){
                textView_right.setText(a.getString(R.styleable.actionbarstyle_text_right));
                Toast.makeText(context, "text_right", Toast.LENGTH_SHORT).show();
                break;
            }
            if (a.getResourceId(R.styleable.actionbarstyle_image_right,1)!=1){
                imageView_right.setBackgroundResource(a.getResourceId(R.styleable.actionbarstyle_image_right, 1));
                Toast.makeText(context, "image_right", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        a.recycle();
    }

    public myActionBarLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //将actionbar_linearlayout这个布局放在this所指的actionbarlinearlayout中
        inflater.inflate(R.layout.actionbar_linearlayout, this);
        imageView_right = (ImageView) findViewById(R.id.imageView);
        textView_center = (TextView) findViewById(R.id.textView_middle);
        textView_right = (TextView) findViewById(R.id.textView_right);

        textView_center.setText("");
        textView_right.setText("");
        imageView_right.setImageResource(0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.actionbarstyle, 0, 0);
        Toast.makeText(context, "调用了3个参数的", Toast.LENGTH_SHORT).show();
        for (int i=0;i<a.length();i++){

            if(a.getString(R.styleable.actionbarstyle_text_center)!=null){
                textView_center.setText(a.getString(R.styleable.actionbarstyle_text_center));
                Toast.makeText(context, "text_center", Toast.LENGTH_SHORT).show();
                break;
            }
            if (a.getString(R.styleable.actionbarstyle_text_right)!=null){
                textView_right.setText(a.getString(R.styleable.actionbarstyle_text_right));
                Toast.makeText(context, "text_right", Toast.LENGTH_SHORT).show();
                break;
            }
            if (a.getResourceId(R.styleable.actionbarstyle_image_right,1)!=1){
                imageView_right.setBackgroundResource(a.getResourceId(R.styleable.actionbarstyle_image_right, 1));
                Toast.makeText(context, "image_right", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        a.recycle();
    }
}
