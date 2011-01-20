package fraguel.android.states;

import android.content.Context;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import fraguel.android.FRAGUEL;
import fraguel.android.State;

public class PointInfoState extends State{
	public static final int STATE_ID = 9;
	private GridView gridView;
	@Override
	public void load() {
		// TODO Auto-generated method stub
		gridView= new GridView(FRAGUEL.getInstance().getApplicationContext());
		gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		gridView.setNumColumns(2);
		gridView.setPadding(5, 5, 5, 5);
		Display display = ((WindowManager)FRAGUEL.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
		//gridView.setColumnWidth(width/2);
		
		
        viewGroup=gridView;
        FRAGUEL.getInstance().addView(viewGroup);
		
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
