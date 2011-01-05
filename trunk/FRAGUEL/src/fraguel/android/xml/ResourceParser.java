package fraguel.android.xml;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.ar.AREntity;
import fraguel.android.ar.ARMesh;

public class ResourceParser {

	private static ResourceParser _instance;
	private XMLReader _parser;
	private String _root;
	private HashMap<Integer, ARMesh> _meshes;

	private ResourceParser() {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp;
			sp = spf.newSAXParser();
			_parser = sp.getXMLReader();
			_root = "/sdcard/Fraguel/";
			_meshes = new HashMap<Integer, ARMesh>();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al inicializar el lector XML");
		}
	}

	public static ResourceParser getInstance() {
		if (null == _instance)
			_instance = new ResourceParser();
		return _instance;
	}

	public void setRoot(String r) {
		_root = r;
	}

	public ARMesh getMesh(int id) {
		return _meshes.get(id);
	}

	public ArrayList<Route> readRoutes(String path) {
		try {
			RoutesHandler rh = new RoutesHandler();
			_parser.setContentHandler(rh);
			_parser.parse(_root + path);
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
			_parser.setContentHandler(ph);
			_parser.parse(_root + path);
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
			_parser.setContentHandler(mh);
			_parser.parse(_root + path);
			return mh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
		return null;
	}

	public ArrayList<AREntity> readAR(String path) {
		try {
			// TODO Parse all meshes
			ARHandler arh = new ARHandler();
			_parser.setContentHandler(arh);
			_parser.parse(_root + path);
			return arh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			System.out.println("Error al leer el fichero de rutas");
		}
		return null;
	}

}
