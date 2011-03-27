package fraguel.android.resources;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class ResourceManager {

	private static ResourceManager _instance;
	private boolean _initialized;

	private String _rootPath;

	private DownloadManager downloadManager;
	private XMLManager xmlManager;

	private ResourceManager() {
		_initialized = false;

		downloadManager=new DownloadManager("http://www.blackmesa.es/fraguel");
		xmlManager =new XMLManager();
	}

	public static ResourceManager getInstance() {
		if (_instance == null)
			_instance = new ResourceManager();

		return _instance;
	}
	
	private void createDirs(File rootSD) {
		rootSD.mkdir();
		new File(rootSD.getAbsolutePath() + "/ar").mkdir();
		new File(rootSD.getAbsolutePath() + "/config").mkdir();
		new File(rootSD.getAbsolutePath() + "/points").mkdir();
		new File(rootSD.getAbsolutePath() + "/routes").mkdir();
		new File(rootSD.getAbsolutePath() + "/tmp").mkdir();
		// TODO Create all the directories
	}

	public void initialize(final String root) {
		try {
			String state = Environment.getExternalStorageState();
			if (!Environment.MEDIA_MOUNTED.equals(state))
				throw new Exception("SD Card not avaliable");

			File sd = Environment.getExternalStorageDirectory();
			_rootPath = sd.getAbsolutePath() + "/" + root;

			if ((!sd.canRead()) || (!sd.canWrite()))
				throw new Exception("SD Card not avaliable");

			Log.d("FRAGUEL", "SD Card ready");
			
			File rootSD = new File(_rootPath);
			if (!rootSD.exists())
				createDirs(rootSD);
				
			xmlManager.setRoot(root);
			_initialized = true;
		} catch (Exception e) {
			// TODO Message asking for SD Card
			Log.d("FRAGUEL", "Error: " + e);
		}
	}

	public boolean isInitialized() {
		return _initialized;
	}

	public String getRootPath() {
		return _rootPath;
	}


	public DownloadManager getDownloadManager() {
		return downloadManager;
	}


	public XMLManager getXmlManager() {
		return xmlManager;
	}

}
