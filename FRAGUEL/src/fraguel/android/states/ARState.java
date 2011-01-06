package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.ar.ARCameraView;
import fraguel.android.ar.ARGLView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class ARState extends State {
	
	public static final int STATE_ID = 5;

	ARCameraView camView;
	ARGLView glView;

	public ARState() {
		super();
		id = STATE_ID;
	}

	@Override
	public void load() {
		// Inicializar viewGroup
		viewGroup = new FrameLayout(FRAGUEL.getInstance()
				.getApplicationContext());
		// Crear vista de la cámara
		camView = new ARCameraView(viewGroup.getContext());
		viewGroup.addView(camView);
		// Crear vista OpenGL
//		glView = new ARGLView(viewGroup.getContext());
//		viewGroup.addView(glView);
		// Añadir a la actividad
		FRAGUEL.getInstance().addView(viewGroup);
	}

	@Override
	public void onRotationChanged(float[] values) {
		glView._worldRotation[0] = -values[0];
		glView._worldRotation[1] = -values[1];
		glView._worldRotation[2] = -values[2];
	}

	@Override
	public void onLocationChanged(float[] values) {
		glView._worldPosition[0] = -values[0];
		glView._worldPosition[1] = -values[1];
		glView._worldPosition[2] = -values[2];
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
