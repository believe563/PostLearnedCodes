package com.feng.demo.linearprogress;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import util.ViewUtil;


public class ProgressView extends View implements ThemeManager.OnThemeChangedListener {

    protected int mStyleId;
    protected int mCurrentStyle = ThemeManager.THEME_UNDEFINED;

    private boolean mAutostart = false;
    private boolean mCircular = true;
    private int mProgressId;

    public static final int MODE_DETERMINATE = 0;
    public static final int MODE_INDETERMINATE = 1;
    public static final int MODE_BUFFER = 2;
    public static final int MODE_QUERY = 3;


    private Drawable mProgressDrawable;

    public ProgressView(Context context) {
        super(context);

        init(context, null, 0, 0);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        applyStyle(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode())
            mStyleId = ThemeManager.getStyleId(context, attrs, defStyleAttr, defStyleRes);
    }

    public void applyStyle(int resId) {
        ViewUtil.applyStyle(this, resId);
        applyStyle(getContext(), null, 0, resId);
    }

    private boolean needCreateProgress(boolean circular) {
        if (mProgressDrawable == null)
            return true;

        if (!circular)
            return !(mProgressDrawable instanceof LinearProgressDrawable);
        return false;
    }

    protected void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyleAttr, defStyleRes);

        int progressId = 0;
        int progressMode = -1;
        float progress = -1;
        float secondaryProgress = -1;

        for (int i = 0, count = a.getIndexCount(); i < count; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.ProgressView_pv_autostart)
                mAutostart = a.getBoolean(attr, false);
            else if (attr == R.styleable.ProgressView_pv_circular)
                mCircular = a.getBoolean(attr, true);
            else if (attr == R.styleable.ProgressView_pv_progressStyle)
                progressId = a.getResourceId(attr, 0);
            else if (attr == R.styleable.ProgressView_pv_progressMode)
                progressMode = a.getInteger(attr, 0);
            else if (attr == R.styleable.ProgressView_pv_progress)
                progress = a.getFloat(attr, 0);
            else if (attr == R.styleable.ProgressView_pv_secondaryProgress)
                secondaryProgress = a.getFloat(attr, 0);
        }

        a.recycle();

        boolean needStart = false;

//        if(needCreateProgress(mCircular)){
//            mProgressId = progressId;
//            if(mProgressId == 0)
//                mProgressId = mCircular ? R.style.Material_Drawable_CircularProgress : R.style.Material_Drawable_LinearProgress;

        needStart = mProgressDrawable != null && ((Animatable) mProgressDrawable).isRunning();
        mProgressDrawable = new LinearProgressDrawable.Builder(context, mProgressId).build();
        ViewUtil.setBackground(this, mProgressDrawable);
//        }
//        else if(mProgressId != progressId){
//            mProgressId = progressId;
        if (mProgressDrawable instanceof LinearProgressDrawable)
            ((LinearProgressDrawable) mProgressDrawable).applyStyle(context, mProgressId);


//        if(progressMode >= 0) {
//            if(mProgressDrawable instanceof CircularProgressDrawable)
//                ((CircularProgressDrawable) mProgressDrawable).setProgressMode(progressMode);
//            else
//                ((LinearProgressDrawable)mProgressDrawable).setProgressMode(progressMode);
//        }

//        if(progress >= 0)
//            setProgress(progress);

        // setSecondaryProgress(secondaryProgress);

        //        if(needStart)
        start();

    }
    @Override
    public void onThemeChanged(ThemeManager.OnThemeChangedEvent event) {
        int style = ThemeManager.getInstance().getCurrentStyle(mStyleId);
        if (mCurrentStyle != style) {
            mCurrentStyle = style;
            applyStyle(mCurrentStyle);
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (changedView != this)
            return;

        if (mAutostart) {
            if (visibility == GONE || visibility == INVISIBLE)
                stop();
            else
                start();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == View.VISIBLE && mAutostart)
            start();
        if (mStyleId != 0) {
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
            onThemeChanged(null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAutostart)
            stop();

        super.onDetachedFromWindow();
        if (mStyleId != 0)
            ThemeManager.getInstance().unregisterOnThemeChangedListener(this);
    }

    public int getProgressMode() {
        if (!mCircular)
            return ((LinearProgressDrawable) mProgressDrawable).getProgressMode();
        return 0;
    }

    /**
     * @return The current progress of this view in [0..1] range.
     */
    public float getProgress() {
        if (!mCircular)
            return ((LinearProgressDrawable) mProgressDrawable).getProgress();
        return 0f;
    }

    /**
     * @return The current secondary progress of this view in [0..1] range.
     */
    public float getSecondaryProgress() {
        if (!mCircular)
            return ((LinearProgressDrawable) mProgressDrawable).getSecondaryProgress();
        return 0f;
    }

    /**
     * Set the current progress of this view.
     *
     * @param percent The progress value in [0..1] range.
     */
    public void setProgress(float percent) {
        if (!mCircular)
            ((LinearProgressDrawable) mProgressDrawable).setProgress(percent);
    }

    /**
     * Set the current secondary progress of this view.
     *
     * @param percent The progress value in [0..1] range.
     */
    public void setSecondaryProgress(float percent) {
        if (!mCircular)
            ((LinearProgressDrawable) mProgressDrawable).setSecondaryProgress(percent);
    }

    /**
     * Start showing progress.
     */
    public void start() {
        if (mProgressDrawable != null)
            ((Animatable) mProgressDrawable).start();
    }

    /**
     * Stop showing progress.
     */
    public void stop() {
        if (mProgressDrawable != null)
            ((Animatable) mProgressDrawable).stop();
    }

}
