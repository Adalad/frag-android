package fraguel.android.ar;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ARGLView extends GLSurfaceView {

	/** ATTRIBUTES **/
	// Renderer
	private ARRenderer _renderer;

	// World position (GPS coordinates)
	// private float[] _worldPosition = { 0f, 0f, 0f };
	// World orientation (degrees from north
	// private int[] _worldRotation = { 0, 0, 0 };

	/** CONSTRUCTOR **/
	public ARGLView(Context context) {
		super(context);

		_renderer = new ARRenderer();
		setRenderer(_renderer);
		// TODO transparencia
		//setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		//getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	/** PUBLIC METHODS **/
	// TODO procesar eventos, GPS, brujula, acelerometro

}
