package fraguel.android.states;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.maps.MapItemizedOverlays;

public class MapState extends State{
	
	// Singleton
	private static MapState mapInstance;
	
	//public static final int STATE_ID = 2;
	
	private MapController mapControl;
	private MapView mapView;
	private View popupView;
	private List<Overlay> mapOverlays;
	

	public MapState() {
		super();
		id = 2;
		// Singleton
		mapInstance = this;
	}

	public static MapState getInstance() {
		if (mapInstance == null)
			mapInstance = new MapState();
		return mapInstance;
	}
	
	
	@Override
	public void load() {
		//Creamos e importamos el layout del xml
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.maingooglemaps,  null);
		FRAGUEL.getInstance().addView(viewGroup);

		//Creamos e importamos el popup del xml
		popupView= li.inflate(R.layout.popup,  null);
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//popupView.setLayoutParams(params);
		
		
		//Creamos, importamos y configuramos la mapview del xml
		mapView = (MapView) FRAGUEL.getInstance().findViewById(R.id.mapview);
		//mapView.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		//mapView.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());
		mapView.setBuiltInZoomControls(true);
		mapControl = mapView.getController();
		GeoPoint pointInit = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7267881 * 1000000));
		mapControl.setZoom(15);
		mapControl.setCenter(pointInit);

		//Creamos los Overlays
		mapOverlays = mapView.getOverlays();
		Drawable drawable = FRAGUEL.getInstance().getResources().getDrawable(R.drawable.museumsalango);
		MapItemizedOverlays itemizedoverlay = new MapItemizedOverlays(drawable,FRAGUEL.getInstance());

		//primer punto
		GeoPoint point1 = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7257881 * 1000000));
		OverlayItem overlayitem = new OverlayItem(point1, "Facultad A", "En el año...");

		itemizedoverlay.addOverlay(overlayitem);

		//segundo punto
		GeoPoint point2 = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7297881 * 1000000));
		OverlayItem overlayitem2 = new OverlayItem(point2, "Facultad B", "En el año...");
	
		itemizedoverlay.addOverlay(overlayitem2);
		
		//mi casa
		
		GeoPoint point3 = new GeoPoint((int) (40.44929 * 1000000), (int) (-3.64072927 * 1000000));
		OverlayItem overlayitem3 = new OverlayItem(point3, "Mi casa", "My house...");
		
		itemizedoverlay.addOverlay(overlayitem3);

		mapOverlays.add(itemizedoverlay);
		addMyPosition();
	
		
	}

	@Override
	public void onClick(View v) {
		
		FRAGUEL.getInstance().getView().removeView(popupView);
		
		switch (v.getId()) {
		case R.id.btn_popupPI_info:
			FRAGUEL.getInstance().changeState(6);
			break;
		case R.id.btn_popupPI_photo:
			FRAGUEL.getInstance().changeState(4);
			break;
		case R.id.btn_popupPI_video:
			FRAGUEL.getInstance().changeState(3);
			break;
		case R.id.btn_popupPI_ar:
			FRAGUEL.getInstance().changeState(5);
			break;
		
		default:
			
		}
		
		
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		FRAGUEL.getInstance().getView().removeView(popupView);
		
		return true;
	}

	
	public MapView getMapView() {
		return mapView;
	}
	
	public View getPopupView() {
		return popupView;
	}
	
	public void animateTo(GeoPoint g){
		mapControl.animateTo(g);		
	}
	
	private void addMyPosition(){
		final MyLocationOverlay me = new MyLocationOverlay(FRAGUEL.getInstance().getApplicationContext(),mapView);
		mapOverlays.add(me);
		me.enableMyLocation();
		me.disableCompass();
        me.runOnFirstFix(new Runnable() {
            public void run() {
                mapControl.animateTo(me.getMyLocation());
            }
        });
		
	}
	
	public void LocationChanged(GeoPoint p){
		
		mapControl.animateTo(p);
		
	}

}
