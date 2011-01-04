package fraguel.android.xml;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.ar.ARMesh;

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
			root = "/sdcard/Fraguel/";
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

	public void setRoot(String r) {
		root = r;
	}

	public ArrayList<Route> readRoutes(String path) {
		try {
			RoutesHandler rh = new RoutesHandler();
			parser.setContentHandler(rh);
			parser.parse(root + path);
			return rh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
		return null;
	}

	public ArrayList<PointOI> readPointsOI(String path) {
		try {
			PointsHandler ph = new PointsHandler();
			parser.setContentHandler(ph);
			parser.parse(root + path);
			return ph.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
		return null;
	}

	public ARMesh readARMesh(String path) {
		try {
			MeshHandler mh = new MeshHandler();
			parser.setContentHandler(mh);
			parser.parse(root + path);
			return mh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
		return null;
	}

}
