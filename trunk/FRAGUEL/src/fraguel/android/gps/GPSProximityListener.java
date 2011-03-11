package fraguel.android.gps;

import java.util.ArrayList;
import java.util.Iterator;

import android.location.Location;
import android.util.Pair;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import fraguel.android.states.PointInfoState;

public class GPSProximityListener extends GPSProximity{

	
	public GPSProximityListener(){
		
		pointsVisited = new ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>>();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		//sacamos puntos que hayamos visitado
		Iterator<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> it = pointsVisited.iterator();
			while (it.hasNext()) {
				Pair<Pair<Integer, Integer>, Pair<Float, Float>> routeAndPoint = it.next();
				Location.distanceBetween(latitude, longitude,routeAndPoint.second.first, routeAndPoint.second.first,results);
				if (results[0] >= proximityAlertDistance + proximityAlertError) {
					it.remove();
				}
			}

		// comprobamos si estamos cerca para cada punto de cada ruta
			for (Route r : FRAGUEL.getInstance().routes) {
				for (PointOI p : r.pointsOI) {
					hasBeenVisited = false;

					it = pointsVisited.iterator();
					// comprobamos que no lo hayamos visitado ya ese punto(es decir, sigamos aun en el radio de acción)
					while (it.hasNext() && !hasBeenVisited) {
						Pair<Pair<Integer, Integer>, Pair<Float, Float>> actual = it.next();
						if (actual.first.first == r.id && actual.first.second == p.id) {
							hasBeenVisited = true;
						}
					}

					if (!hasBeenVisited) {
						Location.distanceBetween(latitude, longitude,p.coords[0], p.coords[1], results);

						if (results[0] <= proximityAlertDistance) {
								if (results[0] < distance) {
									currentRoute = r;
									currentPoint = p;
									distance=results[0];
								}
						}
					}
				}
			}

			if (currentRoute != null && currentPoint != null && !FRAGUEL.getInstance().getGPS().isDialogDisplayed()) {
				
				FRAGUEL.getInstance().changeState(PointInfoState.STATE_ID);
				FRAGUEL.getInstance().getCurrentState().loadData(currentRoute, currentPoint);
				FRAGUEL.getInstance().getGPS().setDialogDisplayed(true);
			}

			currentRoute = null;
			currentPoint = null;
	}

	@Override
	public void setPointVisited(Route r, PointOI p, float latitude,
			float longitude) {
		// TODO Auto-generated method stub
		
	}

}
