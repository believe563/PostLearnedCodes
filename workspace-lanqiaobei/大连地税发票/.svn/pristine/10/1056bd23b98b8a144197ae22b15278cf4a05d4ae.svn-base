package com.innova.reward.frag;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ocrsearch.OcrFinderView;
import com.example.ocrsearch.PlanarYUVLuminanceSource;
import com.example.ocrsearch.camera.CameraManager;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.innova.reward.R;
import com.innova.reward.aty.AtyMain;
import com.innova.reward.data.Constant;
import com.innova.reward.util.CommonUtil;
import com.innova.reward.util.DBUtil;
import com.innova.reward.util.MyAppli;
import com.innova.reward.view.MyToast;

public class InFrag extends BaseFrag implements OnClickListener {
	private Button mBtnClear;
	private Button mBtnSave;
	private TextView mTvDistinguish;
	private TextView mTvManual;
	private EditText mEtContinuous;
	private EditText mEtFpdm;
	private EditText mEtFphm;
	private LinearLayout mLlManual;
	private FrameLayout mFlDistinguish;

	private TessBaseAPI mBaseApi;
	private SurfaceHolder mSurfaceHolder;
	private CameraManager mCameraManager;
	private OcrFinderView mOrcFindView;
	private int mScreenWidth, mScreenHeight, mDistinguishTime, mTimeOfSure;
	private ImageView iv;
	private View mInView;
	private SurfaceView mSurfaceView;
	private String mEnter, mFpdm, mFphm;
	private boolean mHasSurface, mOnceInitOrc, mIsExternalStorageStatusOk;// ocr只初始化一次
	private Map<String, Integer> mDistinguishedMap = new HashMap<String, Integer>();

	private final static int MOST_DISTINGUISH_TIME = 6;
	public static boolean mIsOpenDistinguish;// 用来判断是否在Infrag界面，以判断是否开启识别

	public View onCreateView(LayoutInflater inf, ViewGroup vp, Bundle bundle) {
		mInView = inf.inflate(R.layout.lyt_in, null);
		if (CommonUtil.getExternalStorageStatus()) {
			mIsExternalStorageStatusOk = true;
		} else {
			mIsExternalStorageStatusOk = false;
		}
		initViews(mInView);
		initEvents();

		return mInView;
	}

	@SuppressWarnings("deprecation")
	public void onResume() {
		super.onResume();
		// 由Fragment的声明周期决定
		if (mIsExternalStorageStatusOk) {
			if (!mOnceInitOrc) {
				mCameraManager = new CameraManager(getActivity().getApplicationContext(), this);//
				mOrcFindView = (OcrFinderView) mInView.findViewById(R.id.ocrfv_find_view);
				mOrcFindView.setCameraManager(mCameraManager);
				mOnceInitOrc = true;
			}
			if (mHasSurface) {
				initCamera(mSurfaceHolder);
			} else {
				mSurfaceHolder.addCallback(mSurcallback);
				mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			}
		}
	}

	public static final int DECODE = 0, DECOCE_FAIL = 1;
	@SuppressLint("HandlerLeak")
	private Handler decodeHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DECODE:
				break;
			case DECOCE_FAIL:
				mCameraManager.requestPreviewFrame(decodeHandle, DECODE, new PreviewCallback());
			}
		};
	};

	public String decodeBitmapValue(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		mBaseApi.init(CommonUtil.TESSBASE_PATH, Constant.DEFAULT_LANGUAGE);
		bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
		mBaseApi.setImage(bitmap);
		String value = mBaseApi.getUTF8Text();
		mBaseApi.clear();
		mBaseApi.end();
		return value;
	}

	private class PreviewCallback implements Camera.PreviewCallback {

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			if (mDistinguishTime == MOST_DISTINGUISH_TIME) {
				final String text = getMostTimeText();
				mDistinguishTime++;
				Builder builder = new Builder(getActivity());
				AlertDialog dialog = builder.setTitle(R.string.please_check)
						.setPositiveButton(R.string.distinguish_correct, new DialogInterface.OnClickListener() {

							@SuppressWarnings("deprecation")
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
								mDistinguishTime = 0;
								mDistinguishedMap.clear();
								mTimeOfSure++;
								if (text.length() == 12) {
									mFpdm = text;
								} else if (text.length() == 8) {
									mFphm = text;
								}
								if (mTimeOfSure == 2) {
									handleTouchOnManualActionUp();
									mTvManual.setBackgroundDrawable(getResources().getDrawable(
											R.drawable.x_in_method_manual_selected));
									mEtFpdm.setText(mFpdm);
									mEtFphm.setText(mFphm);
								}
							}
						}).setNegativeButton(R.string.distinguish_again, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
								mDistinguishTime = 0;
								mDistinguishedMap.clear();
								cameraDistinguish();
							}
						}).setMessage(text).create();
				dialog.show();
			} else if (data != null && mIsOpenDistinguish && mDistinguishTime < MOST_DISTINGUISH_TIME) {
				Parameters parameters = camera.getParameters();
				int imageFormat = parameters.getPreviewFormat();
				if (imageFormat == ImageFormat.NV21) {
					Bitmap image = null;
					int w = parameters.getPreviewSize().width;
					int h = parameters.getPreviewSize().height;
					Rect rect = new Rect(0, 0, w, h);
					YuvImage img = new YuvImage(data, ImageFormat.NV21, w, h, null);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					if (img.compressToJpeg(rect, 100, baos)) {
						image = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
						image = cutBitmap(image, mCameraManager.getFramingRectInPreview(), Bitmap.Config.ARGB_8888);
						Matrix matrix = new Matrix();
						matrix.postRotate(90);
						image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);

						if (image != null) {
							iv.setImageBitmap(image);
							mEnter = removeNotNum(decodeBitmapValue(image));
							if (mEnter.length() == 8 || mEnter.length() == 12) {
								mDistinguishTime++;
								MyToast.toast(mEnter + "," + mDistinguishTime, 0);
								if (mDistinguishedMap.containsKey(mEnter)) {
									CommonUtil.log(mEnter + "," + mDistinguishedMap);
									mDistinguishedMap.put(mEnter, mDistinguishedMap.get(mEnter) + 1);
								} else {
									mDistinguishedMap.put(mEnter, 1);
								}
							}
						}

					}
					cameraDistinguish();
				}
			}
		}

		private String getMostTimeText() {
			int mostTime = -1;
			String res = null;
			for (Map.Entry<String, Integer> m : mDistinguishedMap.entrySet()) {
				if (mostTime < m.getValue()) {
					mostTime = m.getValue();
					res = m.getKey();
				}
			}
			return res;
		}

		private void cameraDistinguish() {
			decodeHandle.removeMessages(DECOCE_FAIL);
			Message msg = Message.obtain(decodeHandle, DECOCE_FAIL);
			decodeHandle.sendMessageDelayed(msg, 800);
		}

		/**
		 * 1.去
		 */
		private String removeNotNum(String str) {
			return str.replaceAll("[^\\d]", "");
		}

	}

	public static Bitmap cutBitmap(Bitmap mBitmap, Rect r, Bitmap.Config config) {
		int width = r.width();
		int height = r.height();

		Bitmap croppedImage = Bitmap.createBitmap(width, height, config);

		Canvas cvs = new Canvas(croppedImage);
		Rect dr = new Rect(0, 0, width, height);

		cvs.drawBitmap(mBitmap, r, dr, null);

		return croppedImage;
	}

	public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
		Rect rect = mCameraManager.getFramingRectInPreview();
		if (rect == null) {
			return null;
		}
		return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height(),
				false);
	}

	private void pauseCamera() {
		if (mIsExternalStorageStatusOk) {
			mCameraManager.stopPreview();
			mCameraManager.closeDriver();
			if (!mHasSurface) {
				SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
				surfaceHolder.removeCallback(mSurcallback);
			}
			CommonUtil.log("调用了释放资源方法");
		}
	}

	private SurfaceHolder.Callback mSurcallback = new SurfaceHolder.Callback() {
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			mHasSurface = false;
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if (holder == null) {
			}
			if (!mHasSurface) {
				mHasSurface = true;
				initCamera(holder);
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
	};

	private void initCamera(SurfaceHolder sh) {
		if (sh == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (mCameraManager.isOpen()) {
			return;
		}
		try {
			mCameraManager.openDriver(sh);
			mCameraManager.startPreview((int) (mScreenHeight * 0.8), (int) (mScreenWidth * 0.2));
			mCameraManager.requestPreviewFrame(decodeHandle, DECODE, new PreviewCallback());
		} catch (Exception e) {
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			final String fpdm = mEtFpdm.getText().toString();
			final String fphm = mEtFphm.getText().toString();
			MyToast.toast(fpdm + "," + fphm, 0);
			int fpContinuous;
			try {
				fpContinuous = Integer.valueOf(mEtContinuous.getText().toString());
			} catch (Exception e) {
				fpContinuous = 1;
			}
			if (validate(fpdm, fphm)) {

				List<ContentValues> list = new ArrayList<ContentValues>();
				ContentValues values;
				for (int i = 0; i < fpContinuous; i++) {
					values = new ContentValues();
					values.put(Constant.FP_DM, fpdm);
					// 连续录入发票
					String hm = String.valueOf(Integer.parseInt(fphm) + i);
					for (int k = 0; k < 8 - hm.length(); k++) {
						hm = "0" + hm;
					}
					values.put(Constant.FP_HM, hm);
					list.add(values);
				}
				DBUtil.addData(getActivity(), list);
				CommonUtil.sp.edit().putBoolean(Constant.IS_HAVE_NEW_FP_RECORD, true).commit();
				MyToast.toast(R.string.entry_success, 0);
				CommonUtil.sp.edit().putBoolean(Constant.IS_TO_REWARD, true).commit();
				refreshTable();
			}
			break;
		case R.id.btn_clear:
			mEtContinuous.setText("");
			mEtFpdm.setText("");
			mEtFphm.setText("");
		}
	}

	/**
	 * 刷新发票表格
	 */
	private void refreshTable() {
		getActivity().finish();
		myStartActivity(AtyMain.class);
	}

	private boolean validate(String fpdm, String fphm) {
		Pattern pFpdm = Pattern.compile("\\d{12}");
		Pattern pFphm = Pattern.compile("\\d{8}");
		boolean bFpdm = pFpdm.matcher(fpdm).matches();
		boolean bFphm = pFphm.matcher(fphm).matches();
		if (bFpdm && bFphm) {
			return true;
		} else if (!bFpdm) {
			MyToast.toast(MyAppli.getContext().getString(R.string.fpdm_12_num), 0);
		} else {
			MyToast.toast(MyAppli.getContext().getString(R.string.fphm_8_num), 0);
		}
		return false;
	}

	private OnTouchListener touchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.tv_manual:
				handleTouchOnManual(event);
				break;

			case R.id.tv_distinguish:
				handleTouchOnDistinguish(event);
			}
			return true;
		}

	};

	@SuppressWarnings("deprecation")
	private void handleTouchOnDistinguish(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			mTvDistinguish.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.x_in_method_distinguish_selected));
		} else if (action == MotionEvent.ACTION_UP) {
			mTvManual.setBackgroundDrawable(getResources().getDrawable(R.drawable.x_in_method_manual_unselected));
			mLlManual.setVisibility(View.GONE);
			mFlDistinguish.setVisibility(View.VISIBLE);
			mTvManual.setTextColor(Color.parseColor("#0099CC"));
			mTvDistinguish.setTextColor(getResources().getColor(android.R.color.white));
			mIsOpenDistinguish = true;
			mTimeOfSure = 0;
		}
	}

	@SuppressWarnings("deprecation")
	private void handleTouchOnManual(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			mTvManual.setBackgroundDrawable(getResources().getDrawable(R.drawable.x_in_method_manual_selected));
		} else if (action == MotionEvent.ACTION_UP) {
			handleTouchOnManualActionUp();
		}
	}

	@SuppressWarnings("deprecation")
	private void handleTouchOnManualActionUp() {
		mTvDistinguish.setBackgroundDrawable(getResources().getDrawable(R.drawable.x_in_method_distinguish_unselected));
		mLlManual.setVisibility(View.VISIBLE);
		mFlDistinguish.setVisibility(View.GONE);
		mTvDistinguish.setTextColor(Color.parseColor("#0099CC"));
		mTvManual.setTextColor(getResources().getColor(android.R.color.white));
		mIsOpenDistinguish = false;
	}

	protected void initViews(View v) {
		mTvManual = (TextView) v.findViewById(R.id.tv_manual);
		mTvDistinguish = (TextView) v.findViewById(R.id.tv_distinguish);
		mLlManual = (LinearLayout) v.findViewById(R.id.ll_manual);
		mFlDistinguish = (FrameLayout) v.findViewById(R.id.fl_distinguish);
		mBtnSave = (Button) v.findViewById(R.id.btn_save);
		mBtnClear = (Button) v.findViewById(R.id.btn_clear);
		mEtFpdm = (EditText) v.findViewById(R.id.et_fpdm);
		mEtFphm = (EditText) v.findViewById(R.id.et_fphm);
		mEtContinuous = (EditText) v.findViewById(R.id.et_continuous);

		iv = (ImageView) v.findViewById(R.id.iv_show_view);

		mFlDistinguish.setVisibility(View.GONE);
		if (mIsExternalStorageStatusOk) {
			mSurfaceView = (SurfaceView) mInView.findViewById(R.id.sv_face);
			mSurfaceHolder = mSurfaceView.getHolder();
			DisplayMetrics metrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			mScreenWidth = metrics.widthPixels;
			mScreenHeight = metrics.heightPixels;
			mBaseApi = new TessBaseAPI();
			mIsOpenDistinguish = false;
		} else {
			mTvDistinguish.setEnabled(false);
			MyToast.toast(R.string.external_storage_bad, 0);
		}
	}

	protected void initEvents() {
		mBtnSave.setOnClickListener(this);
		mBtnClear.setOnClickListener(this);
		mTvManual.setOnTouchListener(touchListener);
		mTvDistinguish.setOnTouchListener(touchListener);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mIsExternalStorageStatusOk) {
			pauseCamera();
		}
	}
}
