package fraguel.android.maps;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.notifications.BackKeyNotification;
import fraguel.android.notifications.StartRouteNotification;
import fraguel.android.notifications.StopRouteNotification;
import fraguel.android.states.MapState;
import fraguel.android.threads.ImageDownloadingThread;

public class MapItemizedOverlays extends ItemizedOverlay  implements OnGestureListener{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private Activity act;
	private GestureDetector gestureDetector;
	private float[] results = new float[3];
	final CharSequence[] options = {"Abandonar ruta"};

	public MapItemizedOverlays(Drawable arg0) {
		super(boundCenterBottom(arg0));
		gestureDetector= new GestureDetector((OnGestureListener)this);
		// TODO Auto-generated constructor stub
	}

	public MapItemizedOverlays(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		gestureDetector= new GestureDetector((OnGestureListener)this);

	}

	public MapItemizedOverlays(Drawable defaultMarker, Activity actividad) {
		super(boundCenterBottom(defaultMarker));
		act = actividad;
		mContext= actividad.getApplicationContext();
		gestureDetector= new GestureDetector((OnGestureListener)this);


	}
	
	public ArrayList<OverlayItem> getOverlayItems(){
		return this.mOverlays;
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



	@Override
	protected boolean onTap(int index) {

		
		PointOverlay i= (PointOverlay) mOverlays.get(index);
		MapState.getInstance().loadData(i.getRoute(), i.getPointOI());


		//Sacamos el popup del punto de interés
		showPopup(i);
        
		return true;


	}

	
	
	public void showPopup(PointOverlay item){

		PointOverlay overlay= item;
		
		View popup= MapState.getInstance().getPopupPI();
		((TextView) popup.findViewById(R.id.popupPI_texto1)).setText(overlay.getTitle());
		
		ImageView v = ((ImageView) popup.findViewById(R.id.popupPI_imagen2));
		v.setAdjustViewBounds(true);
		

		
		MapState.getInstance().loadData(overlay.getRoute(), overlay.getPointOI());
		
		String path="route"+Integer.toString(overlay.getRoute().id)+"point"+overlay.getPointOI().id+"image";
		
		File f= new File(path);
		if (f.exists()){
			Bitmap bmp = BitmapFactory.decodeFile(path);
			v.setImageBitmap(bmp);
		}else{
			v.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
			ImageDownloadingThread t = MapState.getInstance().getImageThread();
			String[] url={overlay.getPointOI().icon};
			t= new ImageDownloadingThread(url,path,0);
			t.start();
		}
		
		
		
		popup.findViewById(R.id.btn_popupPI_info).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_photo).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_ar).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_video).setOnClickListener((OnClickListener) FRAGUEL.getInstance());

		
		boolean isPopup= false;
		for (int i=0 ; i<FRAGUEL.getInstance().getView().getChildCount(); i++){
			if (FRAGUEL.getInstance().getView().getChildAt(i).getId()== popup.getId())
				isPopup= true;
		}
		if (MapState.getInstance().isAnyPopUp()){
			MapState.getInstance().removePopUpOnRoute();
			MapState.getInstance().removePopUpPIOnRoute();
			MapState.getInstance().removePopUpPI();
		}
		if (!isPopup)
			MapState.getInstance().setPopupPI();

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		if (!MapState.getInstance().isContextMenuDisplayed()&&!FRAGUEL.getInstance().getGPS().isRouteMode()){
			GeoPoint point=MapState.getInstance().getMapView().getProjection().fromPixels((int)e.getX(),(int) e.getY());
			float latitude=(float)point.getLatitudeE6()/1000000;
			float longitude=(float)point.getLongitudeE6()/1000000;
			float distance=Float.MAX_VALUE;
			Route rTmp = null;
			PointOI pTmp = null;
			float[] results= new float[3];
			
			for (Route r : FRAGUEL.getInstance().routes){
				for (PointOI p: r.pointsOI){
					Location.distanceBetween(latitude,longitude, p.coords[0], p.coords[1], results);
					
					if (results[0]<distance){
						rTmp=r;
						pTmp=p;
						distance=results[0];
					}
					
				}
			}
			
			MapState.getInstance().loadData(rTmp, pTmp);
			MapState.getInstance().setContextMenuDisplayed(true);
			MapState.getInstance().options[1]="Desde: "+pTmp.title;
			FRAGUEL.getInstance().createDialog("Comenzar Ruta: "+rTmp.name, MapState.getInstance().options, new StartRouteNotification(), new BackKeyNotification());
			//MapState.getInstance().chooseStartPointDialog();
		}else if (!MapState.getInstance().isContextMenuDisplayed()){
			MapState.getInstance().setContextMenuDisplayed(true);
			FRAGUEL.getInstance().createDialog("¿Desea abandonar la ruta?", options, new StopRouteNotification(), new BackKeyNotification());
			//FRAGUEL.getInstance().openContextMenu(MapState.getInstance().getMapView());
		}
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub

		return false;
	}
	
	 @Override 
     public boolean onTouchEvent(MotionEvent event, MapView mapView) { 
               if(this.gestureDetector.onTouchEvent(event)) 
            	   return true; 
               else 
            	   return super.onTouchEvent(event, mapView); 
} 

	


	

}



 