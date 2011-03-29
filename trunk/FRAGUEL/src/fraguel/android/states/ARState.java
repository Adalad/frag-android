package fraguel.android.states;

import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.ar.CamLayer;
import fraguel.android.ar.GLView;

public class ARState extends State {
	
	public static final int STATE_ID = 5;

	//ARCameraView camView;
	GLView glView;
	private CamLayer mPreview;
	//private GLLayer glView;

	public ARState() {
		super();
		id = STATE_ID;
	}

	@Override
	public void load() {
		FRAGUEL.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// Inicializar viewGroup
		//viewGroup = new FrameLayout(FRAGUEL.getInstance().getApplicationContext());
		/*// Crear vista de la cámara
		camView = new ARCameraView(viewGroup.getContext());
		viewGroup.addView(camView);*/
		// Crear vista OpenGL
		glView = new GLView(FRAGUEL.getInstance()
				.getApplicationContext());
		//glView = new ARGLView(viewGroup.getContext());
		//viewGroup.addView(glView);
		/*glView=new GLLayer(viewGroup.getContext());*/
        
		//mPreview = new CamLayer(viewGroup.getContext(), 240, 160);
		mPreview = new CamLayer(glView.getContext(), 240, 160);
		mPreview.synchronCallback=glView;
		
		/*FRAGUEL.getInstance().setContentView(glView);
		FRAGUEL.getInstance().addContentView(mPreview, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));*/
		//viewGroup.addView(glView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		//viewGroup.addView(mPreview, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// Añadir a la actividad
		FRAGUEL.getInstance().setContentView(glView);
		FRAGUEL.getInstance().addContentView(mPreview, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		glView.setKeepScreenOn(true);
		//FRAGUEL.getInstance().addView(viewGroup);
	}

	@Override
	public void unload(){
		glView.setKeepScreenOn(false);
		FRAGUEL.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		FRAGUEL.getInstance().setContentView(FRAGUEL.getInstance().getView());
		super.unload();
	}
	@Override
	public void onRotationChanged(float[] values) {
		glView.scene.camera().rotation.x = values[2] + 90;
		glView.scene.camera().rotation.y = values[0] + 90;
		glView.scene.camera().rotation.z = -values[1];
	}

	@Override
	public void onLocationChanged(float[] values) {
	//	glView._worldPosition[0] = -values[0];
	//	glView._worldPosition[1] = -values[1];
	//	glView._worldPosition[2] = -values[2];
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		return false;
	}


}
