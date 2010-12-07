package fraguel.android.sensors;

import fraguel.android.FRAGUEL;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener {

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {

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
		} else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			FRAGUEL.getInstance().sGyroscope[0] = event.values[0];
			FRAGUEL.getInstance().sGyroscope[1] = event.values[1];
			FRAGUEL.getInstance().sGyroscope[2] = event.values[2];
		} else if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
			FRAGUEL.getInstance().sTemperature = event.values[0];
		} else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
			FRAGUEL.getInstance().sLight = event.values[0];
		} else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
			FRAGUEL.getInstance().sProximity = event.values[0];
		}

	}

}
