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

    public GridImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        //return mThumbIds.length;
    	return 0;
    }
  
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

       // imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
   /* private Integer[] mThumbIds = {
    		 R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,
             R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,
             R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
            R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,R.drawable.guerracivil_1,
             R.drawable.guerracivil_2,
             R.drawable.guerracivil_3,
             R.drawable.guerracivil_4,
            
    };*/
}

