package fraguel.android.gps;

import java.util.ArrayList;


import fraguel.android.R;
import android.location.Location;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.notifications.WarningNotificationButton;
import fraguel.android.states.MapState;
import fraguel.android.states.PointInfoState;

public class GPSProximityRouteListener extends GPSProximity{

	private ArrayList<PointOI> pointsToVisit;
	private float bearing;
	
	public GPSProximityRouteListener(){
		
		super();
		pointsToVisit= new ArrayList<PointOI>();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		distance = Float.MAX_VALUE;
		
		if (pointsToVisit.size()==0){
			//ruta terminada
			MapState.getInstance().removeAllPopUps();
			FRAGUEL.getInstance().createOneButtonNotification("Ruta finalizada", "Ha completado todos los puntos de interés de la ruta "+currentRoute.name, new WarningNotificationButton());
			
		}else{
			
			//miramos la distancia al siguiente punto a visitar en la ruta
			Location.distanceBetween(latitude, longitude, pointsToVisit.get(0).coords[0], pointsToVisit.get(0).coords[1], results);
			distance=results[0];
			if (results[0]<=proximityAlertDistance){
				bearing=results[1];
				currentPoint=pointsToVisit.get(0);
				
				FRAGUEL.getInstance().changeState(PointInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(currentRoute, currentPoint);
				MapState.getInstance().getGPS().setDialogDisplayed(true);
				//actualizar las listas y el MapState
				pointsVisited.add(currentPoint);
				pointsToVisit.remove(0);
				MapState.getInstance().refreshMapRouteMode();

			}else{
				//mostrar info de la distancia y el bearing si no hay ningun popup
				((TextView)MapState.getInstance().getPopupOnRoute().findViewById(R.id.popuponroute_texto1)).setText(distance+ " metros para llegar a "+pointsToVisit.get(0).title);
				if (!MapState.getInstance().isAnyPopUp())
					MapState.getInstance().setPopupOnRoute();
				

			}	

		}
	}
	
	public void startRoute (Route selectedRoute, PointOI pointToStart){
		pointsToVisit.clear();
		pointsVisited.clear();
		boolean limit=false;
				currentRoute=selectedRoute;
				for (PointOI point : currentRoute.pointsOI){
					if (!limit){
						if (point.id==pointToStart.id){
							limit=true;
							pointsToVisit.add(point);
						}else{
							pointsVisited.add(point);
						}	
						
					}else{
						pointsToVisit.add(point);
					}	
				}
				
				
		MapState.getInstance().startRoute();
	}
	
	public ArrayList<PointOI> pointsVisited(){
		return pointsVisited;
	}
	
	public ArrayList<PointOI> pointsToVisit(){
		return pointsToVisit;
	}
	

}
