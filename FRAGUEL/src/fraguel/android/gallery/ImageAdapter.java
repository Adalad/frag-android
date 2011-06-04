package fraguel.android.gallery;

import java.io.File;
import java.util.ArrayList;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.resources.ResourceManager;
import fraguel.android.states.RouteManagerState;
import fraguel.android.threads.ImageDownloadingThread;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    /*private Integer[] mImageIds = {
            R.drawable.guerracivil_1,
            R.drawable.guerracivil_2,
            R.drawable.guerracivil_3,
            R.drawable.guerracivil_4,
    };*/
    private ArrayList<String> urls=new ArrayList<String>();

    public ImageAdapter(Context c) {
        mContext = c;
        TypedArray a = FRAGUEL.getInstance().obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
    	return urls.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public void setData(ArrayList<String> data){
    	urls=data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	ImageView i;
    	if (convertView==null){
    		i = new ImageView(mContext);
	        
	        i.setLayoutParams(new Gallery.LayoutParams(200, 150));
	        i.setScaleType(ImageView.ScaleType.FIT_XY);
	        i.setBackgroundResource(mGalleryItemBackground);
        
    	}else {
            i = (ImageView) convertView;
        }
    	
	    	String path="";
	    	path=ResourceManager.getInstance().getRootPath()+"/tmp/"+"route"+Integer.toString(FRAGUEL.getInstance().getCurrentState().getRoute().id)+"point"+Integer.toString(FRAGUEL.getInstance().getCurrentState().getPointOI().id)+"images"+position+".png";
	    	
	    	File f= new File(path);
			if (f.exists()){
				Bitmap bmp = BitmapFactory.decodeFile(path);
				i.setImageBitmap(bmp);
			}else{
				i.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));				
			}
    	

        return i;
    }
}