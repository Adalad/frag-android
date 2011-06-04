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

	private String[] urls;
	private String name;
	private int index;
	private URLConnection conn;
	private InputStream is;
	private BufferedInputStream bis;
	private URL aURL;
	private Bitmap bm,tmp;
	private File f ;
	public ImageDownloadingThread(String[] paths,String n,int indice){
		super();
		urls=paths;
		name=n;
		tmp=null;
		f=null;
		index=indice;
	}
	public ImageDownloadingThread(String[] paths,String n){
		super();
		urls=paths;
		name=n;
		tmp=null;
		f=null;
		index=0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		String absolutePath;
		for (String url: urls){
				if (urls.length==1)
					absolutePath=ResourceManager.getInstance().getRootPath()+"/tmp/"+name+".png";
				else
					absolutePath=ResourceManager.getInstance().getRootPath()+"/tmp/"+name+i+".png";
				
				f = new File(absolutePath);
				
				
				if (!f.exists()&& url!=null){
					
					try{
						tmp=getImageBitmap(url);
						FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);
		
						BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
		
						tmp.compress(CompressFormat.PNG, 50, bos);
		
						bos.flush();
		
						bos.close();
		
				        Message m = new Message();
						m.arg2 = index;
						FRAGUEL.getInstance().imageHandler.sendMessage(m);
						i++;
					}catch(Exception e){
						f.delete();
					}
		
				}else if (f.exists()){
		
			        Message m = new Message();
					m.arg2 = index;
					FRAGUEL.getInstance().imageHandler.sendMessage(m);
				}
		
		}
		
		
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
