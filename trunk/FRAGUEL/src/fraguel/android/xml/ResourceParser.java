package fraguel.android.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

public class ResourceParser {

	private XMLReader parser;

	public ResourceParser() {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp;
			sp = spf.newSAXParser();
			parser = sp.getXMLReader();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al inicializar el lector XML");
		}
	}

	public void readRoutes() {
		try {
			RoutesHandler rh = new RoutesHandler();
			parser.setContentHandler(rh);
			parser.parse("path");
			rh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
	}

}
