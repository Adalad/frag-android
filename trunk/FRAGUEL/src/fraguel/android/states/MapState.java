package fraguel.android.states;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
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

public class MapState extends State implements OnTouchListener{

	// Singleton
	private static MapState mapInstance;
	private boolean isMyPosition;
	private MyPositionOverlay me;

	// Variables de los botones del men
	private static final int MAPSTATE_MENU_CHANGEMAP = 1;
	private static final int MAPSTATE_MENU_BACKMENU = 2;
	private static final int MAPSTATE_MENU_EXIT = 3;
	private static final int MAPSTATE_MENU_MY_POSITION = 4;
	private static final int MAPSTATE_MENU_EXPLORE_MAP = 5;
	private static final int MAPSTATE_MENU_COMPASS=6;


	public static final int STATE_ID = 2;

	private MapController mapControl;
	private MapView mapView;
	private View popupView;
	private List<Overlay> mapOverlays;


	public MapState() {
		super();
		id = STATE_ID;
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
		mapView.setClickable(true);
		mapView.setEnabled(true);

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
		isMyPosition=true;


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

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
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
		me = new MyPositionOverlay(FRAGUEL.getInstance().getApplicationContext(),mapView);
		mapOverlays.add(me);

	}

	//****************************************************************************************
	//****************************************************************************************
	private class MyPositionOverlay extends MyLocationOverlay{

		public MyPositionOverlay(Context context, MapView mapView) {
			super(context, mapView);
			this.disableCompass();
			this.enableMyLocation();
			// TODO Auto-generated constructor stub
		}

		@Override
		public synchronized void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			super.onLocationChanged(location);
			if (isMyPosition)
				mapControl.animateTo(getMyLocation());
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			super.onProviderDisabled(provider);
		}

	}

	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		//Borramos el menu de opciones anterior
		menu.clear();
		//Añadimos las opciones del menu
		menu.add(0, MAPSTATE_MENU_CHANGEMAP, 0, R.string.mapstate_menu_changemap).setIcon(R.drawable.change_map_icon);
		menu.add(0, MAPSTATE_MENU_BACKMENU, 0, R.string.mapstate_menu_backmenu).setIcon(R.drawable.geotaging);
		menu.add(0, MAPSTATE_MENU_EXIT, 0,R.string.menu_exit).setIcon(R.drawable.exit);
		menu.add(0, MAPSTATE_MENU_MY_POSITION, 0,R.string.mapstate_menu_my_position).setIcon(R.drawable.my_location_icon);
		menu.add(0, MAPSTATE_MENU_EXPLORE_MAP, 0,R.string.mapstate_menu_explore_map).setIcon(R.drawable.explore_map_icon);
		menu.add(0, MAPSTATE_MENU_COMPASS, 0,R.string.mapstate_menu_compass).setIcon(R.drawable.compass_icon);

		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		//Añadimos los eventos del menu de opciones
		switch (item.getItemId()) {

		case MAPSTATE_MENU_CHANGEMAP:
			if(!mapView.isSatellite()){
			   mapView.setSatellite(true);
			   //mapView.setStreetView(false);
			}
			else{
				mapView.setSatellite(false);
				//mapView.setStreetView(true);
			
			}
			return true;

		case MAPSTATE_MENU_BACKMENU:
			FRAGUEL.getInstance().changeState(MenuState.STATE_ID);
			return true;

		case MAPSTATE_MENU_EXIT:
			System.exit(0);
			return true;
		case MAPSTATE_MENU_EXPLORE_MAP:
			isMyPosition=false;
			return true;
		case MAPSTATE_MENU_MY_POSITION:
			isMyPosition=true;
			return true;
			
		case MAPSTATE_MENU_COMPASS:
			if (me.isCompassEnabled())
				me.disableCompass();
			else
				me.enableCompass();
			return true;
		}

		return false;
	}



}
