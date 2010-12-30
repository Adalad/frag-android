package fraguel.android.ar;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class ARGLView extends GLSurfaceView {

	// Renderer
	private ARRenderer _renderer;

	// World position (GPS coordinates)
	public float[] _worldPosition = { 0f, 0f, 0f };
	// World orientation (radians from north)
	public float[] _worldRotation = { 0f, 0f, 0f };

	public ARGLView(Context context) {
		super(context);

		// Transparency
		_renderer = new ARRenderer();
		_renderer.init();
		setEGLConfigChooser(8,8,8,8,16,0);
		setRenderer(_renderer);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		setZOrderOnTop(true);
	}

}
