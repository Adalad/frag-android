package fraguel.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class State implements Comparable<State> {

	protected int id;
	protected ViewGroup viewGroup;
	

	public State() {
		super();
	}

	public abstract void load();

	public void unload() {
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
	public void onRotationChanged(float[] values){}
	public void onLocationChanged(float[] values){}
	public boolean dispatchKeyEvent(KeyEvent event){
		
		if (event.getKeyCode()==event.KEYCODE_BACK & event.getAction()==event.ACTION_DOWN){ 
			FRAGUEL.getInstance().changeState(1);
			return true;
		}else
			return false;
	}
	
	//Métodos para modificar menu de opciones y sus eventos desde un estado
	public abstract Menu onCreateStateOptionsMenu(Menu menu);
	public abstract boolean onStateOptionsItemSelected(MenuItem item);
	//public abstract void onTouch(View v, MotionEvent event);
	public void onActivityResult(int requestCode, int resultCode, Intent data){}
}
