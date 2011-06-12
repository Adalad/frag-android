package fraguel.android;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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

import com.google.android.maps.MapActivity;

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
import fraguel.android.states.RouteInfoState;
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
	private LocationManager locationManager;
	private float[] sOrientation = { 0, 0, 0 };
	private float[] sAccelerometer = { 0, 0, 0 };
	private float[] sMagnetic = { 0, 0, 0 };
	private float[] rotMatrix = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private float[] incMatrix = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static final float RAD2DEG = (float) (180 / Math.PI);

	// TextToSpeech
	private TextToSpeech tts;
	private int MY_DATA_CHECK_CODE;
	private HashMap<String, String> ttsHashMap = new HashMap<String, String>();
	private Handler handler;
	public Handler imageHandler;
	public Handler routeHandler;

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
	private static final int MENU_EXIT = 2;
	
	private ProgressDialog dialog;

	/**
	 * Se crea el menu de opciones en función del estado
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.clear();
		// Menu de opciones creado por defecto
		menu.add(0, MENU_MAIN, 0, R.string.menu_menu).setIcon(R.drawable.info);
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
				changeState(MainMenuState.STATE_ID);
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
		this.setContentView(view);

		// Singleton
		instance = this;

		// RequestServices (GPS & Sensors)

		requestServices();

		// Sensors

		newSensorListener();


		// requestUpdatesFromAllSensors
		activateSensors();

		// Routes and points OI
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			// TODO Message asking for SD Card
			Log.d("FRAGUEL", "SD Card not avaliable");
			System.exit(RESULT_CANCELED);
		} else
			Log.d("FRAGUEL", "SD Card ready");

		LoadRoutes();
		
		// TODO añadir estados
		_stateStack = new Stack<State>();
		states = new ArrayList<State>();
		addState(new MapState(), true);
		addState(new IntroState(), true);
		addState(new MainMenuState(), false);
		//addState(new MenuState(), false);
		addState(new VideoState(), false);
		addState(new VideoGalleryState(), false);
		//addState(new ImageGalleryState(), false);
		addState(new ImageState(), false);
		addState(new RouteInfoState(),false);
		addState(new ARState(), false);
		addState(new InfoState(), false);
		addState(new ConfigState(), false);
		addState(new RouteManagerState(), false);
		addState(new PointInfoState(), false);

		// TextToSpeech init & instalation
		checkTTSLibrary();
		initHandler();
		initImageHandler();
		initRouteHandler();
	
		//FRAGUEL.getInstance().getGPS().startRoute(this.routes.get(0), this.routes.get(0).pointsOI.get(1));
		//MapState.getInstance().removePopUpPI();
		//((TextView)MapState.getInstance().getPopupOnRoute().findViewById(R.id.popuponroute_texto1)).setText(20+ " metros para llegar a Milán");
		//MapState.getInstance().setPopupOnRoute();
		//FRAGUEL.getInstance().getCurrentState().loadData(routes.get(0), routes.get(0).pointsOI.get(0));		
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
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated methd stub
		if (currentState.onContextItemSelected(item))
			return true;
		else
			return super.onContextItemSelected(item);
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.clear();
		
		currentState.onCreateContextMenu(menu, v, menuInfo);
		 
		
	}
	
	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		MapState.getInstance().setContextMenuDisplayed(false);
		super.onContextMenuClosed(menu);
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
			if (_stateStack.firstElement().getId()!=MainMenuState.STATE_ID){
				State current = _stateStack.pop();
				current.unload();
				currentState = _stateStack.peek();
				currentState.load();
			}
		} catch (Exception e) {
			currentState = null;
			changeState(MainMenuState.STATE_ID);
		}
	}

	public void addView(View v) {
		view.addView(v);
	}

	public void removeAllViews() {
		view.removeAllViews();
	}

	public void activateSensors() {
		sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
		sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
		sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
	}

	public void deactivateSensors() {
		sensorManager.unregisterListener(sensorListener);

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		tts.shutdown();
		super.onDestroy();
		
	}
	
	public void cleanCache(){
		String[] rutas= new File(ResourceManager.getInstance().getRootPath()+"/tmp").list();
		int i = 0;
		File f;
		while ( i<rutas.length){
			f= new File(ResourceManager.getInstance().getRootPath()+"/tmp/"+rutas[i]);
			if (f.isDirectory())
				cleanDir(f.getPath());
			else
				f.delete();
			i++;
		}
	}
	
	public void cleanDir(String path){
		String[] ficheros= new File(path).list();
		int i = 0;
		File f;
		while ( i<ficheros.length){
			f= new File(path+"/"+ficheros[i]);
			f.delete();
			i++;
		}
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	private void requestServices() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
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
					
					//pasamos los valores de rotación sobre cada eje al estado actual
					sOrientation[0]=sOrientation[0]*RAD2DEG;
					sOrientation[1]=sOrientation[1]*RAD2DEG;
					sOrientation[2]=sOrientation[2]*RAD2DEG;
					currentState.onRotationChanged(sOrientation);

					if (currentState.getId() == 1) {
						MenuState m = (MenuState) currentState;
						m.setOrientationText("X: " + sOrientation[0] * RAD2DEG	+ ", Y: " + sOrientation[1] * RAD2DEG + ",Z: "	+ sOrientation[2] * RAD2DEG);
					}					
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

	public void createOneButtonNotification(int title, int msg,DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.accept_spanish, listener);
		AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.TOP);
		alert.show();

	}
	
	public void createOneButtonNotification(String title, String msg,DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.accept_spanish, listener);
		AlertDialog alert = builder.create();
		alert.getWindow().setGravity(Gravity.TOP);
		alert.show();

	}

	public void createTwoButtonNotification(int title, int msg,	int positiveButton, int negativeButton,	DialogInterface.OnClickListener listenerPositiveButton,
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

	public void createTwoButtonNotification(String title, String msg,int positiveButton, int negativeButton,DialogInterface.OnClickListener listenerPositiveButton,
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
	
public void createDialog(String title,final CharSequence[] items,DialogInterface.OnClickListener clickListener,DialogInterface.OnKeyListener keyListener){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(FRAGUEL.getInstance());
		builder.setTitle(title);
		builder.setItems(items, clickListener);
		if (keyListener!=null)
			builder.setOnKeyListener(keyListener);
		
		AlertDialog alert = builder.create();
		alert.show();
}
public void createDialogWithRadioButtons(String title,final CharSequence[] items,DialogInterface.OnClickListener clickListener,DialogInterface.OnKeyListener keyListener){
	
	AlertDialog.Builder builder = new AlertDialog.Builder(FRAGUEL.getInstance());
	builder.setTitle(title);
	builder.setSingleChoiceItems(items, -1, clickListener);
	if (keyListener!=null)
		builder.setOnKeyListener(keyListener);
	
	AlertDialog alert = builder.create();
	alert.show();
}

public void createCustomDialog(String title, View view,DialogInterface.OnClickListener listenerPositiveButton,String button,DialogInterface.OnKeyListener keyListener){
	AlertDialog.Builder builder = new AlertDialog.Builder(FRAGUEL.getInstance());
	builder.setTitle(title);
	builder.setView(view);
	builder.setNeutralButton(button, listenerPositiveButton);
	//builder.setPositiveButton(button, listenerPositiveButton);
	if (keyListener!=null)
		builder.setOnKeyListener(keyListener);
	AlertDialog alert = builder.create();
	alert.show();
}

public void createCustomDialog(String title, View view,DialogInterface.OnClickListener listenerFirstButton,String firstButton,DialogInterface.OnClickListener listenerSecondButton,String secondButton,DialogInterface.OnKeyListener keyListener){
	AlertDialog.Builder builder = new AlertDialog.Builder(FRAGUEL.getInstance());
	builder.setTitle(title);
	builder.setView(view);
	builder.setNeutralButton(firstButton, listenerFirstButton);
	builder.setNegativeButton(secondButton, listenerSecondButton);
	//builder.setPositiveButton(firstButton, listenerFirstButton);
	//builder.setNegativeButton(secondButton, listenerSecondButton);
	if (keyListener!=null)
		builder.setOnKeyListener(keyListener);
	AlertDialog alert = builder.create();
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
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),"Instalando las librerías necesarias",	Toast.LENGTH_SHORT).show();
				FRAGUEL.getInstance().startActivity(installIntent);

			}
			tts = new TextToSpeech(FRAGUEL.getInstance().getApplicationContext(), this);
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
	
	private void initImageHandler(){
		imageHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				currentState.imageLoaded(msg.arg2);
			}
		};
	}
	
	private void initRouteHandler(){
		routeHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (currentState.id==MapState.STATE_ID){
					MapState.getInstance().routeLoaded();
					MapState.getInstance().getMapView().invalidate();
				}
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
			if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE) 
				tts.setLanguage(loc);
			else
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),R.string.language_no_available_spanish,Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),R.string.no_tts_spanish, Toast.LENGTH_LONG).show();
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
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),R.string.no_tts_spanish, Toast.LENGTH_LONG).show();
	}

	public void stopTalking() {
		if (tts != null)
			tts.stop();
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
	
	public void showProgressDialog(){
		dialog = ProgressDialog.show(FRAGUEL.getInstance(), "", 
				"Cargando. Por favor espere...", true);

	}

	public void dismissProgressDialog(){
		if(dialog!=null)
			dialog.dismiss();

	}
	
	public Pair<Route,PointOI> getRouteandPointbyId(int routeId,int pointId){
		Route route=null;
		PointOI point=null;
		for (Route r: routes){
			if (r.id==routeId){
				route=r;
				for (PointOI p: route.pointsOI){
					if (p.id==pointId){
						point=p;
						break;
					}
				}
				break;
			}
		}
		return new Pair<Route,PointOI>(route,point);
	}
	

	private void LoadRoutes(){
		int i=0;
		routes = new ArrayList<Route>();
		ResourceManager.getInstance().initialize("fraguel");
		String[] rutas= new File(ResourceManager.getInstance().getRootPath()+"/routes").list();
			while ( i<rutas.length){
				if (rutas[i].endsWith(".xml")){
					Route ruta=ResourceManager.getInstance().getXmlManager().readRoute(rutas[i].split(".xml")[0]);
					ruta.pointsOI = ResourceManager.getInstance().getXmlManager().readPointsOI(rutas[i].split(".xml")[0]);
					routes.add(ruta);
					new File(ResourceManager.getInstance().getRootPath() + "/tmp" + "/route"+ruta.id).mkdir();
				}
			i++;
		}
	}
		

}