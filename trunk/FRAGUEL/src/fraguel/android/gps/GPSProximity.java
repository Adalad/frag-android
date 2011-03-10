package fraguel.android.gps;

import android.location.Location;

public abstract class GPSProximity {
	public static final float proximityAlertDistance = 50;
	public static final float proximityAlertError = 10;
	
	public abstract void onLocationChanged(Location location);
}
