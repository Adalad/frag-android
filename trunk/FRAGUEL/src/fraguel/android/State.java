package fraguel.android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class State implements Comparable<State> {

	protected int id;
	protected ViewGroup viewGroup;
	
	//estos textViews son pruebas
	protected TextView gps;
	protected TextView orientation;

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
	public void setGPSText(String s){
		gps.setText(s);
	}
	public void setOrientationText(String s){
		orientation.setText(s);
	}

	public abstract void onClick(View v);
	//public abstract void onTouch(View v, MotionEvent event);

}
