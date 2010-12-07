package fraguel.android.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlays extends ItemizedOverlay implements OnClickListener{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext=null;
	private MediaPlayer mp;
	private Activity act;
	
	public MapItemizedOverlays(Drawable arg0) {
		super(boundCenterBottom(arg0));
		// TODO Auto-generated constructor stub
	}
	
	public MapItemizedOverlays(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		  mContext = context;
		  //Reproducción de audio y video
	     // mp = MediaPlayer.create(mContext, R.raw.hefilmstheclouds);
		    //mp= new MediaPlayer();
		    //mp.setDataSource("http://www.youtube.com/watch?v=oDc-1zfffMw");
			//mp.prepare();
			   		
		}
	
	public MapItemizedOverlays(Drawable defaultMarker, Activity actividad) {
		super(boundCenterBottom(defaultMarker));
		  act = actividad;
		  mContext= actividad.getApplicationContext();	
		 // mp = MediaPlayer.create(mContext, R.raw.hefilmstheclouds);
		}

	@Override
	protected OverlayItem createItem(int arg0) {
		// TODO Auto-generated method stub
		return mOverlays.get(arg0);

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		 return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	protected boolean onTap(int index) {
			
	  OverlayItem item = mOverlays.get(index);
	  
	  Toast t= Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT);
	  t.show();
	  

	  if(item.getTitle()=="Facultad A"){ 
		  mp.start();
		  //videoPlayer("/PruebasMapas/res/raw","video1.avi", true);
		 /* 
		  Button b= new Button(mContext);
		  b.setId(1);
		  b.setText("Salir");
		  b.setOnClickListener((OnClickListener) act);
		  TransparentPanel tp= new TransparentPanel(mContext);
		  tp.addView(b);
		 // this.act.setContentView(tp);
		  ((MiMapActivity)this.act).getMapView().addView(tp) ;
		  ((MiMapActivity)this.act).getMapView().bringChildToFront(tp);*/
		  
		  
		//  TransparentOverlay to= new TransparentOverlay(new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7257881 * 1000000)), (MiMapActivity) act);
		//  ((MiMapActivity) this.act).getMapView().getOverlays().add(to);
		  
		  
		 
		  
	  }
	  
	  if(item.getTitle()=="Facultad B"){
		  mp.pause();
		  videoPlayer("/PruebasMapas/res/raw","video1.avi", false);
	  }



//	  Point point = ((MiMapActivity) act).getMapView().getProjection().toPixels(item.getPoint(), null);
	  //height=((Drawable) item).getIntrinsicHeight();
	  //width=marker.getIntrinsicWidth();
	  LayoutInflater li=  act.getLayoutInflater();
//	  View popup= li.inflate(R.layout.popup,  null);
	  LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//	  popup.setLayoutParams(params);
	  //popup.layout(point.x, point.y ,point.x, point.y);
//	  ((TextView) popup.findViewById(R.id.popupPI_texto1)).setText(item.getTitle());
//	  ((MiMapActivity) act).getMapView().addView(popup);
	 
	  

	  /* AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();*/
	  return true;
	  
	 
	}
	
		
	/* public boolean onTouchEvent(MotionEvent event, MapView mapView) 
	    {   
			//cuando levante el dedo
	        if (event.getAction() == 1) {                
	            GeoPoint p = mapView.getProjection().fromPixels(
	                (int) event.getX(),
	                (int) event.getY());
	
	            Geocoder geoCoder = new Geocoder(
	                mContext, Locale.getDefault());
	            try {
	                List<Address> addresses = geoCoder.getFromLocation(
	                    p.getLatitudeE6()  / 1E6, 
	                    p.getLongitudeE6() / 1E6, 1);
	
	                String add = "";
	                if (addresses.size() > 0) 
	                {
	                    for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
	                         i++)
	                       add += addresses.get(0).getAddressLine(i) + "\n";
	                }
	
	               Toast.makeText(mContext, add, Toast.LENGTH_SHORT).show();
	                
	            }
	            catch (IOException e) {                
	                e.printStackTrace();
	            }   
	            //Toast.makeText(mContext, p.getLatitudeE6()+" " + p.getLongitudeE6(), Toast.LENGTH_LONG).show();
	            return true;
	        }
	        else            
	            return false;
	    }   */ 
	
	
	public void videoPlayer(String path, String fileName, boolean autoplay){
		//get current window information, and set format, set it up differently, if you need some special effects
		act.getWindow().setFormat(PixelFormat.TRANSLUCENT);
		//the VideoView will hold the video
		VideoView videoHolder = new VideoView(act);
		//MediaController is the ui control howering above the video (just like in the default youtubeplayer).
		videoHolder.setMediaController(new MediaController(act));
		//assing a video file to the video holder
		videoHolder.setVideoURI(Uri.parse(path+"/"+fileName));
		//get focus, before playing the video.
		videoHolder.requestFocus();
		if(autoplay){
			videoHolder.start();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}


/*
public void audioPlayer(String path, String fileName){
	//set up MediaPlayer    
	MediaPlayer mp = new MediaPlayer();

	try {
		mp.setDataSource(path+"/"+fileName);
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		mp.prepare();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	mp.start();
}


public void videoPlayer(String path, String fileName, boolean autoplay){
	//get current window information, and set format, set it up differently, if you need some special effects
	getWindow().setFormat(PixelFormat.TRANSLUCENT);
	//the VideoView will hold the video
	VideoView videoHolder = new VideoView(this);
	//MediaController is the ui control howering above the video (just like in the default youtube
	player).
	videoHolder.setMediaController(new MediaController(this));
	//assing a video file to the video holder
	videoHolder.setVideoURI(Uri.parse(path+"/"+fileName));
	//get focus, before playing the video.
	videoHolder.requestFocus();
	if(autoplay){
		videoHolder.start();
	}

}

 
 
 */
 