package fraguel.android.gps;

import java.util.ArrayList;

import android.location.Location;
import android.util.Pair;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;

public class GPSProximityRouteListener extends GPSProximity{

	private ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> pointsToVisit;
	
	public GPSProximityRouteListener(){
		
		super();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void setPointVisited(Route r, PointOI p, float latitude,
			float longitude) {
		// TODO Auto-generated method stub
		
	}
	
	public void startRoute (Route selectedRoute, PointOI pointToStart){
		boolean limit=false;
		for (Route route : FRAGUEL.getInstance().routes) {
			if (selectedRoute.id==route.id){
				
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
	

}
