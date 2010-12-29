package fraguel.android.ar;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ARMesh {

	private ShortBuffer _indexBuffer;
	private FloatBuffer _vertexBuffer;
	private int _nrOfVertices;
	private int _type;

	public ARMesh(ShortBuffer iB, FloatBuffer vB, int nV, int t) {
		_indexBuffer = iB;
		_vertexBuffer = vB;
		_nrOfVertices = nV;
		_type = t;
	}

	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
		gl.glDrawElements(_type, _nrOfVertices, GL10.GL_UNSIGNED_SHORT,
				_indexBuffer);
	}

}
