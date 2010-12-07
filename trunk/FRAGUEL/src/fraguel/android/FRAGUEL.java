package fraguel.android;

import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.maps.MapActivity;

import fraguel.android.sensors.SensorListener;

public class FRAGUEL extends MapActivity implements OnClickListener {

	// Singleton
	public static FRAGUEL instance;
	// Sensors info
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
		SensorManager sm = (SensorManager) this
				.getSystemService(SENSOR_SERVICE);

		SensorListener l = new SensorListener();

		sm.registerListener(l, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(l, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(l, sm.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(l, sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(l, sm.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(l, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY),
				SensorManager.SENSOR_DELAY_NORMAL);

		// TODO a�adir estados
		states = new ArrayList<State>();
		addState(new MenuState(), true);
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
		// TODO
	}

	public void deactivateSensors() {
		// TODO
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}