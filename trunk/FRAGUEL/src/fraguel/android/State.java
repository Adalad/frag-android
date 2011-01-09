package fraguel.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class State implements Comparable<State> {

	protected int id;
	protected ViewGroup viewGroup;
	
	// Menu variable buttons
	private static final int MENU_MAIN = 1;
	private static final int MENU_CONFIG = 2;
	private static final int MENU_ROUTE = 3;
	private static final int MENU_EXIT = 4;

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
	public Menu onCreateStateOptionsMenu(Menu menu){
		//Menu de opciones creado por defecto
		menu.add(0, MENU_MAIN, 0, R.string.menu_menu).setIcon(R.drawable.info);
		menu.add(0, MENU_CONFIG, 0, R.string.menu_config).setIcon(R.drawable.geotaging);
		menu.add(0, MENU_ROUTE, 0,R.string.menu_route).setIcon(R.drawable.info);
		menu.add(0, MENU_EXIT, 0, R.string.menu_exit).setIcon(R.drawable.info);
		
		return menu;}
	public boolean onStateOptionsItemSelected(MenuItem item){
		
		//Eventos del menu de opciones creados por defecto
		switch (item.getItemId()) {
		case MENU_MAIN:
			FRAGUEL.getInstance().changeState(1);
			return true;
		case MENU_CONFIG:
			Toast t1= Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT);
			t1.show();
			return true;
		case MENU_ROUTE:
			Toast t2= Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT);
			t2.show();
			return true;
		case MENU_EXIT:
			System.exit(0);
			return true;
		}
		
		return false;}
	//public abstract void onTouch(View v, MotionEvent event);
	public void onActivityResult(int requestCode, int resultCode, Intent data){}
}
