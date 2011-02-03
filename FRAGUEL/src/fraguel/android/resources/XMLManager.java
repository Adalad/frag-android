package fraguel.android.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.Environment;
import android.util.Log;
import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.ar.AREntity;
import fraguel.android.ar.ARMesh;

class XMLManager {

	private XMLReader _parser;
	private File _root;
	private HashMap<Integer, ARMesh> _meshes;

	XMLManager() {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp;
			sp = spf.newSAXParser();
			_parser = sp.getXMLReader();
			_root = null;
			_meshes = new HashMap<Integer, ARMesh>();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			Log.d("FRAGUEL", "Error", e);
		}
	}

	void setRoot(final String root) {
		File sd = Environment.getExternalStorageDirectory();
		_root = sd.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.equals(root);
			}
		})[0];
	}

	ARMesh getMesh(int id) {
		return _meshes.get(id);
	}

	public ArrayList<Route> readRoutes() {
		try {
			File routesFile = _root.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.equals("routes");
				}
			})[0].listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.equals("routes.xml");
				}
			})[0];
			FileInputStream routesStream = new FileInputStream(routesFile);
			RoutesHandler rh = new RoutesHandler();
			_parser.setContentHandler(rh);
			_parser.parse(new InputSource(routesStream));
			return rh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

	public ArrayList<PointOI> readPointsOI(final String fileName) {
		try {
			File pointsFile = _root.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.equals("routes");
				}
			})[0].listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.equals(fileName + ".xml");
				}
			})[0];
			FileInputStream pointsStream = new FileInputStream(pointsFile);
			PointsHandler ph = new PointsHandler();
			_parser.setContentHandler(ph);
			_parser.parse(new InputSource(pointsStream));
			return ph.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			Log.d("FRAGUEL", "Error", e);
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
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

	public ArrayList<AREntity> readAR(String path) {
		try {
			// TODO Parse all meshes
			ARHandler arh = new ARHandler(this);
			_parser.setContentHandler(arh);
			_parser.parse(_root + path);
			return arh.getParsedData();
		} catch (Exception e) {
			// TODO Show error pop-up
			// TODO Show language string
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

}
