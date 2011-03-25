package fraguel.android;

import java.util.Iterator;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class State implements Comparable<State> {

	protected int id;
	protected ViewGroup viewGroup;
	protected PointOI point=null;
	protected Route route=null;
	

	public State() {
		super();
	}

	public abstract void load();

	public void unload() {
		if (FRAGUEL.getInstance().isTalking())
			FRAGUEL.getInstance().stopTalking();
		FRAGUEL.getInstance().removeAllViews();
	}

	@Override
	public int compareTo(State another) {
		if (id == another.id)
			return 0;
		return 1;
	}


	public int getId() {
		return id;
	}

	public ViewGroup getViewGroup() {
		return viewGroup;
	}
	
	//estos dos métodos son para probar los datos de los sensores

	public abstract void onClick(View v);

	public boolean onConfigurationChanged(Configuration newConfig){return false;}
	
	/**
	 * Method which provides the three rotation values in degrees respect to the global axis.
	 * @param values values[0]: azimuth, rotation around the Z axis. values[1]: pitch, rotation around the X axis. values[2]: roll, rotation around the Y axis. 
	 */
	public void onRotationChanged(float[] values){}
	/**
	 * Method which provides the current location using the GPS.
	 * @param values values[0]: Latitude. values[1]: Longitude. values[2]: Altitude.
	 */
	public void onLocationChanged(float[] values){}
	public boolean dispatchKeyEvent(KeyEvent event){
		
		if (event.getKeyCode()==KeyEvent.KEYCODE_BACK & event.getAction()==KeyEvent.ACTION_DOWN){ 
			FRAGUEL.getInstance().returnState();
			if (FRAGUEL.getInstance().isTalking())
				FRAGUEL.getInstance().stopTalking();
			return true;
		}else
			return false;
	}
	
	//Métodos para modificar menu de opciones y sus eventos desde un estado
	public  abstract Menu onCreateStateOptionsMenu(Menu menu);
	public  abstract boolean onStateOptionsItemSelected(MenuItem item);
	//public abstract void onTouch(View v, MotionEvent event);
	public void onActivityResult(int requestCode, int resultCode, Intent data){}
	public void onUtteranceCompleted(String arg0) {}
	public boolean loadData(Route r,PointOI p){return false;}
}
