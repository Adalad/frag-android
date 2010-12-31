package fraguel.android;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
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

import fraguel.android.states.ARState;
import fraguel.android.states.ConfigState;
import fraguel.android.states.ImageState;
import fraguel.android.states.InfoState;
import fraguel.android.states.IntroState;
import fraguel.android.states.MapState;
import fraguel.android.states.MenuState;
import fraguel.android.states.VideoState;
import fraguel.android.xml.ResourceParser;

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
	
	// Routes and Points OI
	ArrayList<Route> routes;
	ArrayList<PointOI> pointsOI;

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
			myPosition= new Me(new GeoPoint((int) (40.4435602 * 1000000), (int) (-3.7257781 * 1000000)));
		
		//requestUpdatesFromAllSensors
		activateSensors();
		activateGPS();

		// Routes and points OI
		routes = new ArrayList<Route>();
		pointsOI = new ArrayList<PointOI>();
		ResourceParser.getInstance().setRoot("/");
		//ResourceParser.getInstance().readRoutes("routes");
		
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
		
		//view.removeView(MapState.getInstance().getPopupView());
		//if(currentState.getId()==2)
		//((MapState) currentState).onTouch(view,ev);
		
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
				SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
		sensorManager.registerListener(sensorListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
	}
	
	public void activateGPS(){
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myPosition);
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {	
			createGpsDisabledAlert();
		}

		
	}
	
	private void createGpsDisabledAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¡Su GPS está desactivado! Para el correcto funcionamiento de la aplicación debe activarlo."+"\n"+ "¿Desea activarlo ahora?");
        builder.setCancelable(false);
        builder.setPositiveButton("Habilitar GPS",
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                	Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        			startActivity(gpsOptionsIntent);
                                                }
                                        });
        builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                }
                        });
        AlertDialog alert = builder.create();
        alert.getWindow().setGravity(Gravity.TOP);
        alert.show();
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
			 public synchronized void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
			}

			@Override
			 public synchronized void onSensorChanged(SensorEvent event) {
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
	    
			
			currentState.onConfigurationChanged(newConfig);
			super.onConfigurationChanged(newConfig);
	}


//***********************************************************************************
//*************************************************************************************
	public class Me implements LocationListener{

		private GeoPoint currentLocation;
		private double latitude=0,longitude=0,altitude=0;
		
		
		private Me(GeoPoint arg0) {
			currentLocation=arg0;
			// TODO Auto-generated constructor stub
		}
		

		@Override
		public synchronized void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			latitude=location.getLatitude();
			longitude=location.getLongitude();
			altitude=location.getAltitude();
			currentLocation=new GeoPoint((int) (latitude * 1E6),(int) (longitude * 1E6));
			if (FRAGUEL.getInstance().getCurrentState().getId()==1){
				MenuState s=(MenuState)FRAGUEL.getInstance().getCurrentState();
				s.setGPSText("Latitud: "+latitude+", Longitud: "+longitude);
			}
						
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