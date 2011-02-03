package fraguel.android.resources;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.ar.ARMesh;

public class MeshHandler extends DefaultHandler {

	private ARMesh _mesh;

	private int _id;
	private ShortBuffer _indexBuffer;
	private FloatBuffer _vertexBuffer;
	private int _nrOfVertices;
	private int _type;
	private short[] _indices;
	private float[] _vertices;
	private int _indicesI;
	private int _verticesI;

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		_indicesI = _verticesI = 0;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("mesh")) {
			_id = Integer.parseInt(attributes.getValue("id"));
		} else if (localName.equals("props")) {
			_nrOfVertices = Integer.parseInt(attributes.getValue("nvertices"));
			_type = Integer.parseInt(attributes.getValue("type"));
			// float has 4 bytes
			ByteBuffer vbb = ByteBuffer.allocateDirect(_nrOfVertices * 3 * 4);
			vbb.order(ByteOrder.nativeOrder());
			_vertexBuffer = vbb.asFloatBuffer();
			// short has 4 bytes
			ByteBuffer ibb = ByteBuffer.allocateDirect(_nrOfVertices * 2);
			ibb.order(ByteOrder.nativeOrder());
			_indexBuffer = ibb.asShortBuffer();
			// arrays
			_indices = new short[_nrOfVertices];
			_vertices = new float[3 * _nrOfVertices];
		} else if (localName.equals("index")) {
			_indices[_indicesI] = Short.parseShort(attributes.getValue("i"));
		} else if (localName.equals("vertex")) {
			_vertices[_verticesI] = Float.parseFloat(attributes.getValue("x"));
			_vertices[_verticesI + 1] = Float.parseFloat(attributes
					.getValue("y"));
			_vertices[_verticesI + 2] = Float.parseFloat(attributes
					.getValue("z"));
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("mesh")) {
			_mesh = new ARMesh(_id, _indexBuffer, _vertexBuffer, _nrOfVertices,
					_type);
		} else if (localName.equals("indexBuffer")) {
			_indexBuffer.put(_indices);
			_indexBuffer.position(0);
		} else if (localName.equals("index")) {
			_indicesI++;
		} else if (localName.equals("vertexBuffer")) {
			_vertexBuffer.put(_vertices);
			_vertexBuffer.position(0);
		} else if (localName.equals("vertex")) {
			_verticesI += 3;
		}
	}

	public ARMesh getParsedData() {
		return _mesh;
	}
}