package fraguel.android.states;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.lists.InfoPointAdapter;

public class PointInfoState extends State{
	public static final int STATE_ID = 20;
	private GridView gridView;
	private TextView title;
	
	
	public PointInfoState() {
		super();
		id = STATE_ID;
	}
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		LinearLayout container= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		container.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		container.setOrientation(LinearLayout.VERTICAL);
		
		title= new TextView(FRAGUEL.getInstance().getApplicationContext());
		title.setText("Información disponible del punto de interés");
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		container.addView(title);
		
		
		gridView= new GridView(FRAGUEL.getInstance().getApplicationContext());
		gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		gridView.setNumColumns(2);
		Display display = ((WindowManager)FRAGUEL.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
		gridView.setColumnWidth(width/2);
		//gridView.setVerticalSpacing(10);
		//gridView.setHorizontalSpacing(10);
		gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gridView.setGravity(Gravity.CENTER);
		gridView.setAdapter(new InfoPointAdapter(FRAGUEL.getInstance().getApplicationContext()));
		//gridView.setBackgroundColor(Color.GREEN);
		gridView.setScrollContainer(false);
		
		container.addView(gridView);
		
        viewGroup=container;
        FRAGUEL.getInstance().addView(viewGroup);
		
	}
	
	public int getTitleWidth(){
		return title.getWidth();
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
