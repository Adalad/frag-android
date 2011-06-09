package fraguel.android.gps;

import java.util.ArrayList;

import android.location.Location;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Pair;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;

public abstract class GPSProximity {
	public static final float proximityAlertDistance = 50;
	public static final float proximityAlertError = 10;
	protected float distance;
	protected Route currentRoute = null;
	protected PointOI currentPoint = null;
	protected float[] results = new float[3];
	protected String msg;
	
	protected double latitude=0,longitude=0,altitude=0;
	
	protected boolean hasBeenVisited;

	protected ArrayList<PointOI> pointsVisited;
	
	private Vibrator v ;
	private MediaPlayer soundClip;

	public GPSProximity(){
		v= (Vibrator)FRAGUEL.getInstance().getSystemService(FRAGUEL.getInstance().getApplicationContext().VIBRATOR_SERVICE);
		soundClip=MediaPlayer.create(FRAGUEL.getInstance().getApplicationContext(),R.raw.notification_sound);
		pointsVisited = new ArrayList<PointOI>();
	}
	
	public abstract void onLocationChanged(Location location);
	//public abstract void setPointVisited(Route r, PointOI p, float latitude,float longitude);
	public void mediaNotification(){
		v.vibrate(1000);
		soundClip.start();
	}
	public Route getCurrentRoute(){
		return currentRoute;
	}
	public PointOI getCurrentPoint(){
		return currentPoint;
	}
}

