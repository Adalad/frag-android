package fraguel.android.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

public class ResourceParser {

	private static ResourceParser instance;
	private XMLReader parser;
	private String root;

	private ResourceParser() {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp;
			sp = spf.newSAXParser();
			parser = sp.getXMLReader();
			root = "/";
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al inicializar el lector XML");
		}
	}

	public static ResourceParser getInstance() {
		if (null == instance)
			instance = new ResourceParser();
		return instance;
	}

	public void sestRoot(String r) {
		root = r;
	}

	public void readRoutes() {
		try {
			RoutesHandler rh = new RoutesHandler();
			parser.setContentHandler(rh);
			parser.parse(root + "path");
			rh.getParsedData();
			PointsHandler ph = new PointsHandler();
			parser.setContentHandler(ph);
			parser.parse(root + "path");
			ph.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
	}

}
