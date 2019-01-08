
package com.example.ocrsearch.camera;

import java.io.IOException;

import com.innova.reward.frag.InFrag;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;

public final class CameraManager {

	private static final int MIN_FRAME_WIDTH =240;
	private static final int MIN_FRAME_HEIGHT = 240;
	private static final int MAX_FRAME_WIDTH = 1920;
	private static final int MAX_FRAME_HEIGHT = 1240;
	
	private final Context context;
	private final CameraConfigurationManager configManager;
	private Camera camera;
	private AutoFocusManager autoFocusManager;
	private Rect framingRect;
	private Rect framingRectInPreview;
	private boolean initialized;
	private boolean previewing;
	private int requestedFramingRectWidth;
	private int requestedFramingRectHeight;
	private final PreviewCallback previewCallback;

	public CameraManager(Context context, InFrag inFrag) {
		this.context = context;
		this.configManager = new CameraConfigurationManager(context);
		previewCallback = new PreviewCallback(configManager);
	}
	public synchronized void openDriver(SurfaceHolder holder)
			throws IOException {
		Camera theCamera = camera;
		if (theCamera == null) {
			theCamera = Camera.open();
			if (theCamera == null) {
				throw new IOException();
			}else {
					theCamera.setDisplayOrientation(90);
			}
			camera = theCamera;
		}
		theCamera.setPreviewDisplay(holder);

		if (!initialized) {
			initialized = true;
			configManager.initFromCameraParameters(theCamera);
			if (requestedFramingRectWidth > 0 && requestedFramingRectHeight > 0) {
				setManualFramingRect(requestedFramingRectWidth,
						requestedFramingRectHeight);
				requestedFramingRectWidth = 0;
				requestedFramingRectHeight = 0;
			}
		}

		Camera.Parameters parameters = theCamera.getParameters();
		String parametersFlattened = parameters == null ? null : parameters
				.flatten(); 
		try {
			configManager.setDesiredCameraParameters(theCamera, false);
		} catch (RuntimeException re) {
			if (parametersFlattened != null) {
				parameters = theCamera.getParameters();
				parameters.unflatten(parametersFlattened);
				try {
					theCamera.setParameters(parameters);
					configManager.setDesiredCameraParameters(theCamera, true);
				} catch (RuntimeException re2) {
				}
			}
		}

	}

	public synchronized boolean isOpen() {
		return camera != null;
	}
	public synchronized void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
			framingRect = null;
			framingRectInPreview = null;
		}
	}

	public synchronized void startPreview(int width,int height) {
		Camera theCamera = camera;
		if (theCamera != null && !previewing) {
			theCamera.startPreview();
			previewing = true;
			autoFocusManager = new AutoFocusManager(context, camera);
			autoFocusManager.manager = this;
			//取景框
			setManualFramingRect(width,height);
		}
	}

	public synchronized void stopPreview() {
		if (autoFocusManager != null) {
			autoFocusManager.stop();
			autoFocusManager = null;
		}
		if (camera != null && previewing) {
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			previewing = false;
		}
	}
	public static int getDisplayRotation(Activity activity) {
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		switch (rotation) {
			case Surface.ROTATION_0 :
				return 0;
			case Surface.ROTATION_90 :
				return 90;
			case Surface.ROTATION_180 :
				return 180;
			case Surface.ROTATION_270 :
				return 270;
		}
		return 0;
	}

	public static void setCameraDisplayOrientation(Activity activity,
			int cameraId, Camera camera) {
	}
	public synchronized void setTorch(boolean newSetting) {
		if (camera != null) {
			if (autoFocusManager != null) {
				autoFocusManager.stop();
			}
			configManager.setTorch(camera, newSetting);
			if (autoFocusManager != null) {
				autoFocusManager.start();
			}
		}
	}
	private Handler handler = null;
	private int message = -1;
	private Camera.PreviewCallback pre = null ;
	public synchronized void requestPreviewFrame(Handler handler, int message,Camera.PreviewCallback pre) {
		this.handler = handler;
		this.message = message;
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			previewCallback.setHandler(handler, message);
			this.pre = pre ;
			theCamera.setOneShotPreviewCallback(pre);
		}
	}

	public synchronized void requestPreviewFrame() {
		if (handler != null) {
			Camera theCamera = camera;
			if (theCamera != null && previewing) {
				previewCallback.setHandler(handler, message);
				theCamera.setOneShotPreviewCallback(pre);
			}
		}
	}
	public synchronized Rect getFramingRect() {
		if (framingRect == null) {
			if (camera == null) {
				return null;
			}
			Point screenResolution = configManager.getScreenResolution();
			if (screenResolution == null) {
				return null;
			}
			int width = screenResolution.y * 3 / 4;
			if (width < MIN_FRAME_WIDTH) {
				width = MIN_FRAME_WIDTH;
			} else if (width > MAX_FRAME_WIDTH) {
				width = MAX_FRAME_WIDTH;
			}
			int height = screenResolution.x * 3 / 4;
			if (height < MIN_FRAME_HEIGHT) {
				height = MIN_FRAME_HEIGHT;
			} else if (height > MAX_FRAME_HEIGHT) {
				height = MAX_FRAME_HEIGHT;
			}
			int leftOffset = (screenResolution.y- width) / 2;
			int topOffset = (screenResolution.x - height) / 2;
			framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
			topOffset + height);
		}
		return framingRect;
	}

	public synchronized Rect getFramingRectInPreview() {
		if (framingRectInPreview == null) {
			Rect framingRect = getFramingRect();
			if (framingRect == null) {
				return null;
			}
			Rect rect = new Rect(framingRect);
			Point cameraResolution = configManager.getCameraResolution();
			Point screenResolution = configManager.getScreenResolution();
			if (cameraResolution == null || screenResolution == null) {
				return null;
			}
			rect.left = rect.left * cameraResolution.x / screenResolution.x;
			rect.right = rect.right * cameraResolution.x / screenResolution.x;
			rect.top = rect.top * cameraResolution.y / screenResolution.y;
			rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
			framingRectInPreview = rect;
		}
		return framingRectInPreview;
	}

	//尽量不再动
	public synchronized void setManualFramingRect(int width, int height) {
		if (initialized) {
			Point screenResolution = configManager.getScreenResolution();
			if (width > screenResolution.y) {
				width = screenResolution.y;
			}
			if (height > screenResolution.x) {
				height = screenResolution.x;
			}
			int leftOffset = (screenResolution.x - height) / 2;
			int topOffset = (screenResolution.y - width) / 2;
			framingRect = new Rect(leftOffset,(int)(topOffset-screenResolution.x*0.1) ,leftOffset + height ,
					(int)(topOffset + width-screenResolution.x*0.1));//设置预览框
			framingRectInPreview = null;
		} else {
			requestedFramingRectWidth = width;
			requestedFramingRectHeight = height;
		}
	}
}
