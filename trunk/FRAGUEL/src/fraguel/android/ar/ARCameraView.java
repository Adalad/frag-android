package fraguel.android.ar;

import android.content.Context;
import android.hardware.Camera;
import android.view.View;

public class ARCameraView extends View {
	
	private ARCameraPreview mPreview;
    Camera mCamera;

	public ARCameraView(Context context) {
		super(context);
		mPreview = new ARCameraPreview(this.getContext());
		mCamera = Camera.open();
		mPreview.setCamera(mCamera);
	}

}
