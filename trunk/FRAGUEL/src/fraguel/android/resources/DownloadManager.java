package fraguel.android.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Log;
import android.webkit.URLUtil;

public class DownloadManager {

	private String _serverURL;

	DownloadManager(String serverURL) {
		_serverURL = serverURL;
	}
	
	
	public String downloadFromPath(String path) throws IOException {
		
		if (!URLUtil.isNetworkUrl(path)) {
			return path;
		} else {
			URL url = new URL(path);
			URLConnection cn = url.openConnection();
			cn.connect();
			InputStream stream = cn.getInputStream();
			if (stream == null)
				throw new RuntimeException("stream is null");
			File temp = File.createTempFile("fraguel/tmp/mediaplayertmp", "dat");
			temp.deleteOnExit();
			String tempPath = temp.getAbsolutePath();
			FileOutputStream out = new FileOutputStream(temp);
			byte buf[] = new byte[128];
			do {
				int numread = stream.read(buf);
				if (numread <= 0)
					break;
				out.write(buf, 0, numread);
			} while (true);
			try {
				stream.close();
			} catch (IOException ex) {
				Log.e("Getting data source", "error: " + ex.getMessage(), ex);
			}
			return tempPath;
		}
		
	}
	
	

	public boolean downloadFromUrl(String fileURL, String fileName) {

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
