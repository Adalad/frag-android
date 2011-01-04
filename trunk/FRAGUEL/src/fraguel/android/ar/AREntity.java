package fraguel.android.ar;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class AREntity {

	public int _id = 0;
	public float[] _posXYZ = { 0.0f, 0.0f, 0.0f };
	public float[] _rotXYZ = { 0.0f, 0.0f, 0.0f };
	public ArrayList<ARElement> _list;

	public AREntity(int i) {
		_id = i;
		_list = new ArrayList<ARElement>();
	}

	public void draw(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(_posXYZ[0], _posXYZ[1], _posXYZ[2]);
		gl.glRotatef(_rotXYZ[0], 1, 0, 0);
		gl.glRotatef(_rotXYZ[1], 0, 1, 0);
		gl.glRotatef(_rotXYZ[2], 0, 0, 1);
		for (ARElement e : _list) {
			e.draw(gl);
		}
		gl.glPopMatrix();
	}

	public void translate(float x, float y, float z) {
		_posXYZ[0] += x;
		_posXYZ[1] += y;
		_posXYZ[2] += z;
	}

	public void roll(float angle) {
		_rotXYZ[2] = (float) ((_rotXYZ[2] + angle) % (2 * Math.PI));
	}

	public void pitch(float angle) {
		_rotXYZ[0] = (float) ((_rotXYZ[0] + angle) % (2 * Math.PI));
	}

	public void yaw(float angle) {
		_rotXYZ[1] = (float) ((_rotXYZ[1] + angle) % (2 * Math.PI));
	}
}
