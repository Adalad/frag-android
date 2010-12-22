package fraguel.android.maps;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.states.MapState;
import fraguel.android.states.MenuState;

public class Me implements LocationListener{

	private static Me instance;
	private GeoPoint currentLocation;
	private double latitude=0,longitude=0,altitude=0;
	private Drawable picture;
	
	
	private Me(GeoPoint arg0,Drawable d) {
		currentLocation=arg0;
		setPicture(d);
		// TODO Auto-generated constructor stub
	}
	
	public static Me getInstance(){
		if (instance == null)
			instance = new Me(new GeoPoint(0,0),FRAGUEL.getInstance().getResources().getDrawable(R.drawable.icon_museo));
		return instance;
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		altitude=location.getAltitude();
		currentLocation=new GeoPoint((int) (location.getLatitude() * 1E6),(int) (location.getLongitude() * 1E6));
		if (FRAGUEL.getInstance().getCurrentState().getId()==1){
			MenuState s=(MenuState)FRAGUEL.getInstance().getCurrentState();
			s.setGPSText("Latitud: "+location.getLatitude()+", Longitud: "+location.getLongitude());
		}
		
		MapState.getInstance().animateTo(currentLocation);
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		switch (status){
		case  LocationProvider.AVAILABLE:  break;
		case  LocationProvider.OUT_OF_SERVICE: break;
		case  LocationProvider.TEMPORARILY_UNAVAILABLE: break;
		
		}
	}

	public void setPicture(Drawable picture) {
		this.picture = picture;
	}

	public Drawable getPicture() {
		return picture;
	}

	public GeoPoint getCurrentLocation() {
		return currentLocation;
	}
	

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getAltitude() {
		return altitude;
	}

}
