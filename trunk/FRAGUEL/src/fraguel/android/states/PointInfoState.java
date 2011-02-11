package fraguel.android.states;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.lists.InfoPointAdapter;
import fraguel.android.utils.TitleTextView;

public class PointInfoState extends State{
	public static final int STATE_ID = 20;
	private GridView gridView;
	private TitleTextView title;
	
	
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
		//container.setBackgroundResource(R.drawable.aqua);
		
		title= new TitleTextView(FRAGUEL.getInstance().getApplicationContext());
		title.setText("Información disponible del punto de interés");
		//title.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		title.setMaxHeight(20);
		
		container.addView(title);
		
		gridView= new GridView(FRAGUEL.getInstance().getApplicationContext());
		gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		gridView.setNumColumns(2);
		Display display = ((WindowManager)FRAGUEL.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
		gridView.setColumnWidth(width/2);
		gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gridView.setGravity(Gravity.CENTER);
		gridView.setAdapter(new InfoPointAdapter(FRAGUEL.getInstance().getApplicationContext()));
		gridView.setScrollContainer(false);
		setGridViewListener();
		
		
		container.addView(gridView);
		
        viewGroup=container;
        FRAGUEL.getInstance().addView(viewGroup);
		
	}
	
	
	public void setGridViewListener(){
		
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				switch (position){
				
				case 0:
					FRAGUEL.getInstance().changeState(6);
					break;
				case 1:

					FRAGUEL.getInstance().changeState(4);
					break;
					
				case 2:

					FRAGUEL.getInstance().changeState(9);
					break;
					
				case 3:

					FRAGUEL.getInstance().changeState(5);
					break;
				
				}
			}});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void unload(){
		FRAGUEL.getInstance().getGPS().setDialogDisplayed(false);
		super.unload();
	}
	
	@Override
	public boolean loadData(Route route, PointOI point){
		boolean ok=super.loadData(route, point);
		
		if (ok){
			String titleText;
			titleText=route.name+" - "+point.title;
			title.setText(titleText);
		}
		return ok;
		
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
