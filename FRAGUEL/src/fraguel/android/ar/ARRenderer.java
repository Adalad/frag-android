package fraguel.android.ar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class ARRenderer implements GLSurfaceView.Renderer {

	
	/** PUBLIC METHODS **/
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClearColor(1.0f, .0f, .0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		// TODO pintar geometria		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// preparation
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// TODO inicializar geometria
	}
	
}
