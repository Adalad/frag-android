package fraguel.android.ar;

import javax.microedition.khronos.opengles.GL10;

public class ARElement {

	public ARMesh _mesh;
	public float[] _posXYZ = { 0.0f, 0.0f, 0.0f };
	public float[] _rotXYZ = { 0.0f, 0.0f, 0.0f };
	public float[] _color = { 0.0f, 0.0f, 0.0f, 0.0f };

	public ARElement(ARMesh m) {
		_mesh = m;
	}

	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(_posXYZ[0], _posXYZ[1], _posXYZ[2]);
		gl.glRotatef(_rotXYZ[0], 1, 0, 0);
		gl.glRotatef(_rotXYZ[1], 0, 1, 0);
		gl.glRotatef(_rotXYZ[2], 0, 0, 1);
		gl.glColor4f(_color[0], _color[1], _color[2], _color[3]);
		_mesh.draw(gl);
		gl.glPopMatrix();
	}

}
