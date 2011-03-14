package fraguel.android.gps;

import java.util.ArrayList;

import android.location.Location;
import android.util.Pair;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.notifications.WarningNotificationButton;

public class GPSProximityRouteListener extends GPSProximity{

	private ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> pointsToVisit;
	private float bearing;
	
	public GPSProximityRouteListener(){
		
		super();
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
			for (Pair<Pair<Integer, Integer>, Pair<Float, Float>> point : pointsToVisit){
				Location.distanceBetween(latitude, longitude,point.second.first, point.second.second,results);
				
				if (results[0]<distance){
					distance=results[0];
					bearing=results[1];
					
				}
			}
			
			if (distance<=proximityAlertDistance && !FRAGUEL.getInstance().getGPS().isDialogDisplayed()){
				//hay un punto cerca, mostrar el estado del punto y ponerlo en la lista de visitados actualizando mapa
				FRAGUEL.getInstance().getGPS().setDialogDisplayed(true);
			}else{
				//miramos si estamos cerca de los ya visitados
				for (Pair<Pair<Integer, Integer>, Pair<Float, Float>> point : pointsVisited){
					Location.distanceBetween(latitude, longitude,point.second.first, point.second.second,results);
					
					if (results[0]<distance){
						distance=results[0];
						bearing=results[1];
						
					}
					
				}
				if (distance<=proximityAlertDistance && !FRAGUEL.getInstance().getGPS().isDialogDisplayed()){
					//hay un punto cerca de los ya visitados, llamar al pop-up del mapa
					FRAGUEL.getInstance().getGPS().setDialogDisplayed(true);
				}
				
			}
			
		}
	}

	@Override
	public void setPointVisited(Route r, PointOI p, float latitude,	float longitude) {
		// TODO Auto-generated method stub
		
	}
	
	public void startRoute (Route selectedRoute, PointOI pointToStart){
		boolean limit=false;
		for (Route route : FRAGUEL.getInstance().routes) {
			if (selectedRoute.id==route.id){
				currentRoute=selectedRoute;
				for (PointOI point : route.pointsOI){
					if (!limit){
						if (point.id==pointToStart.id){
							limit=true;
							pointsToVisit.add(new Pair<Pair<Integer, Integer>, Pair<Float, Float>>(new Pair<Integer, Integer>(route.id,point.id),new Pair<Float, Float>(point.coords[0],point.coords[1])));
						}else{
							pointsVisited.add(new Pair<Pair<Integer, Integer>, Pair<Float, Float>>(new Pair<Integer, Integer>(route.id,point.id),new Pair<Float, Float>(point.coords[0],point.coords[1])));
						}	
						
					}else{
						pointsToVisit.add(new Pair<Pair<Integer, Integer>, Pair<Float, Float>>(new Pair<Integer, Integer>(route.id,point.id),new Pair<Float, Float>(point.coords[0],point.coords[1])));
					}	
				}
				
				break;
			}
		}
	}
	
	public ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> pointsVisited(){
		return pointsVisited;
	}
	
	public ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> pointsToVisit(){
		return pointsToVisit;
	}
	

}
