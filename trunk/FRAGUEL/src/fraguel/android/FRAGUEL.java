package fraguel.android;

import java.util.ArrayList;


import android.hardware.Sensor;
import android.hardware.SensorManager;
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
	private SensorListener sensorListener;
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

		// Sensors
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		sensorListener = new SensorListener();
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
	}

	public void deactivateSensors() {
		sensorManager.unregisterListener(sensorListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	

}