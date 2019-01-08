package com.courseselecting.onlinesystem.ocourseselectings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/3/10.
 */
public class ClearableAutoCompleteTextView extends AppCompatAutoCompleteTextView implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;
    private Drawable mClearTextIcon;

    public ClearableAutoCompleteTextView(Context context) {
        super(context);
        init(context);
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.clear);//叉号
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);//擦除
        DrawableCompat.setTint(wrappedDrawable,getCurrentHintTextColor());
        mClearTextIcon=wrappedDrawable;
        mClearTextIcon.setBounds(-20, 0, mClearTextIcon.getIntrinsicWidth()-20, mClearTextIcon.getIntrinsicHeight());

        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public void setClearIconVisible(boolean b) {//隐藏了清除按钮，在输入文本时才会显示
        mClearTextIcon.setVisible(b, false);
        Drawable[] compoundDrawables=getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0],compoundDrawables[1],b?mClearTextIcon:null,compoundDrawables[3]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x=(int)event.getX();
        if (mClearTextIcon.isVisible() && x > getWidth() - getPaddingRight() -mClearTextIcon.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {//如果触摸了叉号就将内容设置为空
                setText("");
            }
            return true;
        }
        return mOnTouchListener!=null&&mOnTouchListener.onTouch(v,event);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mOnTouchListener=l;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(v,hasFocus);
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mOnFocusChangeListener=l;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (isFocused()) {
            setClearIconVisible(getText().length()>0);
        }
    }
}
