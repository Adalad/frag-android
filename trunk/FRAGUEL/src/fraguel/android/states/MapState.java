package fraguel.android.states;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
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
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.maps.MapItemizedOverlays;
import fraguel.android.maps.NextPointOverlay;
import fraguel.android.maps.PointOverlay;
import fraguel.android.maps.RouteOverlay;
import fraguel.android.resources.ResourceManager;

public class MapState extends State implements OnTouchListener{

	// Singleton
	private static MapState mapInstance;
	private boolean isMyPosition;
	private MyPositionOverlay me=null;

	// Variables de los botones del men
	private static final int MAPSTATE_MENU_CHANGEMAP = 1;
	private static final int MAPSTATE_MENU_MY_POSITION = 2;
	private static final int MAPSTATE_MENU_EXPLORE_MAP = 3;
	private static final int MAPSTATE_MENU_COMPASS=4;
	private static final int MAPSTATE_MENU_BACKMENU = 5;
	private static final int MAPSTATE_MENU_DRAWPATH=6;
	


	public static final int STATE_ID = 2;

	private MapController mapControl;
	private MapView mapView;
	private View popupPI;
	private View popupPIonroute;
	private View popupOnRoute;
	private List<Overlay> mapOverlays;
	private boolean isPopupPI;
	private boolean isPopupOnRoute;
	private boolean isPopupPIOnRoute;
	private boolean isContextMenuDisplayed,chooseAnotherRoute,choosePoint,showWay=true;
	private Route routeContext;


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
		isPopupPI=false;
		popupPI= li.inflate(R.layout.popup,  null);
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//popupView.setLayoutParams(params);
        popupPI.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());
        
        //Creamos e importamos el popup onroute del xml
        isPopupPIOnRoute=false;
		popupPIonroute= li.inflate(R.layout.popup2,  null);
		popupPIonroute.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());
		
		//Creamos e importamos el popup onroute del xml
		isPopupOnRoute=false;
		popupOnRoute= li.inflate(R.layout.popup3,  null);
		popupOnRoute.setOnTouchListener((OnTouchListener) FRAGUEL.getInstance());
		

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
		
		if (me==null)
			me = new MyPositionOverlay(FRAGUEL.getInstance().getApplicationContext(),mapView);
		

		isMyPosition=true;
		
		//Cargamos todo
		if (!FRAGUEL.getInstance().getGPS().isRouteMode())
			this.reStartMap();
		else{
			mapControl.setCenter(me.getMyLocation());
			//refreshMapRouteMode();
		}
		
		
		FRAGUEL.getInstance().registerForContextMenu(mapView);
		isContextMenuDisplayed=false;
		chooseAnotherRoute=false;
		choosePoint=false;
		routeContext=null;

		
	}

	public boolean isPopupPI() {
		return isPopupPI;
	}
	public boolean isPopUpPIOnRoute(){
		return isPopupPIOnRoute;
	}
	public boolean isPopupOnRoute() {
		return isPopupOnRoute;
	}
	public boolean isAnyPopUp(){
		return isPopupPI || isPopupPIOnRoute || isPopupOnRoute;
	}

	public void setPopupPI() {
		FRAGUEL.getInstance().addView(popupPI);
		this.isPopupPI = true;
	}
	public void setPopupPIOnRoute() {
		FRAGUEL.getInstance().addView(popupPIonroute);
		this.isPopupPIOnRoute = true;
	}
	public void setPopupOnRoute() {
		FRAGUEL.getInstance().addView(popupOnRoute);
		this.isPopupOnRoute = true;
	}
	
	public void removePopUpPI(){
		FRAGUEL.getInstance().getView().removeView(popupPI);
		this.isPopupPI = false;
		 
	}
	public void removePopUpOnRoute(){
		FRAGUEL.getInstance().getView().removeView(popupOnRoute);
		this.isPopupOnRoute = false;
		 
	}
	public void removePopUpPIOnRoute(){
		FRAGUEL.getInstance().getView().removeView(popupPIonroute);
		this.isPopupPIOnRoute = false;
		 
	}
	
	@Override
	public void onClick(View v) {

		this.removePopUpPI();
		this.removePopUpOnRoute();
		this.removePopUpPIOnRoute();
		
		
		switch (v.getId()) {
		case R.id.btn_popupPI_info:
			FRAGUEL.getInstance().changeState(InfoState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(route, point);
			break;
		case R.id.btn_popupPI_photo:
			FRAGUEL.getInstance().changeState(ImageState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(route, point);
			break;
		case R.id.btn_popupPI_video:
			FRAGUEL.getInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(point.video)));
			break;
		case R.id.btn_popupPI_ar:
			FRAGUEL.getInstance().changeState(ARState.STATE_ID);
			break;
		case R.id.btn_popupPIonroute_moreinfo:
			FRAGUEL.getInstance().changeState(PointInfoState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(route, point);
			break;
		default:
			

		}


	}

	@Override
	public void imageLoaded(int index){
		if (index==0){
			String path=ResourceManager.getInstance().getRootPath()+"/tmp/"+"route"+Integer.toString(route.id)+"point"+point.id+"image"+".png";
			Bitmap bmp = BitmapFactory.decodeFile(path);
			((ImageView) popupPI.findViewById(R.id.popupPI_imagen2)).setImageBitmap(bmp);
			popupPI.invalidate();
		}
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent mev) {
		// TODO Auto-generated method stub
		if (view==popupPI && isPopupPI()){
		  removePopUpPI();

		}
          
		if (view==popupPIonroute && isPopUpPIOnRoute()){
			  removePopUpPIOnRoute();

			}
		if (view==popupOnRoute && isPopupOnRoute()){
			removePopUpOnRoute();

		}

		return true;
	}


	public MapView getMapView() {
		return mapView;
	}

	public View getPopupPI() {
		return popupPI;
	}
	
	public View getPopupPIonroute() {
		return popupPIonroute;
	}
	
	public View getPopupOnRoute() {
		return popupOnRoute;
	}

	public void animateTo(GeoPoint g){
		mapControl.animateTo(g);		
	}
	
	public void refreshMapRouteMode(){
		mapOverlays.clear();
		mapOverlays.add(me);
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
			visited.addOverlay(new PointOverlay(new GeoPoint((int)(point.second.first*1000000),(int)(point.second.second*1000000)), info.second.title, info.second.title,info.second,info.first));
		}

			idroute=FRAGUEL.getInstance().getGPS().getRouteId();
			
		if (visited.size()!=0)	
			mapOverlays.add(visited);
		
		//pintamos los no visitados
		visited= new MapItemizedOverlays(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_notvisited),FRAGUEL.getInstance());
		for (Pair<Integer, Pair<Float, Float>> point : FRAGUEL.getInstance().getGPS().getRoutePointsNotVisited()){
			info=FRAGUEL.getInstance().getRouteandPointbyId(idroute,point.first);
			visited.addOverlay(new PointOverlay(new GeoPoint((int)(point.second.first*1000000),(int)(point.second.second*1000000)), info.second.title, info.second.title,info.second,info.first));
		}
		if (visited.size()!=0)	
			mapOverlays.add(visited);
		
		
		
	}
	public void startRoute(){
		removePopUpPI();
		this.removePopUpOnRoute();
		this.removePopUpPIOnRoute();
		this.refreshMapRouteMode();
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
		PointOverlay item;
		for (Route r : FRAGUEL.getInstance().routes) {
			image=FRAGUEL.getInstance().getResources().getDrawable(R.drawable.map_marker_notvisited);
			
			capa=new MapItemizedOverlays(image,FRAGUEL.getInstance());
			//RouteOverlay o= new RouteOverlay(r);
			//mapOverlays.add(o);
			for (PointOI p : r.pointsOI) { 
				point=new GeoPoint((int)(p.coords[0]*1000000),(int)(p.coords[1]*1000000));
				item= new PointOverlay(point,p.title,p.title,p,r);
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
		if (FRAGUEL.getInstance().getGPS().isRouteMode())
			if (showWay)
				menu.add(0, MAPSTATE_MENU_DRAWPATH, 0, "¡Guíame!").setIcon(R.drawable.ic_menu_routeadd);
			else
				menu.add(0, MAPSTATE_MENU_DRAWPATH, 0, "No guíar").setIcon(R.drawable.ic_menu_routerem);
		
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
			if (mapView.isSatellite())
				mapView.setSatellite(false);
			else
				mapView.setSatellite(true);
			return true;
		
		case MAPSTATE_MENU_DRAWPATH:
			if(showWay){
				mapOverlays.add(new NextPointOverlay());
			}
			else{
				this.refreshMapRouteMode();			
			}
			showWay=!showWay;
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
			FRAGUEL.getInstance().changeState(MainMenuState.STATE_ID);
			return true;
		}

		return false;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated methd stub
		if (item.getGroupId()==0){
			switch (item.getItemId()) {
			case 0:
				isContextMenuDisplayed=false;
				FRAGUEL.getInstance().closeContextMenu();
				FRAGUEL.getInstance().getGPS().startRoute(route, route.pointsOI.get(0));
				FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(route, route.pointsOI.get(0) );
				
				break;
			case 1:
				isContextMenuDisplayed=false;
				FRAGUEL.getInstance().closeContextMenu();
				FRAGUEL.getInstance().getGPS().startRoute(route, point);
				FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(route,point);
				
	
				break;
			case 2:
				isContextMenuDisplayed=false;
				FRAGUEL.getInstance().closeContextMenu();
				this.chooseAnotherRoute=true;
				FRAGUEL.getInstance().openContextMenu(mapView);
				break;	
			case 3:
				isContextMenuDisplayed=false;
				FRAGUEL.getInstance().closeContextMenu();
				FRAGUEL.getInstance().getGPS().stopRoute();
				break;	
			case 4:
				isContextMenuDisplayed=false;
				FRAGUEL.getInstance().closeContextMenu();
				break;	
			}
		}else if (item.getGroupId()==1){
			if (item.getItemId()>=FRAGUEL.getInstance().routes.size()){
				FRAGUEL.getInstance().closeContextMenu();
				chooseAnotherRoute=false;
			}else{
				FRAGUEL.getInstance().closeContextMenu();
				choosePoint=true;
				routeContext=FRAGUEL.getInstance().routes.get(item.getItemId());
				chooseAnotherRoute=false;
				FRAGUEL.getInstance().openContextMenu(mapView);
			}
		}else if (item.getGroupId()==2){
			if (item.getItemId()==-1){
				FRAGUEL.getInstance().getGPS().startRoute(routeContext, routeContext.pointsOI.get(0));
				FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(routeContext, routeContext.pointsOI.get(0));
				
			}else if (item.getItemId()>=routeContext.pointsOI.size()){
				FRAGUEL.getInstance().closeContextMenu();
			}else{
				FRAGUEL.getInstance().getGPS().startRoute(routeContext, routeContext.pointsOI.get(item.getItemId()));
				FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(routeContext, routeContext.pointsOI.get(item.getItemId()));
				
			}
			choosePoint=false;
			routeContext=null;
		}
		
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		
		if (!FRAGUEL.getInstance().getGPS().isRouteMode()&& !chooseAnotherRoute && !choosePoint){
			menu.setHeaderTitle("Comenzar Ruta: "+ route.name);  
			menu.add(0, 0, 0, "Desde el principio");
			menu.add(0, 1, 0, "Desde: "+point.title);
			menu.add(0, 2, 0, "Elegir otra ruta"); 
			menu.add(0, 4, 0, "Cerrar diálogo");
	    } 
		else if (FRAGUEL.getInstance().getGPS().isRouteMode() && !chooseAnotherRoute && !choosePoint){
			menu.setHeaderTitle("¿Desea abandonar la ruta?");
			menu.add(0, 3, 0, "Abandonar ruta");
		}else if (chooseAnotherRoute){
			menu.setHeaderTitle("Elegir ruta"); 
			int i=0;
			for (Route r: FRAGUEL.getInstance().routes){
				menu.add(1, i, 0, r.name);
				i++;
			}
			//menu.add(1, i, 0, "Cerrar diálogo");
			chooseAnotherRoute=false;
			
		}else if (choosePoint){
			menu.setHeaderTitle("Elegir punto de comienzo"); 
			menu.add(2, -1, 0, "Desde el principio");
			int i=0;
			for (PointOI p: routeContext.pointsOI){
				menu.add(2, i, 0, p.title);
				i++;
			}
			//menu.add(2, i, 0, "Cerrar diálogo");
			choosePoint=false;
			
		}
	}

	public void setContextMenuDisplayed(boolean isContextMenuDisplayed) {
		this.isContextMenuDisplayed = isContextMenuDisplayed;
	}

	public boolean isContextMenuDisplayed() {
		return isContextMenuDisplayed;
	}
	

}
