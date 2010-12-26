package fraguel.android.gallery;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.R;

public class BigImageAdapter extends BaseAdapter{
    int mGalleryItemBackground;
    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.guerracivil_1,
            R.drawable.guerracivil_2,
            R.drawable.guerracivil_3,
            R.drawable.guerracivil_4,
          
    };

    public BigImageAdapter(Context c) {
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
               
        Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth(); 
        int height= display.getHeight();
        
        int currentImageWidth=i.getDrawable().getIntrinsicWidth();
        int currentImageHeight=i.getDrawable().getIntrinsicHeight();
        int imageWidth = 0;
        int imageHeight = 0;

        if (currentImageHeight < currentImageWidth) {
            imageWidth = width;
            imageHeight =currentImageHeight * imageWidth / currentImageWidth;
        }
        else {
            imageHeight = height;
            imageWidth = currentImageWidth * imageHeight/ currentImageHeight;
                }
        
        i.setLayoutParams(new Gallery.LayoutParams(width,height));
        //i.setScaleType(ImageView.ScaleType.CENTER);
        i.setBackgroundResource(mGalleryItemBackground);
        return i;
    }
    
}
