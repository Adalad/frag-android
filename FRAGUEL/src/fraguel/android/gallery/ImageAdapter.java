package fraguel.android.gallery;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
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
            
    };
}



/*public class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.guerracivil_1,
            R.drawable.guerracivil_2,
            R.drawable.guerracivil_3,
            R.drawable.guerracivil_4,
    };

    public ImageAdapter(Context c) {
        mContext = c;
        TypedArray a = FRAGUEL.getInstance().obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[position]);
        i.setLayoutParams(new Gallery.LayoutParams(200, 150));
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        i.setBackgroundResource(mGalleryItemBackground);

        return i;
    }
}*/