package fraguel.android.states;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.lists.InfoPointAdapter;
import fraguel.android.utils.TitleTextView;

public class PointInfoState extends State{
	public static final int STATE_ID = 20;
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	private GridView gridView;
	private TitleTextView title;
	private ImageView image;
	private TextView text;
	
	
	public PointInfoState() {
		super();
		id = STATE_ID;
	}
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		LinearLayout container= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		container.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		container.setOrientation(LinearLayout.VERTICAL);
		//container.setBackgroundResource(R.drawable.aqua);
		
		Display display = ((WindowManager)FRAGUEL.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int height = display.getHeight();
        int width = display.getWidth();
        
        int heightAvailable= height-2*TitleTextView.HEIGHT;
        
        heightAvailable=heightAvailable/2;

		title= new TitleTextView(FRAGUEL.getInstance().getApplicationContext());
		title.setText("Aquí va el título del punto de interés");
		container.addView(title);
		
		
		image= new ImageView(FRAGUEL.getInstance().getApplicationContext());
		image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,heightAvailable ));
		
		
		image.setImageBitmap(getImageBitmap("http://www.navegabem.com/blog/wp-content/uploads/2009/04/firefox-icon.png"));
		image.setPadding(10, 10, 10, 10);
		image.setAdjustViewBounds(true);
		
		container.addView(image);
		
		
		ScrollView sv = new ScrollView (FRAGUEL.getInstance().getApplicationContext());
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,heightAvailable-40));
		text= new TextView(FRAGUEL.getInstance().getApplicationContext());
		text.setText("Aqui va el texto referente a la mínima explicación del punto");
		sv.addView(text);
		container.addView(sv);
		
		gridView=new GridView(FRAGUEL.getInstance().getApplicationContext());
		gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		gridView.setNumColumns(3);
		gridView.setColumnWidth(width/3);
		gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gridView.setAdapter(new InfoPointAdapter(FRAGUEL.getInstance().getApplicationContext()));
		gridView.setScrollContainer(false);
		setGridViewListener();
		container.addView(gridView);
		
	
		
        viewGroup=container;
        FRAGUEL.getInstance().addView(viewGroup);
		
	}
	
	
	
	
	public void setGridViewListener(){
		
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				switch (position){
				

				case 0:

					FRAGUEL.getInstance().changeState(ImageState.STATE_ID);
					break;
					
				case 1:

					FRAGUEL.getInstance().changeState(VideoState.STATE_ID);
					break;
					
				case 2:

					FRAGUEL.getInstance().changeState(ARState.STATE_ID);
					break;
				
				}
			}});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void unload(){
		FRAGUEL.getInstance().getGPS().setDialogDisplayed(false);
		super.unload();
	}
	
	@Override
	public boolean loadData(Route route, PointOI point){
		boolean ok=super.loadData(route, point);
		
		if (ok){
			String titleText;
			titleText=route.name+" - "+point.title;
			title.setText(titleText);
		}
		return ok;
		
	}

	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
       } catch (IOException e) {
           Log.e("Image", "Error getting bitmap", e);
       }
       return bm;
    } 

}
