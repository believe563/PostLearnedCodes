package com.fengjianghui.com.viewpagerpreview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by lenovo on 2017/6/24.
 * 自定义的图片放大缩小的imageview
 */

public class ZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private boolean mOnce = false;//图片加载完成后初始化的操作只需要进行一次

    /**
     * 初始化时缩放的值
     */
    private float mInitScale;
    /**
     * 双击放大时达到的值
     */
    private float mMidScale;
    /**
     * 放大的极限值
     */
    private float mMaxScale;
    private Matrix mScaleMatrix;//矩阵
    /**
     * 捕获用户多指触控时缩放的比例
     */
    private ScaleGestureDetector mScaleGestureDetector;//多点触控的类

//    ——自由移动的类——————————————————————————————————————————————————————————

    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPointerCount;
    private float mLastX;//最后一次多指的中心点的位置
    private float mLastY;
    private float mTouchSlop;//判断是否构成移动的比较值
    private boolean isCanTrag;

    /**
     * 是否进行边界检测
     *
     * @param context
     */
    private boolean isCheckLeftAndRight = true;
    private boolean isCheckTopAndBottom = true;

    //双击放大与缩小------------------------------------------
    private GestureDetector gestureDetector;
    private boolean isAutoScale;


    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr 在三个参数的构造方法中执行初始化方法
     *                     （一个参数和两个参数的构造方法中已经调用了三个参数的构造方法）
     */
    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);//为自定义的Imageview设置scaleType属性
        //1--在屏幕上显示图片时，首先要将放大或缩小显示的图片显示成屏幕大小
        //需要捕获到图片加载完成事件

        mScaleGestureDetector = new ScaleGestureDetector(context, this);//第二个参数是OnScaleGestureListener的监听，会实现三个它的方法
        setOnTouchListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//得到做比较的值

        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isAutoScale) {
                    return true;
                }

                //如果当前缩放比例小于middleScale，就放大到middleScale，其它情况都缩小为initScale
                float x=e.getX();
                float y=e.getY();//当前点击的x,y坐标
                if (getScale() < mMidScale) {
//                    mScaleMatrix.postScale(mMidScale / getScale(), mMidScale / getScale(), x, y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y),16);
                    isAutoScale=true;
                } else {
//                    mScaleMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y),16);
                    isAutoScale=true;
                }
                return true;
            }
        });
    }

    /**
     * 自动放大与缩小
     */
    private class AutoScaleRunnable implements Runnable {

        private float mTargetScale;//目标缩放值
        private float x;//缩放的中心点
        private float y;

        private final float BIGGER=1.07f;//缩放的梯度值
        private final float SMALLER=0.93f;//缩放的梯度值

        private float tmpScale;//临时变量

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale=mTargetScale;
            this.x=x;
            this.y=y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale=SMALLER;
            }
        }
        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale=getScale();//当前缩放值
            if ((tmpScale > 1.0f && currentScale < mTargetScale) || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                //设置为目标值
                float scale=mTargetScale/currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale=false;
            }
        }
    }



    /**
     * 在ZoomImageview中注册了OnGlobalLayoutListener的接口，需要在这里移除这个接口
     * 这个方法表示当view attached到window上时调用该方法
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);//调用onGlobalLayout
    }

    /**
     * 这个方法表示当view从window上消除时调用该方法
     *
     * @SuppressWarnings("deprecation")用来延展removeOnGlobalLayoutListener使其可以兼容低版本的API 如果是高版本的话可能需要修改minSDK
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);//移除onGlobalLayout
    }

    /**
     * 图片加载完成事件
     * 全局的布局完成之后会调用这个方法
     * <p>
     * 主要完成的工作：获取ImageView加载完成的图片，获取它的宽高，进行相应的缩放
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            //获取控件的宽和高，一般情况下这个控件的宽和高就是屏幕的宽和高
            int width = getWidth();
            int height = getHeight();

            //得到图片，以及图片的宽和高
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            //获取图片的宽度和高度
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;//设置一个默认缩放值
            //将图片的宽高和控件的宽高做对比，如果比控件的大，就要缩小，如果比控件的小，就要放大
            if (dw > width && dh < height) {//图片宽度>控件宽度&&图片高度<控件高度
                scale = width * 1.0f / dw;//先将宽度缩小，要先将width*1.0f防止整除，出错
            }
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            if (dw > width && dh > height) {//两个宽度都大于控件宽度--只要取两者的最小值，也就是按放大倍数大的那边缩小
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            if (dw < width && dh < height) {//两个宽度都小于控件宽度，只要取两者的最大值，即缩小倍数小的那边来放大
                //这句和上个if中的语句相同，可以合并
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            /**
             * 得到了初始化时缩放的比例
             */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            /**
             * 将图片移动至当前控件的中心
             */
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            /**
             * 将缩放和移动值应用到控件上
             */
            mScaleMatrix.postTranslate(dx, dy);
            //后两个参数是缩放的中心点，设置为控件中心
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    /**
     * @return 当前图片的缩放值
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 手势的捕获，但是必须要首先传入事件，才能进行捕获
     * 所以要先实现onTouchListener
     *
     * @param detector 可以拿到缩放比例
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();//手势滑动的缩放值
        /**
         * 要拿到当前缩放的比例
         */
        float scale = getScale();//当前的缩放值
        if (getDrawable() == null) {
            return true;
        }

        //缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {//如果当前没有放大到最大 也没有缩小到最小

            if (scale * scaleFactor < mInitScale) {//如果当前缩的太小了，就要设置缩放值为最小值
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }

            //进行缩放(以控件中心点进行缩放，不能随意选择中心点进行详细的查看)
//            mScaleMatrix.postScale(scaleFactor,scaleFactor,getWidth()/2,getHeight()/2);
            //随意选择中心点进行缩放，但是选择任意点缩小后，图片不能仍然放在中心位置
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    /**
     * @return 图片放大缩小以后的宽和高，以及left、right、top、bottom
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;//拿到当前的matrix，就可以获取当前的宽和高
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);//这样就得到了一个缩小或放大以后的图片的宽和高
        }
        return rectF;
    }

    /**
     * 在缩放的时候不断检测图片是不是在控件的中心点
     * 在缩放的时候进行边界控制以及位置控制
     * 首先要拿到图片放大缩小以后的边界位置和宽度、高度（在getMatrixRectF方法中）
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;//插值（左边移出来一点，缩回去，右边移出来一点，缩回去）
        int width = getWidth();
        int height = getHeight();
        //缩放时进行边界检测，防止出现白边
        if (rectF.width() >= width) {//当前是放大状态时
            if (rectF.left > 0) {//偏右,左边有白边
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {//偏左，右边有白边
                deltaX = width-rectF.right;
            }
        }
        if (rectF.height() >= height) {//当前是放大状态时
            if (rectF.top > 0) {//偏下，上面有白边
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {//偏上
                deltaY = height-rectF.bottom;
            }
        }

        //如果宽度或者高度小于控件的宽或者高，则让其居中
        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2;
        }
        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2;//要让height/2f,f不能丢
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;//这里必须要return true
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //如果双击了 就不能有放大缩小和移动的手势被触发
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }

        mScaleGestureDetector.onTouchEvent(event);//这样detector就可以拿到手指触控的坐标

        //多点触控的自由移动--------------------------------------------------
        float x = 0;
        float y = 0;//用于存储多点触控的中心点
        //拿到多点触控的数量
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {//遍历每个中心点的位置
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;//这样得到中心点的位置

        if (mLastPointerCount != pointerCount) {//手指数量改变
            isCanTrag = false;//重新进行判断
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;//改变上一次中心点的位置和手指数量
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                //判断是否足以是一个move的行为
                if (!isCanTrag) {
                    isCanTrag = isMoveAction(dx, dy);
                }
                if (isCanTrag) {
                    //完成图片的移动
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {//左右移动和上下移动在图片小于控件的时候不发生
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderAndCenterWhenScale();

                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount=0;
                break;
        }
        return true;//要写成true
    }

    /**
     * 手指移动时进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 判断是否是move，需要拿dy dx计算斜对角线的距离，与标准值进行比较
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }
}
