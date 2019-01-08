
package com.example.ocrsearch.camera;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.hardware.Camera;
import android.os.AsyncTask;

import com.example.ocrsearch.execute.AsyncTaskExecInterface;
import com.example.ocrsearch.execute.AsyncTaskExecManager;

final class AutoFocusManager implements Camera.AutoFocusCallback {
  private static final long AUTO_FOCUS_INTERVAL_MS = 2000L;
  private static final Collection<String> FOCUS_MODES_CALLING_AF;
  static {
    FOCUS_MODES_CALLING_AF = new ArrayList<String>(2);
    FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_AUTO);
    FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_MACRO);
  }

  private boolean active;
  private final boolean useAutoFocus = true;
  private final Camera camera;
  private AutoFocusTask outstandingTask;
  private final AsyncTaskExecInterface taskExec;
  public CameraManager manager ;
  AutoFocusManager(Context context, Camera camera) {
    this.camera = camera;
    taskExec = new AsyncTaskExecManager().build();
    start();
  }

  @Override
  public synchronized void onAutoFocus(boolean success, Camera theCamera) {
    if (active) {
      outstandingTask = new AutoFocusTask();
      taskExec.execute(outstandingTask);
    }
    if(success){
    	manager.requestPreviewFrame();
    }
  }

  synchronized void start() {
    if (useAutoFocus) {
      active = true;
      try {
        camera.autoFocus(this);
      } catch (RuntimeException re) {
      }
    }
  }

  synchronized void stop() {
    if (useAutoFocus) {
      try {
        camera.cancelAutoFocus();
      } catch (RuntimeException re) {
      }
    }
    if (outstandingTask != null) {
      outstandingTask.cancel(true);
      outstandingTask = null;
    }
    active = false;
  }

  private final class AutoFocusTask extends AsyncTask<Object,Object,Object> {
    @Override
    protected Object doInBackground(Object... voids) {
      try {
        Thread.sleep(AUTO_FOCUS_INTERVAL_MS);
      } catch (InterruptedException e) {
      }
      synchronized (AutoFocusManager.this) {
        if (active) {
          start();
        }
      }
      return null;
    }
  }

}
