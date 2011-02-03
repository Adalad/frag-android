package fraguel.android.resources;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.ar.ARElement;
import fraguel.android.ar.AREntity;

public class ARHandler extends DefaultHandler {

	private ArrayList<AREntity> _entities;
	private AREntity _currentEntity;
	private ARElement _currentElement;

	private XMLManager _manager;

	ARHandler(XMLManager manager) {
		_manager = manager;
	}

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		_entities = new ArrayList<AREntity>();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("entity")) {
			_currentEntity = new AREntity(Integer.parseInt(attributes
					.getValue("id")));
		} else if (localName.equals("pos")) {
			_currentEntity._posXYZ[0] = Float.parseFloat(attributes
					.getValue("x"));
			_currentEntity._posXYZ[1] = Float.parseFloat(attributes
					.getValue("y"));
			_currentEntity._posXYZ[2] = Float.parseFloat(attributes
					.getValue("z"));
		} else if (localName.equals("rot")) {
			_currentEntity._rotXYZ[0] = Float.parseFloat(attributes
					.getValue("x"));
			_currentEntity._rotXYZ[1] = Float.parseFloat(attributes
					.getValue("y"));
			_currentEntity._rotXYZ[2] = Float.parseFloat(attributes
					.getValue("z"));
		} else if (localName.equals("element")) {
			_currentElement = new ARElement(_manager.getMesh(Integer
					.parseInt(attributes.getValue("mesh"))));
		} else if (localName.equals("pose")) {
			_currentElement._posXYZ[0] = Float.parseFloat(attributes
					.getValue("x"));
			_currentElement._posXYZ[1] = Float.parseFloat(attributes
					.getValue("y"));
			_currentElement._posXYZ[2] = Float.parseFloat(attributes
					.getValue("z"));
		} else if (localName.equals("rote")) {
			_currentElement._rotXYZ[0] = Float.parseFloat(attributes
					.getValue("x"));
			_currentElement._rotXYZ[1] = Float.parseFloat(attributes
					.getValue("y"));
			_currentElement._rotXYZ[2] = Float.parseFloat(attributes
					.getValue("z"));
		} else if (localName.equals("color")) {
			_currentElement._color[0] = Float.parseFloat(attributes
					.getValue("r"));
			_currentElement._color[1] = Float.parseFloat(attributes
					.getValue("g"));
			_currentElement._color[2] = Float.parseFloat(attributes
					.getValue("b"));
			_currentElement._color[3] = Float.parseFloat(attributes
					.getValue("a"));
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("entity")) {
			_entities.add(_currentEntity);
		} else if (localName.equals("element")) {
			_currentEntity._list.add(_currentElement);
		}
	}

	public ArrayList<AREntity> getParsedData() {
		return _entities;
	}
}