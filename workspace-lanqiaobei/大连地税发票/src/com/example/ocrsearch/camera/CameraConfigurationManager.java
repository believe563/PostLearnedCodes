package com.example.ocrsearch.camera;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

final class CameraConfigurationManager {

	private static final String TAG = "CameraConfiguration";

	private static final int MIN_PREVIEW_PIXELS = 470 * 320;
	private static final int MAX_PREVIEW_PIXELS = 1920 * 1080;

	private final Context context;
	private Point screenResolution;
	private Point cameraResolution;

	CameraConfigurationManager(Context context) {
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	void initFromCameraParameters(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		if (width < height) {
			int temp = width;
			width = height;
			height = temp;
		}
		screenResolution = new Point(width, height);
		cameraResolution = findBestPreviewSizeValue(parameters, screenResolution);
	}

	void setDesiredCameraParameters(Camera camera, boolean safeMode) {
		Camera.Parameters parameters = camera.getParameters();

		if (parameters == null) {
			return;
		}

		if (safeMode) {
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		initializeTorch(parameters, prefs, safeMode);
		String focusMode = null;
		if (!safeMode && focusMode == null) {
			focusMode = findSettableValue(parameters.getSupportedFocusModes(), Camera.Parameters.FOCUS_MODE_MACRO,
					"edof");
		}
		if (focusMode != null) {
			parameters.setFocusMode(focusMode);
		}

		parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
		camera.setParameters(parameters);
	}

	Point getCameraResolution() {
		return cameraResolution;
	}

	Point getScreenResolution() {
		return screenResolution;
	}

	void setTorch(Camera camera, boolean newSetting) {
		Camera.Parameters parameters = camera.getParameters();
		doSetTorch(parameters, newSetting, false);
		camera.setParameters(parameters);
	}

	private void initializeTorch(Camera.Parameters parameters, SharedPreferences prefs, boolean safeMode) {
	}

	private void doSetTorch(Camera.Parameters parameters, boolean newSetting, boolean safeMode) {
		String flashMode;
		if (newSetting) {
			flashMode = findSettableValue(parameters.getSupportedFlashModes(), Camera.Parameters.FLASH_MODE_TORCH,
					Camera.Parameters.FLASH_MODE_ON);
		} else {
			flashMode = findSettableValue(parameters.getSupportedFlashModes(), Camera.Parameters.FLASH_MODE_OFF);
		}
		if (flashMode != null) {
			parameters.setFlashMode(flashMode);
		}
	}

	private Point findBestPreviewSizeValue(Camera.Parameters parameters, Point screenResolution) {
		List<Camera.Size> rawSupportedSizes = parameters.getSupportedPreviewSizes();
		if (rawSupportedSizes == null) {
			Camera.Size defaultSize = parameters.getPreviewSize();
			return new Point(defaultSize.width, defaultSize.height);
		}

		List<Camera.Size> supportedPreviewSizes = new ArrayList<Camera.Size>(rawSupportedSizes);
		Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
			@Override
			public int compare(Camera.Size a, Camera.Size b) {
				int aPixels = a.height * a.width;
				int bPixels = b.height * b.width;
				if (bPixels < aPixels) {
					return -1;
				}
				if (bPixels > aPixels) {
					return 1;
				}
				return 0;
			}
		});

		if (Log.isLoggable(TAG, Log.INFO)) {
			StringBuilder previewSizesString = new StringBuilder();
			for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
				previewSizesString.append(supportedPreviewSize.width).append('x').append(supportedPreviewSize.height)
						.append(' ');
			}
		}

		Point bestSize = null;
		float screenAspectRatio = (float) screenResolution.x / (float) screenResolution.y;

		float diff = Float.POSITIVE_INFINITY;
		for (Camera.Size supportedPreviewSize : supportedPreviewSizes) {
			int realWidth = supportedPreviewSize.width;
			int realHeight = supportedPreviewSize.height;
			int pixels = realWidth * realHeight;
			if (pixels < MIN_PREVIEW_PIXELS || pixels > MAX_PREVIEW_PIXELS) {
				continue;
			}
			boolean isCandidatePortrait = realWidth < realHeight;
			int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
			int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;
			if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
				Point exactPoint = new Point(realWidth, realHeight);
				return exactPoint;
			}
			float aspectRatio = (float) maybeFlippedWidth / (float) maybeFlippedHeight;
			float newDiff = Math.abs(aspectRatio - screenAspectRatio);
			if (newDiff < diff) {
				bestSize = new Point(realWidth, realHeight);
				diff = newDiff;
			}
		}

		if (bestSize == null) {
			Camera.Size defaultSize = parameters.getPreviewSize();
			bestSize = new Point(defaultSize.width, defaultSize.height);
		}

		return bestSize;
	}

	private static String findSettableValue(Collection<String> supportedValues, String... desiredValues) {
		String result = null;
		if (supportedValues != null) {
			for (String desiredValue : desiredValues) {
				if (supportedValues.contains(desiredValue)) {
					result = desiredValue;
					break;
				}
			}
		}
		return result;
	}

}
