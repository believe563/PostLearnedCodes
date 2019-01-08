
package com.example.ocrsearch.camera;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;

final class PreviewCallback implements Camera.PreviewCallback {

  private final CameraConfigurationManager configManager;
  private Handler previewHandler;
  private int previewMessage;

  PreviewCallback(CameraConfigurationManager configManager) {
    this.configManager = configManager;
  }

  void setHandler(Handler previewHandler, int previewMessage) {
    this.previewHandler = previewHandler;
    this.previewMessage = previewMessage;
  }

  @Override
  public void onPreviewFrame(byte[] data, Camera camera) {
	  if (data != null)
      {
          Camera.Parameters parameters = camera.getParameters();
          int imageFormat = parameters.getPreviewFormat();
          if (imageFormat == ImageFormat.NV21)
          {
              Bitmap image = null;
              int w = parameters.getPreviewSize().width;
              int h = parameters.getPreviewSize().height;
                
              Rect rect = new Rect(0, 0, w, h); 
              YuvImage img = new YuvImage(data, ImageFormat.NV21, w, h, null);
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              if (img.compressToJpeg(rect, 100, baos)) 
              { 
                  image =  BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
                  if(image==null)
                  {
                	  
                  }
                  
              }
      
          }
      }
	  
	  
    Point cameraResolution = configManager.getCameraResolution();
    Handler thePreviewHandler = previewHandler;
    if (cameraResolution != null && thePreviewHandler != null) {
      Message message = thePreviewHandler.obtainMessage(previewMessage, cameraResolution.x,
          cameraResolution.y, data);
      message.sendToTarget();
      previewHandler = null;
    } else {
    }
  }

}
