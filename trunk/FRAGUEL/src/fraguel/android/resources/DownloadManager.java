package fraguel.android.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;

class DownloadManager {

	private String _serverURL;

	DownloadManager(String serverURL) {
		_serverURL = serverURL;
	}

	boolean downloadFromUrl(String fileURL, String fileName) {

		try {

			if (!ResourceManager.getInstance().isInitialized())
				throw new Exception("SD error");

			URL url = new URL(_serverURL + fileURL);
			File file = new File(ResourceManager.getInstance().getRootPath()
					+ fileName);
			long startTime = System.currentTimeMillis();

			Log.d("FRAGUEL", "Download begining");
			Log.d("FRAGUEL", "Download url:" + url);
			Log.d("FRAGUEL", "Downloaded file name:" + fileName);

			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
			Log.d("FRAGUEL", "Download ready in"
					+ ((System.currentTimeMillis() - startTime) / 1000)
					+ " sec");

			return true;
		} catch (Exception e) {
			// TODO Error pop-up
			Log.d("FRAGUEL", "Error: " + e);

			return false;
		}
	}

}
