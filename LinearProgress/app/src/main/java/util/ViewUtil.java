package util;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.concurrent.atomic.AtomicInteger;

import com.feng.demo.linearprogress.R;

public class ViewUtil {
	
	public static final long FRAME_DURATION = 1000 / 60;

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @SuppressLint("NewApi")
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue))
                    return result;
            }
        }
        else
            return View.generateViewId();
    }
    
    public static boolean hasState(int[] states, int state){
		if(states == null)
			return false;

        for (int state1 : states)
            if (state1 == state)
                return true;
		
		return false;
	}

    public static void setBackground(View v, Drawable drawable){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            v.setBackground(drawable);
        else
            v.setBackgroundDrawable(drawable);
    }

    /**
     * Apply any View style attributes to a view.
     * @param v The view is applied.
     * @param resId The style resourceId.
     */
    public static void applyStyle(View v, int resId){
        applyStyle(v, null, 0, resId);
    }

    /**
     * Apply any View style attributes to a view.
     * @param v The view is applied.
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public static void applyStyle(View v, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        TypedArray a = v.getContext().obtainStyledAttributes(attrs, R.styleable.View, defStyleAttr, defStyleRes);

        int leftPadding = -1;
        int topPadding = -1;
        int rightPadding = -1;
        int bottomPadding = -1;
        int startPadding = Integer.MIN_VALUE;
        int endPadding = Integer.MIN_VALUE;
        int padding = -1;

        boolean startPaddingDefined = false;
        boolean endPaddingDefined = false;
        boolean leftPaddingDefined = false;
        boolean rightPaddingDefined = false;

        if (padding >= 0)
            v.setPadding(padding, padding, padding, padding);
        else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            if(startPaddingDefined)
                leftPadding = startPadding;
            if(endPaddingDefined)
                rightPadding = endPadding;

            v.setPadding(leftPadding >= 0 ? leftPadding : v.getPaddingLeft(),
                    topPadding >= 0 ? topPadding : v.getPaddingTop(),
                    rightPadding >= 0 ? rightPadding : v.getPaddingRight(),
                    bottomPadding >= 0 ? bottomPadding : v.getPaddingBottom());
        }
        else{
            if(leftPaddingDefined || rightPaddingDefined)
                v.setPadding(leftPaddingDefined ? leftPadding : v.getPaddingLeft(),
                        topPadding >= 0 ? topPadding : v.getPaddingTop(),
                        rightPaddingDefined ? rightPadding : v.getPaddingRight(),
                        bottomPadding >= 0 ? bottomPadding : v.getPaddingBottom());

            if(startPaddingDefined || endPaddingDefined)
                v.setPaddingRelative(startPaddingDefined ? startPadding : v.getPaddingStart(),
                        topPadding >= 0 ? topPadding : v.getPaddingTop(),
                        endPaddingDefined ? endPadding : v.getPaddingEnd(),
                        bottomPadding >= 0 ? bottomPadding : v.getPaddingBottom());
        }

        a.recycle();

        if(v instanceof TextView)
            applyStyle((TextView)v, attrs, defStyleAttr, defStyleRes);
    }

//    public static void applyFont(TextView v, AttributeSet attrs, int defStyleAttr, int defStyleRes){
//        TypedArray a = v.getContext().obtainStyledAttributes(attrs, new int[]{R.attr.tv_fontFamily}, defStyleAttr, defStyleRes);
//        String fontFamily = a.getString(0);
//
//        if(fontFamily != null){
//            Typeface typeface = TypefaceUtil.load(v.getContext(), fontFamily, 0);
//            v.setTypeface(typeface);
//        }
//
//        a.recycle();
//    }

    public static void applyTextAppearance(TextView v, int resId){
        if(resId == 0)
            return;

        String fontFamily = null;
        int typefaceIndex = -1;
        int styleIndex = -1;
        int shadowColor = 0;
        float dx = 0, dy = 0, r = 0;

//        TypedArray appearance = v.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
//        if (appearance != null) {
//            int n = appearance.getIndexCount();
//            for (int i = 0; i < n; i++) {
//                int attr = appearance.getIndex(i);
//
//                if (attr == R.styleable.TextAppearance_android_textColorHighlight) {
//                    v.setHighlightColor(appearance.getColor(attr, 0));
//
//                } else if (attr == R.styleable.TextAppearance_android_textColor) {
//                    v.setTextColor(appearance.getColorStateList(attr));
//
//                } else if (attr == R.styleable.TextAppearance_android_textColorHint) {
//                    v.setHintTextColor(appearance.getColorStateList(attr));
//
//                } else if (attr == R.styleable.TextAppearance_android_textColorLink) {
//                    v.setLinkTextColor(appearance.getColorStateList(attr));
//
//                } else if (attr == R.styleable.TextAppearance_android_textSize) {
//                    v.setTextSize(TypedValue.COMPLEX_UNIT_PX, appearance.getDimensionPixelSize(attr, 0));
//
//                } else if (attr == R.styleable.TextAppearance_android_typeface) {
//                    typefaceIndex = appearance.getInt(attr, -1);
//
//                } else if (attr == R.styleable.TextAppearance_android_fontFamily) {
//                    fontFamily = appearance.getString(attr);
//
//                } else if (attr == R.styleable.TextAppearance_tv_fontFamily) {
//                    fontFamily = appearance.getString(attr);
//
//                } else if (attr == R.styleable.TextAppearance_android_textStyle) {
//                    styleIndex = appearance.getInt(attr, -1);
//
//                } else if (attr == R.styleable.TextAppearance_android_textAllCaps) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//                        v.setAllCaps(appearance.getBoolean(attr, false));
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowColor) {
//                    shadowColor = appearance.getInt(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowDx) {
//                    dx = appearance.getFloat(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowDy) {
//                    dy = appearance.getFloat(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowRadius) {
//                    r = appearance.getFloat(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_elegantTextHeight) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                        v.setElegantTextHeight(appearance.getBoolean(attr, false));
//
//                } else if (attr == R.styleable.TextAppearance_android_letterSpacing) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                        v.setLetterSpacing(appearance.getFloat(attr, 0));
//
//                } else if (attr == R.styleable.TextAppearance_android_fontFeatureSettings) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                        v.setFontFeatureSettings(appearance.getString(attr));
//
//                }
//            }
//
//            appearance.recycle();
//        }

        if (shadowColor != 0)
            v.setShadowLayer(r, dx, dy, shadowColor);

        Typeface tf = null;
//        if (fontFamily != null) {
//            tf = TypefaceUtil.load(v.getContext(), fontFamily, styleIndex);
//            if (tf != null)
//                v.setTypeface(tf);
//        }
        if(tf != null) {
            switch (typefaceIndex) {
                case 1:
                    tf = Typeface.SANS_SERIF;
                    break;
                case 2:
                    tf = Typeface.SERIF;
                    break;
                case 3:
                    tf = Typeface.MONOSPACE;
                    break;
            }
            v.setTypeface(tf, styleIndex);
        }
    }

    /**
     * Apply any TextView style attributes to a view.
     * @param v
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    private static void applyStyle(TextView v, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        String fontFamily = null;
        int typefaceIndex = -1;
        int styleIndex = -1;
        int shadowColor = 0;
        float dx = 0, dy = 0, r = 0;

        Drawable drawableLeft = null, drawableTop = null, drawableRight = null,
                drawableBottom = null, drawableStart = null, drawableEnd = null;
        boolean drawableDefined = false;
        boolean drawableRelativeDefined = false;

        /*
         * Look the appearance up without checking first if it exists because
         * almost every TextView has one and it greatly simplifies the logic
         * to be able to parse the appearance first and then let specific tags
         * for this View override it.
         */
//        TypedArray a = v.getContext().obtainStyledAttributes(attrs, R.styleable.TextViewAppearance, defStyleAttr, defStyleRes);
//        TypedArray appearance = null;
//        int ap = a.getResourceId(R.styleable.TextViewAppearance_android_textAppearance, 0);
//        a.recycle();
//
//        if (ap != 0)
//            appearance = v.getContext().obtainStyledAttributes(ap, R.styleable.TextAppearance);
//
//        if (appearance != null) {
//            int n = appearance.getIndexCount();
//            for (int i = 0; i < n; i++) {
//                int attr = appearance.getIndex(i);
//
//                if (attr == R.styleable.TextAppearance_android_textColorHighlight) {
//                    v.setHighlightColor(appearance.getColor(attr, 0));
//
//                } else if (attr == R.styleable.TextAppearance_android_textColor) {
//                    v.setTextColor(appearance.getColorStateList(attr));
//
//                } else if (attr == R.styleable.TextAppearance_android_textColorHint) {
//                    v.setHintTextColor(appearance.getColorStateList(attr));
//
//                } else if (attr == R.styleable.TextAppearance_android_textColorLink) {
//                    v.setLinkTextColor(appearance.getColorStateList(attr));
//
//                } else if (attr == R.styleable.TextAppearance_android_textSize) {
//                    v.setTextSize(TypedValue.COMPLEX_UNIT_PX, appearance.getDimensionPixelSize(attr, 0));
//
//                } else if (attr == R.styleable.TextAppearance_android_typeface) {
//                    typefaceIndex = appearance.getInt(attr, -1);
//
//                } else if (attr == R.styleable.TextAppearance_android_fontFamily) {
//                    fontFamily = appearance.getString(attr);
//
//                } else if (attr == R.styleable.TextAppearance_tv_fontFamily) {
//                    fontFamily = appearance.getString(attr);
//
//                } else if (attr == R.styleable.TextAppearance_android_textStyle) {
//                    styleIndex = appearance.getInt(attr, -1);
//
//                } else if (attr == R.styleable.TextAppearance_android_textAllCaps) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//                        v.setAllCaps(appearance.getBoolean(attr, false));
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowColor) {
//                    shadowColor = appearance.getInt(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowDx) {
//                    dx = appearance.getFloat(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowDy) {
//                    dy = appearance.getFloat(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_shadowRadius) {
//                    r = appearance.getFloat(attr, 0);
//
//                } else if (attr == R.styleable.TextAppearance_android_elegantTextHeight) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                        v.setElegantTextHeight(appearance.getBoolean(attr, false));
//
//                } else if (attr == R.styleable.TextAppearance_android_letterSpacing) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                        v.setLetterSpacing(appearance.getFloat(attr, 0));
//
//                } else if (attr == R.styleable.TextAppearance_android_fontFeatureSettings) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                        v.setFontFeatureSettings(appearance.getString(attr));
//
//                }
//            }
//
//            appearance.recycle();
//        }
//
//        a = v.getContext().obtainStyledAttributes(attrs, R.styleable.TextView, defStyleAttr, defStyleRes);
//
//        int n = a.getIndexCount();
//        for (int i = 0; i < n; i++) {
//            int attr = a.getIndex(i);
//
//            if (attr == R.styleable.TextView_android_drawableLeft) {
//                drawableLeft = a.getDrawable(attr);
//                drawableDefined = true;
//
//            } else if (attr == R.styleable.TextView_android_drawableTop) {
//                drawableTop = a.getDrawable(attr);
//                drawableDefined = true;
//
//            } else if (attr == R.styleable.TextView_android_drawableRight) {
//                drawableRight = a.getDrawable(attr);
//                drawableDefined = true;
//
//            } else if (attr == R.styleable.TextView_android_drawableBottom) {
//                drawableBottom = a.getDrawable(attr);
//                drawableDefined = true;
//
//            } else if (attr == R.styleable.TextView_android_drawableStart) {
//                drawableStart = a.getDrawable(attr);
//                drawableRelativeDefined = true;
//
//            } else if (attr == R.styleable.TextView_android_drawableEnd) {
//                drawableEnd = a.getDrawable(attr);
//                drawableRelativeDefined = true;
//
//            } else if (attr == R.styleable.TextView_android_drawablePadding) {
//                v.setCompoundDrawablePadding(a.getDimensionPixelSize(attr, 0));
//
//            } else if (attr == R.styleable.TextView_android_maxLines) {
//                v.setMaxLines(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_maxHeight) {
//                v.setMaxHeight(a.getDimensionPixelSize(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_lines) {
//                v.setLines(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_height) {
//                v.setHeight(a.getDimensionPixelSize(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_minLines) {
//                v.setMinLines(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_minHeight) {
//                v.setMinHeight(a.getDimensionPixelSize(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_maxEms) {
//                v.setMaxEms(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_maxWidth) {
//                v.setMaxWidth(a.getDimensionPixelSize(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_ems) {
//                v.setEms(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_width) {
//                v.setWidth(a.getDimensionPixelSize(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_minEms) {
//                v.setMinEms(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_minWidth) {
//                v.setMinWidth(a.getDimensionPixelSize(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_gravity) {
//                v.setGravity(a.getInt(attr, -1));
//
//            } else if (attr == R.styleable.TextView_android_scrollHorizontally) {
//                v.setHorizontallyScrolling(a.getBoolean(attr, false));
//
//            } else if (attr == R.styleable.TextView_android_includeFontPadding) {
//                v.setIncludeFontPadding(a.getBoolean(attr, true));
//
//            } else if (attr == R.styleable.TextView_android_cursorVisible) {
//                v.setCursorVisible(a.getBoolean(attr, true));
//
//            } else if (attr == R.styleable.TextView_android_textScaleX) {
//                v.setTextScaleX(a.getFloat(attr, 1.0f));
//
//            } else if (attr == R.styleable.TextView_android_shadowColor) {
//                shadowColor = a.getInt(attr, 0);
//
//            } else if (attr == R.styleable.TextView_android_shadowDx) {
//                dx = a.getFloat(attr, 0);
//
//            } else if (attr == R.styleable.TextView_android_shadowDy) {
//                dy = a.getFloat(attr, 0);
//
//            } else if (attr == R.styleable.TextView_android_shadowRadius) {
//                r = a.getFloat(attr, 0);
//
//            } else if (attr == R.styleable.TextView_android_textColorHighlight) {
//                v.setHighlightColor(a.getColor(attr, 0));
//
//            } else if (attr == R.styleable.TextView_android_textColor) {
//                v.setTextColor(a.getColorStateList(attr));
//
//            } else if (attr == R.styleable.TextView_android_textColorHint) {
//                v.setHintTextColor(a.getColorStateList(attr));
//
//            } else if (attr == R.styleable.TextView_android_textColorLink) {
//                v.setLinkTextColor(a.getColorStateList(attr));
//
//            } else if (attr == R.styleable.TextView_android_textSize) {
//                v.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, 0));
//
//            } else if (attr == R.styleable.TextView_android_typeface) {
//                typefaceIndex = a.getInt(attr, -1);
//
//            } else if (attr == R.styleable.TextView_android_textStyle) {
//                styleIndex = a.getInt(attr, -1);
//
//            } else if (attr == R.styleable.TextView_android_fontFamily) {
//                fontFamily = a.getString(attr);
//
//            } else if (attr == R.styleable.TextView_tv_fontFamily) {
//                fontFamily = a.getString(attr);
//
//            } else if (attr == R.styleable.TextView_android_textAllCaps) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//                    v.setAllCaps(a.getBoolean(attr, false));
//
//            } else if (attr == R.styleable.TextView_android_elegantTextHeight) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                    v.setElegantTextHeight(a.getBoolean(attr, false));
//
//            } else if (attr == R.styleable.TextView_android_letterSpacing) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                    v.setLetterSpacing(a.getFloat(attr, 0));
//
//            } else if (attr == R.styleable.TextView_android_fontFeatureSettings) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//                    v.setFontFeatureSettings(a.getString(attr));
//
//            }
//        }
//        a.recycle();

        if (shadowColor != 0)
            v.setShadowLayer(r, dx, dy, shadowColor);

        if(drawableDefined) {
            Drawable[] drawables = v.getCompoundDrawables();
            if (drawableStart != null)
                drawables[0] = drawableStart;
            else if (drawableLeft != null)
                drawables[0] = drawableLeft;
            if (drawableTop != null)
                drawables[1] = drawableTop;
            if (drawableEnd != null)
                drawables[2] = drawableEnd;
            else if (drawableRight != null)
                drawables[2] = drawableRight;
            if (drawableBottom != null)
                drawables[3] = drawableBottom;
            v.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        }

        if(drawableRelativeDefined && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            Drawable[] drawables = v.getCompoundDrawablesRelative();
            if (drawableStart != null)
                drawables[0] = drawableStart;
            if (drawableEnd != null)
                drawables[2] = drawableEnd;
            v.setCompoundDrawablesRelativeWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        }

        Typeface tf = null;
//        if (fontFamily != null) {
//            tf = TypefaceUtil.load(v.getContext(), fontFamily, styleIndex);
//            if (tf != null)
//                v.setTypeface(tf);
//        }
        if(tf != null) {
            switch (typefaceIndex) {
                case 1:
                    tf = Typeface.SANS_SERIF;
                    break;
                case 2:
                    tf = Typeface.SERIF;
                    break;
                case 3:
                    tf = Typeface.MONOSPACE;
                    break;
            }
            v.setTypeface(tf, styleIndex);
        }

        if(v instanceof AutoCompleteTextView)
            applyStyle((AutoCompleteTextView)v, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Apply any AutoCompleteTextView style attributes to a view.
     * @param v
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
//    private static void applyStyle(AutoCompleteTextView v,  AttributeSet attrs, int defStyleAttr, int defStyleRes){
//        TypedArray a = v.getContext().obtainStyledAttributes(attrs, R.styleable.AutoCompleteTextView, defStyleAttr, defStyleRes);
//
//        int n = a.getIndexCount();
//        for (int i = 0; i < n; i++) {
//            int attr = a.getIndex(i);
//
//            if(attr == R.styleable.AutoCompleteTextView_android_completionHint)
//                v.setCompletionHint(a.getString(attr));
//            else if(attr == R.styleable.AutoCompleteTextView_android_completionThreshold)
//                v.setThreshold(a.getInteger(attr, 0));
//            else if(attr == R.styleable.AutoCompleteTextView_android_dropDownAnchor)
//                v.setDropDownAnchor(a.getResourceId(attr, 0));
//            else if(attr == R.styleable.AutoCompleteTextView_android_dropDownHeight)
//                v.setDropDownHeight(a.getLayoutDimension(attr, ViewGroup.LayoutParams.WRAP_CONTENT));
//            else if(attr == R.styleable.AutoCompleteTextView_android_dropDownWidth)
//                v.setDropDownWidth(a.getLayoutDimension(attr, ViewGroup.LayoutParams.WRAP_CONTENT));
//            else if(attr == R.styleable.AutoCompleteTextView_android_dropDownHorizontalOffset)
//                v.setDropDownHorizontalOffset(a.getDimensionPixelSize(attr, 0));
//            else if(attr == R.styleable.AutoCompleteTextView_android_dropDownVerticalOffset)
//                v.setDropDownVerticalOffset(a.getDimensionPixelSize(attr, 0));
//            else if(attr == R.styleable.AutoCompleteTextView_android_popupBackground)
//                v.setDropDownBackgroundDrawable(a.getDrawable(attr));
//        }
//        a.recycle();
//    }

}
