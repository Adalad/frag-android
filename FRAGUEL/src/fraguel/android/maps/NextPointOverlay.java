package fraguel.android.maps;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import fraguel.android.FRAGUEL;
import fraguel.android.states.MapState;

public class NextPointOverlay extends Overlay{
		
	public NextPointOverlay(){
		
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		
	/*	if (FRAGUEL.getInstance().getGPS().isRouteMode()){
			Projection projection=mapView.getProjection();
			Paint paint= new Paint();
			Point me=new Point();
			Point  target=new Point();
			paint.setStrokeWidth(3);
			paint.setAlpha(120);
			paint.setColor(Color.BLUE);
			projection.toPixels(MapState.getInstance().getLocationFromOverlay(), me);
			projection.toPixels(new GeoPoint((int)(FRAGUEL.getInstance().getGPS().getRoutePointsNotVisited().get(0).second.first*1000000),(int)(FRAGUEL.getInstance().getGPS().getRoutePointsNotVisited().get(0).second.second*1000000)),target );
			canvas.drawLine(me.x,me.y, target.x, target.y, paint);
		}*/
		
		super.draw(canvas, mapView, shadow);
	}
}
