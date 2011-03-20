package fraguel.android.resources;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.PointOI;

public class PointsHandler extends DefaultHandler {

	private boolean _in_titletag;
	private boolean _in_icontag;
	private boolean _in_imagetag;
	private boolean _in_videotag;
	private boolean _in_artag;

	private ArrayList<PointOI> _points;
	private PointOI _currentPoint;

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		_in_titletag = false;
		_in_icontag  = false;
		_in_imagetag = false;
		_in_videotag = false;
		_in_artag = false;

		_points = new ArrayList<PointOI>();
	}

	public void characters(char[] ch, int start, int length)
	throws SAXException {
		if (_in_titletag) {
			_currentPoint.title = new String(ch, start, length);
		} else if (_in_icontag) {
			_currentPoint.icon = new String(ch, start, length);
		} else if (_in_imagetag) {
			_currentPoint.setImages( new String(ch, start, length));
		} else if (_in_videotag) {
			_currentPoint.video = new String(ch, start, length);
		} else if (_in_artag) {
			_currentPoint.ar = new String(ch, start, length);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("point")) {
			_currentPoint = new PointOI();
			_currentPoint.id = Integer.parseInt(attributes.getValue("id"));
		} else if (localName.equals("coords")) {
			_currentPoint.coords[0] = Float
			.parseFloat(attributes.getValue("x"));
			_currentPoint.coords[1] = Float
			.parseFloat(attributes.getValue("y"));
		} else if (localName.equals("title")) {
			_in_titletag = true;
		} else if (localName.equals("icon")) {
			_in_icontag = true;
		} else if (localName.equals("image")) {
			_in_imagetag = true;
		} else if (localName.equals("video")) {
			_in_videotag = true;
		} else if (localName.equals("ar")) {
			_in_artag = true;
		}
	}

	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		if (localName.equals("point")) {
			_points.add(_currentPoint);
		} else if (localName.equals("title")) {
			_in_titletag = false;
		} else if (localName.equals("icon")) {
			_in_icontag = false;
		}else if (localName.equals("image")) {
			_in_imagetag = false;
		}else if (localName.equals("video")) {
			_in_videotag = false;
		}else if (localName.equals("ar")) {
			_in_artag = false;
		}
	}

	public ArrayList<PointOI> getParsedData() {
		return _points;
	}
}


/*
 
 package fraguel.android.resources;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.PointOI;

public class PointsHandler extends DefaultHandler {

	private boolean _in_titletag;
	private boolean _in_icontag;

	private ArrayList<PointOI> _points;
	private PointOI _currentPoint;

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		_in_titletag = false;
		_in_icontag = false;

		_points = new ArrayList<PointOI>();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (_in_titletag) {
			_currentPoint.title = new String(ch, start, length);
		} else if (_in_icontag) {
			_currentPoint.icon = new String(ch, start, length);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("point")) {
			_currentPoint = new PointOI();
			_currentPoint.id = Integer.parseInt(attributes.getValue("id"));
		} else if (localName.equals("coords")) {
			_currentPoint.coords[0] = Float
					.parseFloat(attributes.getValue("x"));
			_currentPoint.coords[1] = Float
					.parseFloat(attributes.getValue("y"));
		} else if (localName.equals("title")) {
			_in_titletag = true;
		} else if (localName.equals("icon")) {
			_in_icontag = true;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("point")) {
			_points.add(_currentPoint);
		} else if (localName.equals("title")) {
			_in_titletag = false;
		} else if (localName.equals("icon")) {
			_in_icontag = false;
		}
	}

	public ArrayList<PointOI> getParsedData() {
		return _points;
	}
}
 
 
 
 
 
 
 */
 