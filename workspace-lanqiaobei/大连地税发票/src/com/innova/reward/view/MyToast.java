package com.innova.reward.view;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.innova.reward.R;
import com.innova.reward.util.MyAppli;

/**自定义Toast
 * @author guo
 *
 */
public class MyToast {
	/**
	 * 
	 * @param c
	 *            上下文对象
	 * @param msg
	 *            要显示的信息
	 * @param time
	 *            时间参数 若是“s”表示短时间显示 若是“l”（小写L）表示长时间显示
	 */
	public static void toast(String msg, int time) {
		time = Toast.LENGTH_SHORT;
		if (time == 1) {
			time = Toast.LENGTH_LONG;
		}

		Toast toast = Toast.makeText(MyAppli.getContext(), null, time);
		LinearLayout layout = (LinearLayout) toast.getView();
		layout.setBackgroundResource(R.drawable.bg_toast);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.CENTER_VERTICAL);
		TextView tv = new TextView(MyAppli.getContext());
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER_VERTICAL);
		//Color.parseColor().....里面一定要是8为
		tv.setTextColor(Color.parseColor("#FFFFFFFF"));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		tv.setPadding(0, 0, 0, 0);
		tv.setText(msg);
		layout.addView(tv);
		toast.show();
	}
	
	public static void toast(int resId,int time){
		toast(MyAppli.getContext().getString(resId), time);
	}
}
