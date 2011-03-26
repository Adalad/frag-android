package fraguel.android.maps;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;

public class RouteOverlay extends Overlay{

	private Route route=null;
	ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> visited=null;
	ArrayList<Pair<Integer, Pair<Float, Float>>> notVisited=null;
	

	public RouteOverlay (Route r){
		route=r;
	}
	public RouteOverlay(){
		visited= FRAGUEL.getInstance().getGPS().getRoutePointsVisited();
		notVisited=FRAGUEL.getInstance().getGPS().getRoutePointsNotVisited();
		
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) { 
		Projection projection=mapView.getProjection();
		Paint paint= new Paint();
		Point actual=null;
		Point  anterior=null;
		paint.setStrokeWidth(5);
		paint.setAlpha(120);
		boolean first= false;
		
		if (route==null){
			paint.setColor(200);
			for (Pair<Pair<Integer, Integer>, Pair<Float, Float>> p: visited){
				anterior=actual;
				actual= new Point();
				projection.toPixels(new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000)), actual);
				if (first){
					canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
				}
				else
					first=true;	
			}
			paint.setColor(32);
			for (Pair<Integer, Pair<Float, Float>> p: notVisited){
				anterior=actual;
				actual= new Point();
				projection.toPixels(new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000)), actual);
				if (first){
					canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
				}
				else
					first=true;
			}
			
			
		}else{
			paint.setColor(10);
			for (PointOI p: route.pointsOI){
				anterior=actual;
				actual= new Point();
				projection.toPixels(new GeoPoint((int)p.coords[0]*1000000,(int)p.coords[1]*1000000), actual);
				if (first){
					canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
				}
				else
					first=true;			
			
			}
		}
		super.draw(canvas, mapView, shadow);
		
	}
}
