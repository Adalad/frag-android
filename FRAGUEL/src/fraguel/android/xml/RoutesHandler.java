package fraguel.android.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.Route;

public class RoutesHandler extends DefaultHandler {

	private boolean _in_nametag;
	private boolean _in_descriptiontag;
	private boolean _in_icontag;

	private ArrayList<Route> _routes;
	private Route _currentRoute;

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		_in_nametag = false;
		_in_descriptiontag = false;
		_in_icontag = false;

		_routes = new ArrayList<Route>();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (_in_nametag) {
			_currentRoute.name = new String(ch, start, length);
		} else if (_in_descriptiontag) {
			_currentRoute.description = new String(ch, start, length);
		} else if (_in_icontag) {
			_currentRoute.icon = new String(ch, start, length);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("route")) {
			_currentRoute = new Route();
			_currentRoute.id = Integer.parseInt(attributes.getValue("id"));
		} else if (localName.equals("name")) {
			_in_nametag = true;
		} else if (localName.equals("description")) {
			_in_descriptiontag = true;
		} else if (localName.equals("icon")) {
			_in_icontag = true;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("route")) {
			_routes.add(_currentRoute);
		} else if (localName.equals("name")) {
			_in_nametag = false;
		} else if (localName.equals("description")) {
			_in_descriptiontag = false;
		} else if (localName.equals("icon")) {
			_in_icontag = false;
		}
	}

	public ArrayList<Route> getParsedData() {
		return _routes;
	}
}