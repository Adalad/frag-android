package fraguel.android.states;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.util.Pair;
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
import fraguel.android.IntroVideoActivity;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.VideoPlayer;
import fraguel.android.maps.MapItemizedOverlays;
import fraguel.android.maps.NextPointOverlay;
import fraguel.android.maps.RouteOverlay;
import fraguel.android.resources.ResourceManager;

public class MapState extends State implements OnTouchListener{

	// Singleton
	private static MapState mapInstance;
	private boolean isMyPosition;
	private MyPositionOverlay me;

	// Variables de los botones del men
	private static final int MAPSTATE_MENU_CHANGEMAP = 1;
	private static final int MAPSTATE_MENU_MY_POSITION = 2;
	private static final int MAPSTATE_MENU_EXPLORE_MAP = 3;
	private static final int MAPSTATE_MENU_COMPASS=4;
	private static final int MAPSTATE_MENU_BACKMENU = 5;


	public static final int STATE_ID = 2;

	private MapController mapControl;
	private MapView mapView;
	private View popupView;
	private View popupView2;
	private List<Overlay> mapOverlays;
	private boolean isPopup;
	private String[] videos={"http://daily3gp.com/vids/747.3gp",
			"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?gay_referee.3gp",
	"http://www.free-3gp-video.com/download.php?do-beer-not-drugs.3gp"};


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
		setPopup(false);
		popupView= li.inflate(R.layout.popup,  null);
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//popupView.setLayoutParams(params);
        popupView.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());
        
        //Creamos e importamos el popup onroute del xml
		setPopup(false);
		popupView2= li.inflate(R.layout.popup2,  null);
		popupView2.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());

		//Creamos, importamos y configuramos la mapview del xml
		mapView = (MapView) FRAGUEL.getInstance().findViewById(R.id.mapview);
		//mapView.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		//mapView.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());
		mapView.setTraffic(false);
		mapView.setBuiltInZoomControls(true);
		mapView.setClickable(true);
		mapView.setEnabled(true);
		
		mapView.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());

		mapControl = mapView.getController();
		GeoPoint pointInit = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7267881 * 1000000));
		mapControl.setZoom(15);
		mapControl.setCenter(pointInit);

		//Creamos los Overlays
		mapOverlays = mapView.getOverlays();
		Drawable drawable = FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_visited);
		MapItemizedOverlays itemizedoverlay = new MapItemizedOverlays(drawable,FRAGUEL.getInstance());
 
		FRAGUEL.getInstance().registerForContextMenu(mapView); 
		
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

		me = new MyPositionOverlay(FRAGUEL.getInstance().getApplicationContext(),mapView);

		isMyPosition=true;
		
		//Cargamos todo
		loadAllPoints();
		
	}

	public boolean isPopup() {
		return isPopup;
	}

	public void setPopup(boolean isPopup) {
		this.isPopup = isPopup;
	}

	@Override
	public void onClick(View v) {

		FRAGUEL.getInstance().getView().removeView(popupView);

		switch (v.getId()) {
		case R.id.btn_popupPI_info:
			FRAGUEL.getInstance().changeState(InfoState.STATE_ID);
			break;
		case R.id.btn_popupPI_photo:
			FRAGUEL.getInstance().changeState(ImageState.STATE_ID);
			break;
		case R.id.btn_popupPI_video:
			VideoState.getInstance().setVideoPath(videos[0]);
			//FRAGUEL.getInstance().showProgressDialog();
			//Intent lVideoIntent = new Intent(null, Uri.parse("ytv://w65_FH4X938"), FRAGUEL.getInstance(), IntroVideoActivity.class);
		    //FRAGUEL.getInstance().startActivity(lVideoIntent);
			FRAGUEL.getInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")));
			//VideoState.getInstance().setVideoPath("");
			//FRAGUEL.getInstance().changeState(VideoState.STATE_ID);
			break;
		case R.id.btn_popupPI_ar:
			FRAGUEL.getInstance().changeState(ARState.STATE_ID);
			break;

		default:
			

		}


	}

	@Override
	public boolean onTouch(View view, MotionEvent mev) {
		// TODO Auto-generated method stub
		if (view==popupView && isPopup()){
		  FRAGUEL.getInstance().getView().removeView(popupView);
		  setPopup(false);
		}
          
		if (view==popupView2 && isPopup()){
			  FRAGUEL.getInstance().getView().removeView(popupView2);
			  setPopup(false);
			}
   
		return true;
	}


	public MapView getMapView() {
		return mapView;
	}

	public View getPopupView() {
		return popupView;
	}
	
	public View getPopupView2() {
		return popupView2;
	}

	public void animateTo(GeoPoint g){
		mapControl.animateTo(g);		
	}
	
	public void refreshMapRouteMode(){
		mapOverlays.remove(2);
		mapOverlays.remove(3);
		mapOverlays.remove(4);
		addRouteOverlays();
	}
	
	
	private void addRouteOverlays(){
		Pair<Route,PointOI> info = null;
		int idroute;
		//pintamos las líneas
		mapOverlays.add(new RouteOverlay());
		//pintamos los ya visitados
		MapItemizedOverlays visited = new MapItemizedOverlays(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_visited),FRAGUEL.getInstance());
		
		for (Pair<Pair<Integer,Integer>, Pair<Float, Float>> point : FRAGUEL.getInstance().getGPS().getRoutePointsVisited()){
			info=FRAGUEL.getInstance().getRouteandPointbyId(point.first.first,point.first.second);
			visited.addOverlay(new OverlayItem(new GeoPoint((int)(point.second.first*1000000),(int)(point.second.second*1000000)), info.second.title, info.second.title));
		}
		idroute=info.first.id;
		mapOverlays.add(visited);
		
		//pintamos los no visitados
		visited= new MapItemizedOverlays(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_notvisited),FRAGUEL.getInstance());
		for (Pair<Integer, Pair<Float, Float>> point : FRAGUEL.getInstance().getGPS().getRoutePointsNotVisited()){
			info=FRAGUEL.getInstance().getRouteandPointbyId(idroute,point.first);
			visited.addOverlay(new OverlayItem(new GeoPoint((int)(point.second.first*1000000),(int)(point.second.second*1000000)), info.second.title, info.second.title));
		}
		mapOverlays.add(visited);
		
		
	}
	public void startRoute(){
		mapOverlays.clear();
		mapOverlays.add(me);
		mapOverlays.add(new NextPointOverlay());
		addRouteOverlays();
	}
	
	public void reStartMap(){
		mapOverlays.clear();
		loadAllPoints();
	}
	
	public void loadAllPoints(){
		//PUNTOS LEIDOS DE FICHERO
		Drawable image;
		MapItemizedOverlays capa;
		GeoPoint point;
		OverlayItem item;
		for (Route r : FRAGUEL.getInstance().routes) {
			if (r.icon==null)
				image=FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_notvisited);
			else
				image=FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_notvisited);
			
			capa=new MapItemizedOverlays(image,FRAGUEL.getInstance());
			//RouteOverlay o= new RouteOverlay(r);
			//mapOverlays.add(o);
			for (PointOI p : r.pointsOI) { 
				point=new GeoPoint((int)(p.coords[0]*1000000),(int)(p.coords[1]*1000000));
				item= new OverlayItem(point,p.title,p.title);
				capa.addOverlay(item);
			}
			mapOverlays.add(capa);
		}
		mapOverlays.add(me);
	}
	
	public GeoPoint getLocationFromOverlay(){
		return me.getMyLocation();
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
		menu.add(0, MAPSTATE_MENU_CHANGEMAP, 0, R.string.mapstate_menu_changemap).setIcon(R.drawable.ic_menu_mapmode);
		menu.add(0, MAPSTATE_MENU_MY_POSITION, 0,R.string.mapstate_menu_my_position).setIcon(R.drawable.ic_menu_mylocation);
		menu.add(0, MAPSTATE_MENU_EXPLORE_MAP, 0,R.string.mapstate_menu_explore_map).setIcon(R.drawable.ic_menu_search);
		menu.add(0, MAPSTATE_MENU_COMPASS, 0,R.string.mapstate_menu_compass).setIcon(R.drawable.ic_menu_compass);
		menu.add(0, MAPSTATE_MENU_BACKMENU, 0, R.string.mapstate_menu_backmenu).setIcon(R.drawable.ic_menu_home);

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

		case MAPSTATE_MENU_BACKMENU:
			FRAGUEL.getInstance().changeState(MenuState.STATE_ID);
			return true;
		}

		return false;
	}



}
