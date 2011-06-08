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

	private ArrayList<Pair<Integer, Pair<Float, Float>>> pointsToVisit;
	private float bearing;
	private int pointid;
	
	public GPSProximityRouteListener(){
		
		super();
		pointsToVisit= new ArrayList<Pair<Integer, Pair<Float, Float>>>();
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
			
			FRAGUEL.getInstance().createOneButtonNotification("Ruta finalizada", "Ha completado todos los puntos de interés de la ruta "+currentRoute.name, new WarningNotificationButton());
			
		}else{
			
			//miramos la distancia al siguiente punto a visitar en la ruta
			Location.distanceBetween(latitude, longitude, pointsToVisit.get(0).second.first, pointsToVisit.get(0).second.second, results);
			distance=results[0];
			if (results[0]<=proximityAlertDistance){
				bearing=results[1];
				pointid=pointsToVisit.get(0).first;
				for (PointOI point: currentRoute.pointsOI){
					if (point.id==pointid){
						currentPoint=point;
						break;
					}
				}
				FRAGUEL.getInstance().changeState(PointInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(currentRoute, currentPoint);
				MapState.getInstance().getGPS().setDialogDisplayed(true);
				//actualizar las listas y el MapState
				pointsVisited.add(new Pair<Pair<Integer,Integer>, Pair<Float, Float>>(new Pair<Integer,Integer>(currentRoute.id,currentPoint.id),new Pair<Float, Float>(currentPoint.coords[0],currentPoint.coords[1])));
				pointsToVisit.remove(0);
				MapState.getInstance().refreshMapRouteMode();

			}else{
				//mostrar info de la distancia y el bearing si no hay ningun popup
				((TextView)MapState.getInstance().getPopupOnRoute().findViewById(R.id.popuponroute_texto1)).setText(distance+ " metros para llegar a "+pointsToVisit.get(0).first);
				if (!MapState.getInstance().isAnyPopUp())
					MapState.getInstance().setPopupOnRoute();
				

			}	

		}
	}

	@Override
	public void setPointVisited(Route r, PointOI p, float latitude,	float longitude) {
		// TODO Auto-generated method stub
		
	}
	
	public void startRoute (Route selectedRoute, PointOI pointToStart){
		pointsToVisit.clear();
		pointsVisited.clear();
		boolean limit=false;
		for (Route route : FRAGUEL.getInstance().routes) {
			if (selectedRoute.id==route.id){
				currentRoute=selectedRoute;
				for (PointOI point : route.pointsOI){
					if (!limit){
						if (point.id==pointToStart.id){
							limit=true;
							pointsToVisit.add(new Pair<Integer, Pair<Float, Float>>(point.id,new Pair<Float, Float>(point.coords[0],point.coords[1])));
						}else{
							pointsVisited.add(new Pair<Pair<Integer,Integer>, Pair<Float, Float>>(new Pair<Integer,Integer>(route.id,point.id),new Pair<Float, Float>(point.coords[0],point.coords[1])));
						}	
						
					}else{
						pointsToVisit.add(new Pair<Integer, Pair<Float, Float>>(point.id,new Pair<Float, Float>(point.coords[0],point.coords[1])));
					}	
				}
				
				break;
			}
		}
		MapState.getInstance().startRoute();
	}
	
	public ArrayList<Pair<Pair<Integer,Integer>, Pair<Float, Float>>> pointsVisited(){
		return pointsVisited;
	}
	
	public ArrayList<Pair<Integer, Pair<Float, Float>>> pointsToVisit(){
		return pointsToVisit;
	}
	

}
