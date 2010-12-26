package fraguel.android;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.OverlayItem;

import fraguel.android.states.ARState;
import fraguel.android.states.ConfigState;
import fraguel.android.states.ImageState;
import fraguel.android.states.InfoState;
import fraguel.android.states.IntroState;
import fraguel.android.states.MapState;
import fraguel.android.states.MenuState;
import fraguel.android.states.VideoState;

public class FRAGUEL extends MapActivity implements OnClickListener {

	// Singleton
	private static FRAGUEL instance;
	// Sensors info
	private SensorManager sensorManager;
	private SensorEventListener sensorListener;
	private Me myPosition;
	private LocationManager locationManager;
	private float[] sOrientation = { 0, 0, 0 };
	private float[] sAccelerometer = { 0, 0, 0 };
	private float[] sMagnetic = { 0, 0, 0 };
	private float[] rotMatrix = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private float[] incMatrix = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private static final float RAD2DEG=(float) (180/Math.PI);

	// View container
	private ViewGroup view;
	// States
	private ArrayList<State> states;
	private State currentState;

	// Menu variable buttons
	private static final int MENU_1 = 1;
	private static final int MENU_2 = 2;
	private static final int MENU_3 = 3;
	private static final int MENU_4 = 4;

	
		
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, MENU_1, 0, R.string.menu_menu);
		menu.add(0, MENU_2, 0, R.string.menu_config);
		menu.add(0, MENU_3, 0,R.string.menu_route);
		menu.add(0, MENU_4, 0, R.string.menu_exit);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_1:
			changeState(1);
			return true;
		case MENU_2:
			Toast t1= Toast.makeText(this.getApplicationContext(), "Por definir", Toast.LENGTH_SHORT);
			t1.show();
			return true;
		case MENU_3:
			Toast t2= Toast.makeText(this.getApplicationContext(), "Por definir", Toast.LENGTH_SHORT);
			t2.show();
			return true;
		case MENU_4:
			Toast t3= Toast.makeText(this.getApplicationContext(), "Por definir", Toast.LENGTH_SHORT);
			t3.show();
			return true;
		}

		return false;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		view = new FrameLayout(this);
		setContentView(view);

		// Singleton
		instance = this;

		
		//RequestServices (GPS & Sensors)
		
			requestServices();
			
		// Sensors
		
			newSensorListener();
		
		
		
		//GPS Listener
			myPosition= new Me(new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7257781 * 1000000)),R.drawable.icon_museo);
		
		//requestUpdatesFromAllSensors
		activateSensors();
		activateGPS();

		// TODO añadir estados
		states = new ArrayList<State>();
		addState(new IntroState(), true);
		addState(new MenuState(), false);
		addState(new MapState(), false);
		addState(new VideoState(), false);
		addState(new ImageState(), false);
		addState(new ARState(), false);
		addState(new InfoState(), false);
		addState(new ConfigState(), false);


	}

	public static FRAGUEL getInstance() {
		if (instance == null)
			instance = new FRAGUEL();
		return instance;
	}

	@Override
	public void onClick(View view) {
		currentState.onClick(view);
	}


	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode()==event.KEYCODE_BACK) 
			this.changeState(1);

		return true;
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		view.removeView(MapState.getInstance().getPopupView());
		return super.dispatchTouchEvent(ev);
	}

	public void addState(State state, boolean change) {
		for (State s : states) {
			if (s.compareTo(state) == 0)
				return;
		}
		states.add(state);
		if (change)
			changeState(state.id);

	}

	public void changeState(int id) {
		if (currentState != null) {
			if (currentState.id == id)
				return;
			currentState.unload();
		}
		System.gc();
		for (State s : states) {
			if (s.id == id) {
				currentState = s;
				currentState.load();
				return;
			}
		}
	}

	public void addView(View v) {
		view.addView(v);
	}

	public void removeAllViews() {
		view.removeAllViews();
	}

	public void activateSensors() {
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void activateGPS(){
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myPosition);
	}

	public void deactivateSensors() {
		sensorManager.unregisterListener(sensorListener);
		
	}
	
	public void deactivateGPS(){
		
		locationManager.removeUpdates(myPosition);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onPause(Bundle savedInstanceState) {
        super.onPause();
        deactivateSensors();
        deactivateGPS();
	}
	
	public void onResume(Bundle savedInstanceState) {
        super.onResume();
        activateSensors();
        activateGPS();
        
	}
	
	private void requestServices(){
		locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
	}
	
	private void newSensorListener(){
		sensorListener = new SensorEventListener(){
			
			@Override
			synchronized public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
			}

			@Override
			synchronized public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
					FRAGUEL.getInstance().sOrientation[0] = event.values[0];
					FRAGUEL.getInstance().sOrientation[1] = event.values[1];
					FRAGUEL.getInstance().sOrientation[2] = event.values[2];
				} else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					FRAGUEL.getInstance().sAccelerometer[0] = event.values[0];
					FRAGUEL.getInstance().sAccelerometer[1] = event.values[1];
					FRAGUEL.getInstance().sAccelerometer[2] = event.values[2];
				} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
					FRAGUEL.getInstance().sMagnetic[0] = event.values[0];
					FRAGUEL.getInstance().sMagnetic[1] = event.values[1];
					FRAGUEL.getInstance().sMagnetic[2] = event.values[2];
				}
				
						
					if (SensorManager.getRotationMatrix(rotMatrix, incMatrix, sAccelerometer,sMagnetic )){
						SensorManager.getOrientation(rotMatrix, sOrientation);
		    			
						if (currentState.getId()==1){
		    				MenuState m=(MenuState)currentState;
		    				m.setOrientationText("X: "+ sOrientation[0]*RAD2DEG+", Y: "+sOrientation[1]*RAD2DEG+",Z: "+sOrientation[2]*RAD2DEG);
		    			}
						rotMatrix[3]=(float)myPosition.getLongitude();
						rotMatrix[7]=(float)myPosition.getLatitude();
						rotMatrix[11]=(float)myPosition.getAltitude();
						//rotMatrix: matriz 4X4 de rotación para pasarla a OpenGL
					}
				
			}
	
		};
		
	}
	
	public ViewGroup getView() {
		return view;
	}


	public void setView(ViewGroup view) {
		this.view = view;
	}


	public float[] getRotMatrix() {
		return rotMatrix;
	}


	public float[] getIncMatrix() {
		return incMatrix;
	}

	
	public State getCurrentState(){
		
		return this.currentState;
	}
	
	public Me getGPS(){
		return myPosition;
	}
	
	@Override
	protected boolean isLocationDisplayed() {
		// TODO Auto-generated method stub
		//Este método pone que es obligatorio ponerlo cuando muestras tu posicion
		//en la API de Maps si no es ilegal la app
		return (currentState.id==2);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    
		if (!currentState.onConfigurationChanged(newConfig))
			super.onConfigurationChanged(newConfig);
  
	}


//***********************************************************************************
//*************************************************************************************
	public class Me implements LocationListener{

		private GeoPoint currentLocation;
		private double latitude=0,longitude=0,altitude=0;
		private int picture;
		
		
		private Me(GeoPoint arg0,int d) {
			currentLocation=arg0;
			picture=d;
			// TODO Auto-generated constructor stub
		}
		

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			latitude=location.getLatitude();
			longitude=location.getLongitude();
			altitude=location.getAltitude();
			currentLocation=new GeoPoint((int) (latitude * 1E6),(int) (longitude * 1E6));
			if (FRAGUEL.getInstance().getCurrentState().getId()==1){
				MenuState s=(MenuState)FRAGUEL.getInstance().getCurrentState();
				s.setGPSText("Latitud: "+latitude+", Longitud: "+longitude);
			}
			if (FRAGUEL.getInstance().getCurrentState().getId()==2){
				MapState.getInstance().LocationChanged(currentLocation);
			}
			
			
			
		}


		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "El GPS está desactivado, por favor actívelo", Toast.LENGTH_SHORT).show();
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

		public void setPicture(int picture) {
			this.picture = picture;
		}

		public int getPicture() {
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
}