package fraguel.android.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.PointOI;

public class PointsHandler extends DefaultHandler {

	private boolean in_titletag;

	private ArrayList<PointOI> points;
	private PointOI currentPoint;

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		in_titletag = false;

		points = new ArrayList<PointOI>();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (in_titletag) {
			currentPoint.title = new String(ch, start, length);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("point")) {
			currentPoint = new PointOI();
			currentPoint.id = Integer.parseInt(attributes.getValue("id"));
		} else if (localName.equals("coords")) {
			currentPoint.coords[0] = Float.parseFloat(attributes.getValue("x"));
			currentPoint.coords[1] = Float.parseFloat(attributes.getValue("y"));
		} else if (localName.equals("title")) {
			in_titletag = true;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("point")) {
			points.add(currentPoint);
		} else if (localName.equals("title")) {
			in_titletag = false;
		}
	}

	public ArrayList<PointOI> getParsedData() {
		return points;
	}
}