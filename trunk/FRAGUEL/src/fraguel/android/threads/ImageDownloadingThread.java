package fraguel.android.threads;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.resources.ResourceManager;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;

public class ImageDownloadingThread extends Thread{

	private String url,name;
	private int index;
	private URLConnection conn;
	private InputStream is;
	private BufferedInputStream bis;
	private URL aURL;
	private Bitmap bm,tmp;
	public ImageDownloadingThread(String path,int imageIndex,String n){
		url=path;
		index=imageIndex;
		name=n;
		tmp=null;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String absolutePath=ResourceManager.getInstance().getRootPath()+"/tmp/"+name+".png";
		File f = new File(absolutePath);
		
		if (!f.exists()){
			tmp=getImageBitmap(url);
			try{
				FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);

				BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

				tmp.compress(CompressFormat.PNG, 100, bos);

				bos.flush();

				bos.close();
			}catch(Exception e){
				
			}

		}
		
        Message m = new Message();
		m.arg2 = index;
		FRAGUEL.getInstance().imageHandler.sendMessage(m);
		
		//bm.compress(CompressFormat.JPEG, , stream);
	}
	private Bitmap getImageBitmap(String url) {
        bm = null;
        try {
            aURL = new URL(url);
            conn = aURL.openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            
       } catch (IOException e) {
           Log.e("Image", "Error getting bitmap", e);
       }
       return bm;
    } 

}
