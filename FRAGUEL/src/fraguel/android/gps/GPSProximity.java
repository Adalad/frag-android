package fraguel.android.gps;

import android.location.Location;
import fraguel.android.PointOI;
import fraguel.android.Route;

public abstract class GPSProximity {
	public static final float proximityAlertDistance = 50;
	public static final float proximityAlertError = 10;
	
		
	public abstract void onLocationChanged(Location location);
	public abstract void setPointVisited(Route r, PointOI p, float latitude,float longitude);
}
