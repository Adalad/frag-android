package fraguel.android.ar;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import fraguel.android.ar.core.Object3dContainer;
import fraguel.android.ar.core.Scene;
import fraguel.android.ar.interfaces.ISceneController;
import fraguel.android.ar.objectPrimitives.Box;
import fraguel.android.ar.vos.Light;
import fraguel.android.resources.ar.IParser;
import fraguel.android.resources.ar.Parser;

public class GLView extends GLSurfaceView implements ISceneController, Camera.PreviewCallback {

	public Scene scene;
	protected GLSurfaceView _glSurfaceView;
	public static final float RADIO_TIERRA=6371000;

	protected Handler _initSceneHander;
	protected Handler _updateSceneHander;
	
	Object3dContainer _cube;
	private Object3dContainer objModel;

	final Runnable _initSceneRunnable = new Runnable() {
		public void run() {
			onInitScene();
		}
	};

	final Runnable _updateSceneRunnable = new Runnable() {
		public void run() {
			onUpdateScene();
		}
	};

	public GLView(Context context) {
		super(context);

		_initSceneHander = new Handler();
		_updateSceneHander = new Handler();

		//
		// These 4 lines are important.
		//
		Shared.context(context);
		scene = new Scene(this);
		fraguel.android.ar.core.Renderer r = new fraguel.android.ar.core.Renderer(scene);
		Shared.renderer(r);

		_glSurfaceView = this;

		_glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		_glSurfaceView.setRenderer(r);
		_glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		_glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	/**
	 * Instantiation of Object3D's, setting their properties, and adding
	 * Object3D's to the scene should be done here. Or any point thereafter.
	 * 
	 * Note that this method is always called after GLCanvas is created, which
	 * occurs not only on Activity.onCreate(), but on Activity.onResume() as
	 * well. It is the user's responsibility to build the logic to restore state
	 * on-resume.
	 */
	public void initScene() {
		scene.reset();
		
		scene.backgroundTransparent(true);
		/*scene.lights().add(new Light());
		
		scene.backgroundColor().setAll(0xff444444);
		_cube = new Box(1,1,1);
		scene.addChild(_cube);*/
		scene.lights().add(new Light());
		
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "fraguel.android:raw/camaro_obj", true);
		parser.parse();

		objModel = parser.getParsedObject();
		objModel.scale().x = objModel.scale().y = objModel.scale().z = .7f;
		objModel.position().x = (float) (40.417923*RADIO_TIERRA);
		objModel.position().y = 670;
		objModel.position().z = (float) (-3.67011*RADIO_TIERRA);
		//40.417923,-3.67011,670metros
		scene.addChild(objModel);
		scene.camera().position.x = 0;
		scene.camera().position.y = 0;
		scene.camera().position.z = 10;
		Log.d("FRAGUEL", "End AR load");
	}

	/**
	 * All manipulation of scene and Object3D instance properties should go
	 * here. Gets called on every frame, right before drawing.
	 */
	public void updateScene() {
		//_cube.rotation().y++;
		//objModel.rotation().x++;
		//objModel.rotation().z++;
	}

	/**
	 * Called _after_ scene init (ie, after initScene). Unlike initScene(), is
	 * thread-safe.
	 */
	public void onInitScene() {
	}

	/**
	 * Called _after_ scene init (ie, after initScene). Unlike initScene(), is
	 * thread-safe.
	 */
	public void onUpdateScene() {
	}

	public Handler getInitSceneHandler() {
		return _initSceneHander;
	}

	public Handler getUpdateSceneHandler() {
		return _updateSceneHander;
	}

	public Runnable getInitSceneRunnable() {
		return _initSceneRunnable;
	}

	public Runnable getUpdateSceneRunnable() {
		return _updateSceneRunnable;
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		
	}
}
