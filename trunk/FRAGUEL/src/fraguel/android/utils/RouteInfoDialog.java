package fraguel.android.utils;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.resources.ResourceManager;
import fraguel.android.threads.ImageDownloadingThread;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RouteInfoDialog extends Dialog{

	private Route route;
	private ImageView image;
	public RouteInfoDialog(Context context,Route r) {
		super(context);
		// TODO Auto-generated constructor stub
		route=r;
		this.setTitle(r.name);
		LinearLayout container = new LinearLayout(context);
		container.setOrientation(LinearLayout.VERTICAL);
		
		image = new ImageView(context);
		ImageDownloadingThread imageThread= new ImageDownloadingThread(r.icon,1,"route"+Integer.toString(r.id)+"image");
		imageThread.start();
		image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,80 ));		
		image.setPadding(5, 5, 5, 5);
		image.setAdjustViewBounds(true);
		image.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
		
		container.addView(image);
		
		ScrollView sv = new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,60));
		
		
		
		TextView text = new TextView(context);
		text.setText(r.description);
		text.setPadding(5, 5, 5, 5);
		sv.addView(text);
		
		container.addView(sv);
		
		FRAGUEL.getInstance().talk(r.description);
		
		
		this.setContentView(container);
	}
	
	public void imageLoaded(){
		String path=ResourceManager.getInstance().getRootPath()+"/tmp/"+"route"+Integer.toString(route.id)+"image"+".png";
		Bitmap bmp = BitmapFactory.decodeFile(path);
		image.setImageBitmap(bmp);
		image.invalidate();
		
	}

	
}
