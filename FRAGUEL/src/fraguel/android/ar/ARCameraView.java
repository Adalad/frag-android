package fraguel.android.ar;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ARCameraView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder _holder;
	private Camera _camera;

	public ARCameraView(Context context) {
		super(context);
		_holder = getHolder();
		_holder.addCallback(this);
		_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			_camera = Camera.open();
			if (_camera != null) {
				try {
					_camera.setPreviewDisplay(holder);
					_camera.startPreview();
				} catch (Exception e) {
					_camera.release();
					_camera = null;
					Log.d("FRAGUEL", "ARCameraView", e);
				}
			}
		} catch (Exception e) {
			Log.d("FRAGUEL", "ARCameraView", e);
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		_camera.stopPreview();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	}

}
