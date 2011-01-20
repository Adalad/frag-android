package fraguel.android.states;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import fraguel.android.FRAGUEL;
import fraguel.android.State;

public class PointInfoState extends State{

	private GridView gridView;
	@Override
	public void load() {
		// TODO Auto-generated method stub
		gridView= new GridView(FRAGUEL.getInstance().getApplicationContext());
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}

}
