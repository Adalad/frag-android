package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.ar.ARCameraView;
import fraguel.android.ar.ARGLView;
import android.view.View;
import android.widget.FrameLayout;

public class ARState extends State {

	ARCameraView camView;
	ARGLView glView;

	public ARState() {
		super();
		id = 5;
	}

	@Override
	public void load() {
		// Inicializar viewGroup
		viewGroup = new FrameLayout(FRAGUEL.getInstance()
				.getApplicationContext());
		// Crear vista de la cámara
		//camView = new ARCameraView(viewGroup.getContext());
		//viewGroup.addView(camView);
		// Crear vista OpenGL
		glView = new ARGLView(viewGroup.getContext());
		viewGroup.addView(glView);
		// Añadir a la actividad
		FRAGUEL.getInstance().addView(viewGroup);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
