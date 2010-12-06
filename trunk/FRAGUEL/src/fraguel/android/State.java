package fraguel.android;

import android.view.View;
import android.view.ViewGroup;

public abstract class State implements Comparable<State> {

	public int id;
	public ViewGroup viewGroup;

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

	public abstract void onClick(View v);

}
