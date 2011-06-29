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
import fraguel.android.MinRouteInfo;
import fraguel.android.PointOI;
import fraguel.android.Route;

import fraguel.android.ar.AREntity;
import fraguel.android.ar.ARMesh;

public class XMLManager {

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
			Log.d("FRAGUEL", "Error", e);
		}
	}

	public void setRoot(final String root) {
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
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

	public ArrayList<AREntity> readAR(String path) {
		try {
			ARHandler arh = new ARHandler(this);
			_parser.setContentHandler(arh);
			_parser.parse(_root + path);
			return arh.getParsedData();
		} catch (Exception e) {
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

	public Route readRoute(final String fileName) {
		try {
			File routesFile = _root.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.equals("routes");
				}
			})[0].listFiles(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.equals(fileName + ".xml");
				}
			})[0];
			FileInputStream routesStream = new FileInputStream(routesFile);
			RouteHandler rh = new RouteHandler();
			_parser.setContentHandler(rh);
			_parser.parse(new InputSource(routesStream));
			return rh.getParsedData();
		} catch (Exception e) {
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

	public ArrayList<MinRouteInfo> readAvailableRoutes(final String fileName) {
		try {
			File routesFile = new File(this._root + "/" + fileName);
			FileInputStream routesStream = new FileInputStream(routesFile);
			MinRouteInfoHandler rh = new MinRouteInfoHandler();
			_parser.setContentHandler(rh);
			_parser.parse(new InputSource(routesStream));
			return rh.getParsedData();
		} catch (Exception e) {
			Log.d("FRAGUEL", "Error", e);
		}
		return null;
	}

}
