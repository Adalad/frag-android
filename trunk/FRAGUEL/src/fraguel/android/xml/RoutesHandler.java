package fraguel.android.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fraguel.android.Route;

public class RoutesHandler extends DefaultHandler {

	private boolean in_nametag;
	private boolean in_descriptiontag;

	private ArrayList<Route> routes;
	private Route currentRoute;

	public void endDocument() throws SAXException {
	}

	public void startDocument() throws SAXException {
		in_nametag = false;
		in_descriptiontag = false;

		routes = new ArrayList<Route>();
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (in_nametag) {
			currentRoute.name = new String(ch, start, length);
		} else if (in_descriptiontag) {
			currentRoute.description = new String(ch, start, length);
		}
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("route")) {
			currentRoute = new Route();
			currentRoute.id = Integer.parseInt(attributes.getValue("id"));
		} else if (localName.equals("name")) {
			in_nametag = true;
		} else if (localName.equals("description")) {
			in_descriptiontag = true;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("route")) {
			routes.add(currentRoute);
		} else if (localName.equals("name")) {
			in_nametag = false;
		} else if (localName.equals("description")) {
			in_descriptiontag = false;
		}
	}

	public ArrayList<Route> getParsedData() {
		return routes;
	}
}