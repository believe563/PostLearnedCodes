package com.example.ocrsearch;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.ocrsearch.camera.CameraManager;
import com.innova.reward.R;

public class OcrFinderView extends View {

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192, 128, 64 };
	private static final long ANIMATION_DELAY = 80;
	private static final int CURRENT_POINT_OPACITY = 0xA0;
	private static final int POINT_SIZE = 6;
	private final Paint paint;
	private final int maskColor;
	private final int resultColor;
	private final int laserColor;
	private int scannerAlpha;
	private CameraManager cameraManager;
	private Bitmap resultBitmap;
	private OcrFinderView ocrFinderView;

	public OcrFinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ocrFinderView = (OcrFinderView) findViewById(R.id.ocrfv_find_view);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		laserColor = resources.getColor(R.color.viewfinder_laser);
		scannerAlpha = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (cameraManager == null) {
			return;
		}
		Rect frame = cameraManager.getFramingRect();
		if (frame == null) {
			return;
		}
		int w = ocrFinderView.getWidth();
		int h = ocrFinderView.getHeight();
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		// ******************************************************
		// 这四句决定扫描框的位置和大小！

		// canvas.drawRect((float) (w*0.1), (float) (h*0.45), (float) (w*0.9),
		// (float) (h*0.55), paint);
		canvas.drawRect(0, 0, w, (float) (h * 0.45), paint);
		canvas.drawRect(0, (float) (h * 0.45), (float) (w * 0.1), (float) (h * 0.55), paint);
		canvas.drawRect((float) (w * 0.9), (float) (h * 0.45), w, (float) (h * 0.55), paint);
		canvas.drawRect(0, (float) (h * 0.55), w, h, paint);
		// ******************************************************
		if (resultBitmap != null) {
			paint.setAlpha(CURRENT_POINT_OPACITY);
			canvas.drawBitmap(resultBitmap, null, frame, paint);
		} else {
			paint.setColor(laserColor);
			paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
			scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
			postInvalidateDelayed(ANIMATION_DELAY, frame.left - POINT_SIZE, frame.top - POINT_SIZE, frame.right
					+ POINT_SIZE, frame.bottom + POINT_SIZE);
		}
	}

	public void setCameraManager(CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	public void drawViewfinder() {
		Bitmap resultBitmap = this.resultBitmap;
		this.resultBitmap = null;
		if (resultBitmap != null) {
			resultBitmap.recycle();
		}
		invalidate();
	}
}
