package fraguel.android;

import java.util.ArrayList;


import android.content.Context;
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

import com.google.android.maps.MapActivity;

import fraguel.android.sensors.SensorListener;
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
	public static FRAGUEL instance;
	// Sensors info
	private SensorManager sensorManager;
	private SensorEventListener sensorListener;
	private LocationListener gpsListener;
	private LocationManager locationManager;
	public float[] sOrientation = { 0, 0, 0 };
	public float[] sAccelerometer = { 0, 0, 0 };
	public float[] sMagnetic = { 0, 0, 0 };
	public float[] sGyroscope = { 0, 0, 0 };
	public float sTemperature = 0;
	public float sLight = 0;
	public float sProximity = 0;
	// View container
	public ViewGroup view;
	// States
	public ArrayList<State> states;
	public State currentState;

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
		
		
		
		//GPS
			newGPSListener();
		
		//requestUpdatesFromAllSensors
		activateSensors();

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
				sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
				SensorManager.SENSOR_DELAY_NORMAL);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
	}

	public void deactivateSensors() {
		sensorManager.unregisterListener(sensorListener);
		locationManager.removeUpdates(gpsListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onPause(Bundle savedInstanceState) {
        super.onPause();
        deactivateSensors();
	}
	
	public void onResume(Bundle savedInstanceState) {
        super.onResume();
        activateSensors();
        
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

			}
	
		};
		
	}
	
	private void newGPSListener(){
		gpsListener= new LocationListener() {
        	
    		public void onLocationChanged(Location location){
    			
    		}
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				switch (status){
					case  LocationProvider.AVAILABLE: break;
					case  LocationProvider.OUT_OF_SERVICE: break;
					case  LocationProvider.TEMPORARILY_UNAVAILABLE: break; 
					
				}
			}  
		};
		
		
	}

}