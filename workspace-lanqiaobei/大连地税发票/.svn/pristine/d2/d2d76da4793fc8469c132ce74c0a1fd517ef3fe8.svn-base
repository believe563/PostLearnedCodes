package com.innova.reward.aty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.ocrsearch.common.ZipUtils;
import com.innova.reward.R;
import com.innova.reward.data.Constant;
import com.innova.reward.util.CommonUtil;

@SuppressLint("HandlerLeak")
public class AtyGuide extends FinalActivity {

	private static final int TO_THE_END = 0;// 到达最后一张
	private static final int LEAVE_FROM_END = 1;// 离开最后一张

	private int curPos, offset;// 记录当前的位置,位移量
	private ImageView curDot;
	private Button open;
	private ViewPager pager;
	private List<View> guides = new ArrayList<View>();

	private int[] ids = { R.drawable.guide_one, R.drawable.guide_two, R.drawable.guide_three, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// 这一句是必须加载的
		boolean isFirstUse = CommonUtil.sp.getBoolean(Constant.FIRST_USE, true);

		if (isFirstUse) {// 第一次
			setContentView(R.layout.lyt_welcome);

			zipData();

			for (int i = 0, len = ids.length; i < len; i++) {
				ImageView iv = new ImageView(this);
				iv.setImageResource(ids[i]);
				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);
				iv.setLayoutParams(params);
				iv.setScaleType(ScaleType.FIT_XY);
				guides.add(iv);
			}
			curDot = (ImageView) findViewById(R.id.cur_dot);
			open = (Button) findViewById(R.id.open);
			open.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					startActivity(new Intent(AtyGuide.this, AtyLogin.class));
					finish();
				}
			});
			curDot.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
				public boolean onPreDraw() {
					offset = curDot.getWidth();
					return true;
				}
			});
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == TO_THE_END) {
						open.setVisibility(View.VISIBLE);
					} else if (msg.what == LEAVE_FROM_END) {
						open.setVisibility(View.GONE);
					}
				}
			};
			GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
			pager = (ViewPager) findViewById(R.id.contentPager);
			pager.setAdapter(adapter);
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				public void onPageSelected(int arg0) {
					moveCursorTo(arg0);
					if (arg0 == ids.length - 1) {// 到最后一张了
						handler.sendEmptyMessageDelayed(TO_THE_END, 500);
					} else if (curPos == ids.length - 1) {
						handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
					}
					curPos = arg0;
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				public void onPageScrollStateChanged(int arg0) {
				}
			});
		} else {
			finish();
			startActivity(new Intent(this, AtyLogin.class));
		}
	}

	/**
	 * 是否已经解压过
	 * 
	 * @return true:已经解压过; false:没有解压过
	 */
	private boolean getStatusOfZiped() {
		return new File(CommonUtil.TESSBASE_PATH).exists();
	}

	/**
	 * 移动指针到相邻的位置
	 * 
	 * @param position
	 *            指针的索引值
	 * */
	private void moveCursorTo(int position) {
		TranslateAnimation anim = new TranslateAnimation(offset * curPos, offset * position, 0, 0);
		anim.setDuration(300);
		anim.setFillAfter(true);
		curDot.startAnimation(anim);
	}

	/**
	 * 欢迎界面的adpater
	 * 
	 * @author guo
	 * 
	 */
	private class GuidePagerAdapter extends PagerAdapter {

		private List<View> views;

		public GuidePagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	private void zipData() {
		CommonUtil.log("进入");
		if (CommonUtil.getExternalStorageStatus() && !getStatusOfZiped()) {
			CommonUtil.log("解压");
			new Thread() {
				public void run() {
					try {
						InputStream in = getResources().getAssets().open("tessdata.zip");
						File zip = new File(CommonUtil.TESSBASE_PATH + "/tessdata.zip");
						CommonUtil.log(zip.getAbsolutePath());
						CommonUtil.log(CommonUtil.TESSBASE_PATH);
						OutputStream out = new FileOutputStream(zip);
						byte[] temp = new byte[1024];
						int size = -1;
						while ((size = in.read(temp)) != -1) {
							out.write(temp, 0, size);
						}
						out.flush();
						out.close();
						in.close();
						ZipUtils.upZipFile(zip, CommonUtil.TESSBASE_PATH);
						zip.deleteOnExit();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}