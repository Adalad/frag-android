package fraguel.android.gps;

import java.util.ArrayList;
import android.util.Pair;

import android.location.Location;
import fraguel.android.PointOI;
import fraguel.android.Route;

public class GPSProximityRouteListener extends GPSProximity{

	public GPSProximityRouteListener(){
		
		pointsVisited = new ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>>();
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

}
