package fraguel.android.utils;

import java.io.File;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.resources.ResourceManager;
import fraguel.android.states.MapState;
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
		
		String path="route"+Integer.toString(r.id)+"image";
		
		File f= new File(path);
		if (f.exists()){
			Bitmap bmp = BitmapFactory.decodeFile(path);
			image.setImageBitmap(bmp);
		}else{
			image.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
			String[] url = {r.icon};
			ImageDownloadingThread imageThread= new ImageDownloadingThread(url,"route"+Integer.toString(r.id)+"image",1);
			imageThread.start();
		}
		
		
		image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,120 ));		
		image.setPadding(5, 5, 5, 5);
		image.setAdjustViewBounds(true);
		image.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
		
		container.addView(image);
		
		ScrollView sv = new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,120));
		
		
		
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
