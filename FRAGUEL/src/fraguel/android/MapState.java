package fraguel.android;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import fraguel.android.maps.MapItemizedOverlays;

public class MapState extends State{
	
	private MapController mapControl;
	private LocationManager locationManager;
	private MapView mapView;
	

	private MediaPlayer mp;
	MapItemizedOverlays itemizedoverlay;
	

	public MapState() {
		super();
		id = 2;
	}

	@Override
	public void load() {
		//Creamos e importamos el layout del xml
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		viewGroup= (ViewGroup) li.inflate(R.layout.maingooglemaps,  null);
		FRAGUEL.getInstance().addView(viewGroup);

		//Configuramos mapview
		mapView = (MapView) FRAGUEL.getInstance().findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapControl = mapView.getController();
		GeoPoint pointInit = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7267881 * 1000000));
		mapControl.setZoom(15);
		mapControl.setCenter(pointInit);

		//Creamos los Overlays
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = FRAGUEL.getInstance().getResources().getDrawable(R.drawable.museumsalango);
		itemizedoverlay = new MapItemizedOverlays(drawable,FRAGUEL.getInstance());

		//primer punto
		GeoPoint point1 = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7257881 * 1000000));
		OverlayItem overlayitem = new OverlayItem(point1, "Facultad A", "En el año...");

		itemizedoverlay.addOverlay(overlayitem);

		//segundo punto
		GeoPoint point2 = new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7297881 * 1000000));
		OverlayItem overlayitem2 = new OverlayItem(point2, "Facultad B", "En el año...");

		itemizedoverlay.addOverlay(overlayitem2);

		mapOverlays.add(itemizedoverlay);
	        
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO rellenar con ids de estados
		/*switch (v.getId()) {
		case 1:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 2:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 3:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 4:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 5:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 6:
			FRAGUEL.getInstance().changeState(id);
			break;
		default:
			System.exit(0);
		}*/
	}
	
	public MapView getMapView() {
		return mapView;
	}


}
