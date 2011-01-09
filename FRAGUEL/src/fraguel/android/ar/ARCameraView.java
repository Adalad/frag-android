package fraguel.android.ar;

import java.io.IOException;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ARCameraView extends SurfaceView  {

	Camera camera;
	SurfaceHolder holder;

	public ARCameraView(Context context) {
		super(context);

		holder = getHolder();
		holder.addCallback(surfaceHolderListener);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	
	
	SurfaceHolder.Callback surfaceHolderListener = new SurfaceHolder.Callback() {
	      public void surfaceCreated(SurfaceHolder holder) {
	            camera=Camera.open();

	            try {
					camera.setPreviewDisplay(holder);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            
	           }
	   public void surfaceChanged(SurfaceHolder holder, int format, int width,
	         int height)
	   {
	      Parameters params = camera.getParameters();
	      params.setPreviewSize(width, height);
	      params.setPictureFormat(PixelFormat.JPEG);
	              camera.setParameters(params);
	              camera.startPreview();
	   }

	   public void surfaceDestroyed(SurfaceHolder arg0)
	   {
	      camera.stopPreview();
	      camera.release();   
	   }
	 };
	
	/*
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(holder);
			camera.setPreviewCallback(new PreviewCallback() {

				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {

				}
			});
		} catch (Exception e) {
			camera.release();
            camera = null;
            Log.e("Camera", "Error en SurfaceCreated", exception);
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		try {
			camera.setPreviewDisplay(holder);
			/*camera.setPreviewCallback(new PreviewCallback() {

				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {

				}
			});
		} catch (Exception e) {
			camera.release();
            camera = null;
            //Log.e("Camera", "Error en SurfaceCreated", exception);
			//e.printStackTrace();
		}
		
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(width, height);
		camera.setParameters(parameters);
		camera.startPreview();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
	}*/

}
