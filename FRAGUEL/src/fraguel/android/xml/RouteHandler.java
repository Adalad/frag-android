package fraguel.android.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RouteHandler extends DefaultHandler {

	private boolean in_routetag;
	private boolean in_nametag;
	private boolean in_descriptiontag;

	@Override
	public void endDocument() throws SAXException {
		// TODO Do nothing
		super.endDocument();
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO remove super
		super.startDocument();
		in_routetag = false;
		in_nametag = false;
		in_descriptiontag = false;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO remove super
		super.characters(ch, start, length);
		if (in_routetag)
			;
		// TODO do something
		else if (in_nametag)
			;
		// TODO do something
		else if (in_descriptiontag)
			;
		// TODO do something
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO remove super
		super.endElement(uri, localName, qName);
		if (localName.equals("route"))
			in_routetag = false;
		// TODO add route to list
		else if (localName.equals("name"))
			in_nametag = false;
		else if (localName.equals("description"))
			in_descriptiontag = false;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO remove super
		super.startElement(uri, localName, qName, attributes);
		if (localName.equals("route"))
			in_routetag = true;
		// TODO initialize list
		else if (localName.equals("name"))
			in_nametag = true;
		else if (localName.equals("description"))
			in_descriptiontag = true;
	}

	public void getParsedData() {
		// TODO return parsed class
	}
}