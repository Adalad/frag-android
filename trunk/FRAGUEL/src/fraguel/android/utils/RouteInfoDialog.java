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
import android.view.Display;
import android.view.WindowManager;
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
		
		Display display = ((WindowManager)FRAGUEL.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth(); 
        int height= display.getHeight();
		
        container.setLayoutParams(new LayoutParams(width-20,height-20));
        
		image = new ImageView(context);
		
		String path=ResourceManager.getInstance().getRootPath()+"/tmp/"+"route"+Integer.toString(r.id)+"icon"+".png";
		
		File f= new File(path);
		if (f.exists()){
			Bitmap bmp = BitmapFactory.decodeFile(path);
			image.setImageBitmap(bmp);
		}else{
			image.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
			String[] url = {r.icon};
			ImageDownloadingThread imageThread= new ImageDownloadingThread(url,"route"+Integer.toString(r.id)+"icon",ResourceManager.getInstance().getRootPath()+"/tmp/",1);
			imageThread.start();
		}
		int heightAvailable=height-20;
		int div=heightAvailable/3;
		image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,div ));		
		//image.setPadding(5, 5, 5, 5);
		image.setAdjustViewBounds(true);
		
		container.addView(image);
		
		ScrollView sv = new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		sv.setPadding(5, 5, 5, 5);
		
		
		TextView text = new TextView(context);
		text.setText(r.description);
		text.setPadding(5, 5, 5, 5);
		sv.addView(text);
		
		container.addView(sv);
		if(!FRAGUEL.getInstance().isTalking())
			FRAGUEL.getInstance().talk(r.description);
		
		
		this.setContentView(container);
	}
	
	public void imageLoaded(){
		String path=ResourceManager.getInstance().getRootPath()+"/tmp/"+"route"+Integer.toString(route.id)+"icon"+".png";
		Bitmap bmp = BitmapFactory.decodeFile(path);
		image.setImageBitmap(bmp);
		image.invalidate();
	}

	
}
