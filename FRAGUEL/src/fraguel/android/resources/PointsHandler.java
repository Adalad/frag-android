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