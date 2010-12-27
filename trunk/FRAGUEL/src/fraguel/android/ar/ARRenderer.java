package fraguel.android.ar;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class ARRenderer implements GLSurfaceView.Renderer {

	//--------- PRUEBAS -----------------
	// a raw buffer to hold indices allowing a reuse of points.
	private ShortBuffer _indexBuffer;

	// a raw buffer to hold the vertices
	private FloatBuffer _vertexBuffer;

	private short[] _indicesArray = { 0, 1, 2 };
	private int _nrOfVertices = 3;
	//---------- FIN PRUEBAS --------------
	
	/** PUBLIC METHODS **/
	@Override
	public void onDrawFrame(GL10 gl) {
		//gl.glClearColor(1.0f, .0f, .0f, 1.0f);
		//gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		// TODO pintar geometria		
		//------ PRUEBAS -----
		gl.glColor4f(1.0f, 0f, 0f, 0.5f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, _nrOfVertices,
				GL10.GL_UNSIGNED_SHORT, _indexBuffer);
		//------ FIN PRUEBAS --
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
		initTriangle();	// PRUEBAS
	}
	
	/*
	 * PRUEBAS
	 */
	private void initTriangle() {
		// float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(_nrOfVertices * 3 * 4);
		vbb.order(ByteOrder.nativeOrder());
		_vertexBuffer = vbb.asFloatBuffer();

		// short has 4 bytes
		ByteBuffer ibb = ByteBuffer.allocateDirect(_nrOfVertices * 2);
		ibb.order(ByteOrder.nativeOrder());
		_indexBuffer = ibb.asShortBuffer();

		// float has 4 bytes, 4 colors (RGBA) * number of vertices * 4 bytes
		/*
		 * ByteBuffer cbb = ByteBuffer.allocateDirect(4 * _nrOfVertices * 4);
		 * cbb.order(ByteOrder.nativeOrder()); _colorBuffer =
		 * cbb.asFloatBuffer();
		 */

		float[] coords = { -0.5f, -0.5f, 0f, // (x1, y1, z1)
				0.5f, -0.5f, 0f, // (x2, y2, z2)
				0.5f, 0.5f, 0f // (x3, y3, z3)
		};

		/*
		 * float[] colors = { 1f, 0f, 0f, 1f, // point 1 0f, 1f, 0f, 1f, //
		 * point 2 0f, 0f, 1f, 1f, // point 3 };
		 */

		_vertexBuffer.put(coords);
		_indexBuffer.put(_indicesArray);
		// _colorBuffer.put(colors);

		_vertexBuffer.position(0);
		_indexBuffer.position(0);
		// _colorBuffer.position(0);

	}
	
}
