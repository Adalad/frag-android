package fraguel.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

import fraguel.android.notifications.GPSIgnoreButton;
import fraguel.android.notifications.ProximityAlertNotificationButton;
import fraguel.android.resources.ResourceManager;
import fraguel.android.states.ARState;
import fraguel.android.states.ConfigState;
import fraguel.android.states.ImageState;
import fraguel.android.states.InfoState;
import fraguel.android.states.IntroState;
import fraguel.android.states.MainMenuState;
import fraguel.android.states.MapState;
import fraguel.android.states.MenuState;
import fraguel.android.states.PointInfoState;
import fraguel.android.states.RouteManagerState;
import fraguel.android.states.VideoGalleryState;
import fraguel.android.states.VideoState;

public class FRAGUEL extends MapActivity implements OnClickListener,
		TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener,
		OnTouchListener {

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
	private float[] rotMatrix = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0 };
	private float[] incMatrix = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0 };
	private static final float RAD2DEG = (float) (180 / Math.PI);

	// TextToSpeech
	private TextToSpeech tts;
	private int MY_DATA_CHECK_CODE;
	private HashMap<String, String> ttsHashMap = new HashMap<String, String>();
	private Handler handler;

	// View container
	private ViewGroup view;
	// States
	private ArrayList<State> states;
	private State currentState;
	private Stack<State> _stateStack;

	// Routes and Points OI
	public ArrayList<Route> routes;
	public ArrayList<PointOI> pointsOI;

	// Menu variable buttons
	private static final int MENU_MAIN = 1;
	private static final int MENU_CONFIG = 2;
	private static final int MENU_ROUTE = 3;
	private static final int MENU_EXIT = 4;

	/**
	 * Se crea el menu de opciones en función del estado
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.clear();
		// Menu de opciones creado por defecto
		menu.add(0, MENU_MAIN, 0, R.string.menu_menu).setIcon(R.drawable.info);
		menu.add(0, MENU_CONFIG, 0, R.string.menu_config).setIcon(
				R.drawable.geotaging);
		menu.add(0, MENU_ROUTE, 0, R.string.menu_route)
				.setIcon(R.drawable.info);
		menu.add(0, MENU_EXIT, 0, R.string.menu_exit).setIcon(R.drawable.info);

		// Menu de opciones del estado
		menu = currentState.onCreateStateOptionsMenu(menu);

		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);

		// Menu de opciones del estado
		onCreateOptionsMenu(menu);

		return true;
	}

	/**
	 * Eventos del menu de opciones de la aplicación en función del estado
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		// Eventos del menu de opciones del estado
		if (!currentState.onStateOptionsItemSelected(item)) {

			// Eventos del menu de opciones creados por defecto
			switch (item.getItemId()) {
			case MENU_MAIN:
				changeState(MenuState.STATE_ID);
				return true;
			case MENU_CONFIG:
				Toast t1 = Toast.makeText(this.getApplicationContext(),
						"Por definir", Toast.LENGTH_SHORT);
				t1.show();
				return true;
			case MENU_ROUTE:
				Toast t2 = Toast.makeText(this.getApplicationContext(),
						"Por definir", Toast.LENGTH_SHORT);
				t2.show();
				return true;
			case MENU_EXIT:
				System.exit(0);
				return true;
			}

		}

		return true;
		// return super.onOptionsItemSelected(item);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		view = new FrameLayout(this);
		view.setBackgroundColor(Color.WHITE);
		this.
		setContentView(view);

		// Singleton
		instance = this;

		// RequestServices (GPS & Sensors)

		requestServices();

		// Sensors

		newSensorListener();

		// GPS Listener
		myPosition = new Me(new GeoPoint((int) (40.4435602 * 1000000),
				(int) (-3.7257781 * 1000000)));

		// requestUpdatesFromAllSensors
		activateSensors();
		activateGPS();

		// Routes and points OI
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			// TODO Message asking for SD Card
			Log.d("FRAGUEL", "SD Card not avaliable");
			System.exit(RESULT_CANCELED);
		} else
			Log.d("FRAGUEL", "SD Card ready");

		routes = new ArrayList<Route>();
		pointsOI = new ArrayList<PointOI>();
		 ResourceManager.getInstance().initialize("fraguel");
		 routes = ResourceManager.getInstance().getXmlManager().readRoutes();
		 for (Route r : routes) {
			 r.pointsOI = ResourceManager.getInstance().getXmlManager().readPointsOI("route"+r.id);
		 }

		// TODO añadir estados
		_stateStack = new Stack<State>();
		states = new ArrayList<State>();
		addState(new IntroState(), true);
		addState(new MainMenuState(), false);
		addState(new MenuState(), false);
		addState(new MapState(), false);
		addState(new VideoState(), false);
		addState(new VideoGalleryState(), false);
		addState(new ImageState(), false);
		addState(new ARState(), false);
		addState(new InfoState(), false);
		addState(new ConfigState(), false);
		addState(new RouteManagerState(), false);
		addState(new PointInfoState(), false);

		// TextToSpeech init & instalation
		checkTTSLibrary();
		initHandler();
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

		if (currentState.dispatchKeyEvent(event))
			return true;
		else
			return super.dispatchKeyEvent(event);
	}

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// TODO Auto-generated method stub

	// view.removeView(MapState.getInstance().getPopupView());
	// if(currentState.getId()==2)
	// ((MapState) currentState).onTouch(view,ev);
	// if(currentState== instanceOf(MapState))
	// MapState.getInstance().onTouch(arg0, ev)
	// this.getView().removeView(MapState.getInstance().getPopupView());
	// return super.dispatchTouchEvent(ev);
	// }

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
				if (id != 0)
					_stateStack.push(currentState);
				return;
			}
		}
	}

	public void returnState() {
		try {
			State current = _stateStack.pop();
			current.unload();
			currentState = _stateStack.peek();
			currentState.load();
		} catch (Exception e) {
			currentState = null;
			changeState(1);
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

	public void activateGPS() {

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, myPosition);

	}

	public void deactivateSensors() {
		sensorManager.unregisterListener(sensorListener);

	}

	public void deactivateGPS() {

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		tts.shutdown();
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	private void requestServices() {
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		sensorManager = (SensorManager) this
				.getSystemService(Context.SENSOR_SERVICE);
	}

	private void newSensorListener() {
		sensorListener = new SensorEventListener() {

			@Override
			public synchronized void onAccuracyChanged(Sensor sensor,
					int accuracy) {
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

				if (SensorManager.getRotationMatrix(rotMatrix, incMatrix,
						sAccelerometer, sMagnetic)) {
					SensorManager.getOrientation(rotMatrix, sOrientation);

					if (currentState.getId() == 1) {
						MenuState m = (MenuState) currentState;
						m.setOrientationText("X: " + sOrientation[0] * RAD2DEG
								+ ", Y: " + sOrientation[1] * RAD2DEG + ",Z: "
								+ sOrientation[2] * RAD2DEG);
					}
					rotMatrix[3] = (float) myPosition.getLongitude();
					rotMatrix[7] = (float) myPosition.getLatitude();
					rotMatrix[11] = (float) myPosition.getAltitude();
					// rotMatrix: matriz 4X4 de rotación para pasarla a OpenGL
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

	public State getCurrentState() {

		return this.currentState;
	}

	public Me getGPS() {
		return myPosition;
	}

	public void createOneButtonNotification(int title, int msg,
			DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.accept_spanish, listener);
		AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.TOP);
		alert.show();

	}

	public void createTwoButtonNotification(int title, int msg,
			int positiveButton, int negativeButton,
			DialogInterface.OnClickListener listenerPositiveButton,
			DialogInterface.OnClickListener listenerNegativeButton) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				FRAGUEL.getInstance());
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(positiveButton, listenerPositiveButton);
		builder.setNegativeButton(negativeButton, listenerNegativeButton);
		AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.TOP);
		alert.show();
	}

	public void createTwoButtonNotification(String title, String msg,
			int positiveButton, int negativeButton,
			DialogInterface.OnClickListener listenerPositiveButton,
			DialogInterface.OnClickListener listenerNegativeButton) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				FRAGUEL.getInstance());
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(positiveButton, listenerPositiveButton);
		builder.setNegativeButton(negativeButton, listenerNegativeButton);
		AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.TOP);
		alert.show();
	}

	public void createTwoButtonNotification(int title, String msg,
			int positiveButton, int negativeButton,
			DialogInterface.OnClickListener listenerPositiveButton,
			DialogInterface.OnClickListener listenerNegativeButton) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				FRAGUEL.getInstance());
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(positiveButton, listenerPositiveButton);
		builder.setNegativeButton(negativeButton, listenerNegativeButton);
		AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.TOP);
		alert.show();
	}

	@Override
	protected boolean isLocationDisplayed() {
		// TODO Auto-generated method stub
		// Este método pone que es obligatorio ponerlo cuando muestras tu
		// posicion
		// en la API de Maps si no es ilegal la app
		return (currentState.id == 2);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		currentState.onConfigurationChanged(newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// si no tiene los datos los instala
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),
						"Instalando las librerías necesarias",
						Toast.LENGTH_SHORT).show();
				FRAGUEL.getInstance().startActivity(installIntent);

			}
			tts = new TextToSpeech(FRAGUEL.getInstance()
					.getApplicationContext(), this);
		}

	}

	private void initHandler() {
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				currentState.onUtteranceCompleted(String.valueOf(msg.arg1));
			}

		};
	}

	@Override
	public void onUtteranceCompleted(String arg0) {
		// TODO Auto-generated method stub
		Message m = new Message();
		m.arg1 = Integer.parseInt(arg0);
		handler.sendMessage(m);
		Log.v("TERMINADO", arg0);
	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		if (TextToSpeech.SUCCESS == arg0) {
			tts.setOnUtteranceCompletedListener(this);
			Locale loc = new Locale("es", "", "");
			if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE) {
				tts.setLanguage(loc);
			} else
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),
						R.string.language_no_available_spanish,
						Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),
					R.string.no_tts_spanish, Toast.LENGTH_LONG).show();
	}

	private void checkTTSLibrary() {
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
	}

	public void talk(String s) {
		if (tts != null) {
			tts.stop();
			tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
		} else
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),
					R.string.no_tts_spanish, Toast.LENGTH_LONG).show();
	}

	public void stopTalking() {
		if (tts != null)
			tts.stop();
		else
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),
					R.string.no_tts_spanish, Toast.LENGTH_LONG).show();
	}

	public void talkSpeech(String s, int id) {
		if (tts != null) {
			tts.stop();
			ttsHashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
					String.valueOf(id));
			tts.speak(s, TextToSpeech.QUEUE_FLUSH, ttsHashMap);
		} else
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),
					R.string.no_tts_spanish, Toast.LENGTH_LONG).show();
	}

	public boolean isTalking() {
		if (tts != null)
			return tts.isSpeaking();
		else
			return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (currentState == MapState.getInstance())
			MapState.getInstance().onTouch(v, event);

		return false;
	}

	// ***********************************************************************************
	// *************************************************************************************
	public class Me implements LocationListener {

		public static final float proximityAlertDistance = 50000000;
		public static final float proximityAlertError = 10;
		private GeoPoint currentLocation;
		private double latitude = 0, longitude = 0, altitude = 0;
		private ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> pointsVisited;
		private float[] results = new float[3];
		private String msg;
		private float distance = Float.MAX_VALUE;
		private Route currentRoute = null;
		private PointOI currentPoint = null;
		private boolean isDialogDisplayed = false, hasBeenVisited;

		private Me(GeoPoint arg0) {
			currentLocation = arg0;
			pointsVisited = new ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>>();

			// TODO Auto-generated constructor stub
		}

		@Override
		public synchronized void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			altitude = location.getAltitude();
			currentLocation = new GeoPoint((int) (latitude * 1E6),
					(int) (longitude * 1E6));
			if (FRAGUEL.getInstance().getCurrentState().getId() == 1) {
				MenuState s = (MenuState) FRAGUEL.getInstance()
						.getCurrentState();
				s.setGPSText("Latitud: " + latitude + ", Longitud: "
						+ longitude);
			}

			// sacamos de visitados los puntos que ya no estén dentro del radio
			// de acción(hemos salido)
			Iterator<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> it = pointsVisited
					.iterator();
			while (it.hasNext()) {
				Pair<Pair<Integer, Integer>, Pair<Float, Float>> routeAndPoint = it
						.next();
				Location.distanceBetween(latitude, longitude,
						routeAndPoint.second.first, routeAndPoint.second.first,
						results);
				if (results[0] >= proximityAlertDistance + proximityAlertError) {
					it.remove();
				}
			}

			// comprobamos si estamos cerca para cada punto de cada ruta
			for (Route r : routes) {
				for (PointOI p : r.pointsOI) {
					hasBeenVisited = false;

					it = pointsVisited.iterator();
					// comprobamos que no lo hayamos visitado ya ese punto(es
					// decir, sigamos aun en el radio de acción)
					while (it.hasNext() && !hasBeenVisited) {
						Pair<Pair<Integer, Integer>, Pair<Float, Float>> actual = it
								.next();
						if (actual.first.first == r.id
								&& actual.first.second == p.id) {
							hasBeenVisited = true;
						}
					}

					if (!hasBeenVisited) {
						Location.distanceBetween(latitude, longitude,
								p.coords[0], p.coords[1], results);

						if (results[0] <= proximityAlertDistance) {
							if (results[0] < distance) {
								currentRoute = r;
								currentPoint = p;
							}
						}
					}
				}
			}

			if (currentRoute != null && currentPoint != null
					&& !isDialogDisplayed) {
				msg = currentRoute.name + " - " + currentPoint.title;
				FRAGUEL.getInstance()
						.createTwoButtonNotification(
								R.string.notification_proximityAlert_title_spanish,
								msg,
								R.string.notification_proximityAlert_possitiveButton_spanish,
								R.string.notification_proximityAlert_negativeButton_spanish,
								new ProximityAlertNotificationButton(
										currentRoute, currentPoint),
								new GPSIgnoreButton());
				pointsVisited
						.add(new Pair<Pair<Integer, Integer>, Pair<Float, Float>>(
								new Pair<Integer, Integer>(currentRoute.id,
										currentPoint.id),
								new Pair<Float, Float>(currentPoint.coords[0],
										currentPoint.coords[0])));
				isDialogDisplayed = true;
			}

			currentRoute = null;
			currentPoint = null;

		}

		public void setPointVisited(Route r, PointOI p, float latitude,
				float longitude) {
			pointsVisited
					.add(new Pair<Pair<Integer, Integer>, Pair<Float, Float>>(
							new Pair<Integer, Integer>(r.id, p.id),
							new Pair<Float, Float>(latitude, longitude)));
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
			switch (status) {
			case LocationProvider.AVAILABLE:
				break;
			case LocationProvider.OUT_OF_SERVICE:
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				break;

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

		public void setDialogDisplayed(boolean isDialogDisplayed) {
			this.isDialogDisplayed = isDialogDisplayed;
		}

		public boolean isDialogDisplayed() {
			return isDialogDisplayed;
		}

	}

}