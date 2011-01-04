package fraguel.android.ar;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ARMesh implements Comparable<ARMesh> {

	private int _id;
	private ShortBuffer _indexBuffer;
	private FloatBuffer _vertexBuffer;
	private int _nrOfVertices;
	private int _type;

	public ARMesh(int i, ShortBuffer iB, FloatBuffer vB, int nV, int t) {
		_id = i;
		_indexBuffer = iB;
		_vertexBuffer = vB;
		_nrOfVertices = nV;
		_type = t;
	}

	public int getId() {
		return _id;
	}

	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
		gl.glDrawElements(_type, _nrOfVertices, GL10.GL_UNSIGNED_SHORT,
				_indexBuffer);
	}

	@Override
	public int compareTo(ARMesh another) {
		if (_id == ((ARMesh) another).getId())
			return 0;
		return 1;
	}

}
