package fraguel.android.lists;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.states.PointInfoState;
import fraguel.android.utils.TitleTextView;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoPointAdapter extends BaseAdapter{

	private Context mContext;
	 private Integer[] mImageIds = {
	            R.drawable.informacion,
	            R.drawable.galeria_fotos,
	            R.drawable.galeria_video,
	            R.drawable.realidad_aumentada,
	    };
	 private Integer[] mTitlesIds={R.string.infopointstate_info,R.string.infopointstate_images,R.string.infopointstate_videos,R.string.infopointstate_ar};
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
		
        LinearLayout container= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
        container.setLayoutParams(new GridView.LayoutParams(width/2, (height-TitleTextView.HEIGHT)/2));
		container.setOrientation(LinearLayout.VERTICAL);
		container.setGravity(Gravity.CENTER);
		container.setPadding(10, 10, 10, 10);
        
		ImageView imageView;
        imageView = new ImageView(mContext);
        //imageView.setLayoutParams(new GridView.LayoutParams(width/2, (height-20)/2));
        imageView.setLayoutParams(new LayoutParams(60,60));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(mImageIds[position]);
        
        TextView t= new TextView(FRAGUEL.getInstance().getApplicationContext());
        t.setText(mTitlesIds[position]);
		t.setGravity(Gravity.CENTER_HORIZONTAL);
		t.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        
        container.addView(t);
		container.addView(imageView);
        
        
        return container;

	}

}
