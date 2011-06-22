package fraguel.android.states;

import java.io.File;
import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.ar.CamLayer;
import fraguel.android.ar.GLView;
import fraguel.android.gps.LatLon2UTM;
import fraguel.android.resources.ResourceManager;

public class ARState extends State {

	public static final int STATE_ID = 5;

	// ARCameraView camView;
	GLView glView;
	private CamLayer mPreview;
	public ArrayList<String> models;
	
	private LatLon2UTM ll;

	// private GLLayer glView;

	public ARState() {
		super();
		id = STATE_ID;
		models = new ArrayList<String>();
	}

	@Override
	public void load() {
		
		ll = new LatLon2UTM();
		
		FRAGUEL.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// Inicializar viewGroup
		// viewGroup = new
		// FrameLayout(FRAGUEL.getInstance().getApplicationContext());
		/*
		 * // Crear vista de la cámara camView = new
		 * ARCameraView(viewGroup.getContext()); viewGroup.addView(camView);
		 */
		// Crear vista OpenGL
		glView = new GLView(FRAGUEL.getInstance().getApplicationContext());
		// glView = new ARGLView(viewGroup.getContext());
		// viewGroup.addView(glView);
		/* glView=new GLLayer(viewGroup.getContext()); */

		// mPreview = new CamLayer(viewGroup.getContext(), 240, 160);
		mPreview = new CamLayer(glView.getContext(), 240, 160);
		mPreview.synchronCallback = glView;

		/*
		 * FRAGUEL.getInstance().setContentView(glView);
		 * FRAGUEL.getInstance().addContentView(mPreview, new
		 * LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		 */
		// viewGroup.addView(glView, new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT));
		// viewGroup.addView(mPreview, new
		// LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// Añadir a la actividad
		FRAGUEL.getInstance().setContentView(glView);
		FRAGUEL.getInstance().addContentView(mPreview,new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		glView.setKeepScreenOn(true);
		// FRAGUEL.getInstance().addView(viewGroup);
		models.clear();
	}

	@Override
	public void unload() {
		glView.setKeepScreenOn(false);
		FRAGUEL.getInstance().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		FRAGUEL.getInstance().setContentView(FRAGUEL.getInstance().getView());
		super.unload();
	}
	
	@Override
	public boolean loadData(Route r,PointOI p){
		super.loadData(r, p);
		if (p.urlAr!=""){
			File f = new File(ResourceManager.getInstance().getRootPath()+"/ar/oso.obj");
			if (f.exists()){
				models.add("oso.obj");
				models.add("oso2.obj");
			}
		}
		if (p.textAr!=null && p.textAr!="")
			FRAGUEL.getInstance().talk(point.pointdescription);
		return true;
	}

	@Override
	public void onRotationChanged(float[] values) {
		int d = 2;
		float delta = glView.scene.camera().rotation.x - (values[2] + 90);
		if (Math.abs(delta) > d)
			glView.scene.camera().rotation.x = values[2] + 90;
		delta = glView.scene.camera().rotation.y - (values[0] + 90);
		if (Math.abs(delta) > d)
			glView.scene.camera().rotation.y = values[0] + 90;
		delta = glView.scene.camera().rotation.z - values[1];
		if (Math.abs(delta) > d)
			glView.scene.camera().rotation.z = values[1];
	}

	@Override
	public void onLocationChanged(float[] values) {
		/*ll.setVariables(values[0], values[1]);
		float x = (float) ll.getEasting();
		float y = (float) ll.getNorthing(values[1]);
		float z = values[2];

		glView.scene.camera().position.x = x;
		glView.scene.camera().position.y = z;
		glView.scene.camera().position.z = -y;*/
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
