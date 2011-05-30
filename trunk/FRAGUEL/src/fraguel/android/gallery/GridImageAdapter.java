package fraguel.android.gallery;

import java.io.File;
import java.util.ArrayList;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.resources.ResourceManager;
import fraguel.android.states.RouteManagerState;
import fraguel.android.threads.ImageDownloadingThread;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridImageAdapter extends BaseAdapter {
	    private Context mContext;
	    private ArrayList<String> urls=new ArrayList<String>();
	    private Integer[] mThumbIds = {
	    		 R.drawable.guerracivil_1, R.drawable.guerracivil_1, R.drawable.guerracivil_1, R.drawable.guerracivil_1,
	    		 R.drawable.guerracivil_1, R.drawable.guerracivil_1, R.drawable.guerracivil_1, R.drawable.guerracivil_1,
	    		 R.drawable.guerracivil_1, R.drawable.guerracivil_1, R.drawable.guerracivil_1, R.drawable.guerracivil_1
	    };

	    public GridImageAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return urls.size();
	    }

	   // public void setThumbIds(String[] s){
	  //  	this.mThumbIds
	   // 	imageView.se
	  //  }
	    
	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }
	    public void setData(ArrayList<String> url){
	    	urls=url;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	            //imageView.setAdjustViewBounds(true);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        
	       /* String path="";
	        path=ResourceManager.getInstance().getRootPath()+"/tmp/"+"route"+Integer.toString(FRAGUEL.getInstance().getCurrentState().getRoute().id)+"images"+position+".png";
	        
	        File f= new File(path);
			if (f.exists()){
				Bitmap bmp = BitmapFactory.decodeFile(path);
				imageView.setImageBitmap(bmp);
			}else{
				imageView.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
				ImageDownloadingThread thread = FRAGUEL.getInstance().getCurrentState().getImageThread();
				thread = new ImageDownloadingThread(urls.get(position),position,"route"+Integer.toString(FRAGUEL.getInstance().getCurrentState().getRoute().id)+"images"+position);
				thread.start();
					
			}*/
	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	    }

}

