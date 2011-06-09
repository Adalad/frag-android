package fraguel.android.gps;

import java.util.ArrayList;
import java.util.Iterator;

import android.location.Location;
import android.util.Pair;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.notifications.GPSIgnoreButton;
import fraguel.android.notifications.ProximityAlertNotificationButton;
import fraguel.android.states.MapState;
import fraguel.android.states.PointInfoState;

public class GPSProximityListener extends GPSProximity{

	
	public GPSProximityListener(){
		
		super();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		distance = Float.MAX_VALUE;
		//sacamos puntos que hayamos visitado
		Iterator<PointOI> it = pointsVisited.iterator();
			while (it.hasNext()) {
				PointOI point = it.next();
				Location.distanceBetween(latitude, longitude,point.coords[0], point.coords[1],results);
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
						PointOI actual = it.next();
						if (actual.id == p.id) {
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
			//si hay algún punto dentro del radio de acción mostramos una notificación
			if (currentRoute != null && currentPoint != null && !MapState.getInstance().getGPS().isDialogDisplayed()) {
					
				msg = currentRoute.name + " - " + currentPoint.title + ": "+distance+" metros";
				FRAGUEL.getInstance().createTwoButtonNotification(R.string.notification_proximityAlert_title_spanish,msg,R.string.notification_proximityAlert_possitiveButton_spanish,
								R.string.notification_proximityAlert_negativeButton_spanish,new ProximityAlertNotificationButton(currentRoute, currentPoint),new GPSIgnoreButton());
				
				super.mediaNotification();
				
				pointsVisited.add(currentPoint);
				
				MapState.getInstance().getGPS().setDialogDisplayed(true);
			}

			currentRoute = null;
			currentPoint = null;
	}

}
