package fraguel.android.lists;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.states.PointInfoState;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class InfoPointAdapter extends BaseAdapter{

	private Context mContext;
	 private Integer[] mImageIds = {
	            R.drawable.guerracivil_1,
	            R.drawable.guerracivil_2,
	            R.drawable.guerracivil_3,
	            R.drawable.guerracivil_4,
	    };
	 private Integer[] mTitlesIds={};
	public InfoPointAdapter(Context c){
		mContext=c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageIds.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		Display display = ((WindowManager)FRAGUEL.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int height = display.getHeight();
        int width = display.getWidth();
		
		ImageView imageView;
        imageView = new ImageView(mContext);
        //int titleHeight=((PointInfoState) FRAGUEL.getInstance().getCurrentState()).getTitleWidth();
        imageView.setLayoutParams(new GridView.LayoutParams(width/2, (height-20)/2));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setPadding(10, 10, 10, 10);

        imageView.setImageResource(mImageIds[position]);
        return imageView;

	}

}
