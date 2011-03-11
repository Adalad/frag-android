package fraguel.android.gps;

import java.util.ArrayList;

import android.location.Location;
import android.util.Pair;
import fraguel.android.PointOI;
import fraguel.android.Route;

public abstract class GPSProximity {
	public static final float proximityAlertDistance = 50;
	public static final float proximityAlertError = 10;
	protected float distance = Float.MAX_VALUE;
	protected Route currentRoute = null;
	protected PointOI currentPoint = null;
	protected float[] results = new float[3];
	
	protected double latitude=0,longitude=0,altitude=0;
	
	protected boolean hasBeenVisited;

	
	protected ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> pointsVisited;
	
	public abstract void onLocationChanged(Location location);
	public abstract void setPointVisited(Route r, PointOI p, float latitude,float longitude);
}
