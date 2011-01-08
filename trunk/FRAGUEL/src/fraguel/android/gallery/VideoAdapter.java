package fraguel.android.gallery;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.VideoView;

public class VideoAdapter extends BaseAdapter{

	
	int mGalleryItemBackground;
	private Context mContext;
	private String[] videos={"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?gay_referee.3gp",
			"http://www.free-3gp-video.com/download.php?do-beer-not-drugs.3gp"};
	
	
	public VideoAdapter(Context c) {
		mContext = c;
        TypedArray a = FRAGUEL.getInstance().obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videos.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		VideoView video =(VideoView) FRAGUEL.getInstance().findViewById(R.id.VideoView);
		Uri uri = Uri.parse(videos[position]);
		video.setVideoURI(uri);
		
		return video;
	}

}
