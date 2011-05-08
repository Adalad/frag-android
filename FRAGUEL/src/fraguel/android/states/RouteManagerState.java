package fraguel.android.states;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.lists.RouteManagerAdapter;
import fraguel.android.utils.TitleTextView;


public class RouteManagerState extends State {
	
	public static final int STATE_ID = 8;
	private static RouteManagerState instance;
	// Variables de los botones del menu
	private static final int ROUTEMANAGERSTATE_ADDROUTE = 1;
	private static final int ROUTEMANAGERSTATE_DELETEROUTE = 2;

	private LinearLayout container;
	private RouteManagerAdapter adapter;
	private TitleTextView title;
	private ListView list;
	private ArrayList<String> currentDataTitle;
	private ArrayList<String> currentDataDescrip;
	private ArrayList<String> currentDataImages;
	//0->routes,1->points,2->pointData
	private int internalState;
	private int selectedRoute,selectedPoint;
	
	
	public RouteManagerState() {
		super();
		id = STATE_ID;
		// Singleton
		instance = this;
	}

	public static RouteManagerState getInstance() {
		if (instance == null)
			instance = new RouteManagerState();
		return instance;
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		container= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		container.setOrientation(LinearLayout.VERTICAL);
		title= new TitleTextView(FRAGUEL.getInstance().getApplicationContext());
				
		container.addView(title);
		loadRoutes(0);
		viewGroup=container;
		selectedRoute=0;
		selectedPoint=0;
		
		FRAGUEL.getInstance().addView(viewGroup);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private void addItemClickListenerToListView(){
		
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				switch (internalState){
				case 0:
					selectedRoute=position;
					loadPoints(selectedRoute);
					break;
				case 1:
					selectedPoint=position;
					FRAGUEL.getInstance().changeState(PointInfoState.STATE_ID);
					Route r= FRAGUEL.getInstance().routes.get(selectedRoute);
					FRAGUEL.getInstance().getCurrentState().loadData(r, r.pointsOI.get(selectedPoint));
					break;
				}
				
			}
			
		});
	}
	
	
private void addOnItemLongClickListenerToListView(){
		
		list.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				switch (internalState){
				case 0:
					Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Long Press routes", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Long Press points", Toast.LENGTH_SHORT).show();
					break;
				}
				return true;
			}
			
		});
	}
	
	private void loadPoints(int route){
		System.gc();
		title.setText(R.string.routemanagerstate_title_points_spanish);
		title.setText(title.getText()+" "+ route);
		container.removeView(list);
		setAdapter();
		currentDataTitle=new ArrayList<String>();
		currentDataDescrip= new ArrayList<String>();
		currentDataImages= new ArrayList<String>();
		ArrayList<PointOI> points= FRAGUEL.getInstance().routes.get(route).pointsOI;
		for (PointOI p : points){
			currentDataTitle.add(p.title);
			currentDataDescrip.add(p.icon);
			currentDataImages.add(p.image);
		}
		adapter.setTitle(currentDataTitle);
		adapter.setDescription(currentDataDescrip);
		adapter.setImages(currentDataImages);
		internalState=1;
		list.setSelection(selectedPoint);
	}
	
		
	private void loadRoutes(int routeFocus){
		System.gc();
		container.removeView(list);
		title.setText(R.string.routemanagerstate_title_routes_spanish);
		setAdapter();
		currentDataTitle=new ArrayList<String>();
		currentDataDescrip= new ArrayList<String>();
		currentDataImages= new ArrayList<String>();
		for (Route r : FRAGUEL.getInstance().routes) {
			currentDataTitle.add(r.name);
			currentDataDescrip.add(r.description);
			currentDataImages.add(r.icon);
		}
		adapter.setTitle(currentDataTitle);
		adapter.setDescription(currentDataDescrip);
		adapter.setImages(currentDataImages);
		internalState=0;
		list.setSelection(routeFocus);
		
	}
	
	private void setAdapter(){
		list= new ListView(FRAGUEL.getInstance().getApplicationContext());
		list.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		list.setCacheColorHint(0);
		//list.setSelector(R.drawable.selector);
		list.setDrawSelectorOnTop(true);
		adapter = new RouteManagerAdapter(FRAGUEL.getInstance().getApplicationContext());
		list.setAdapter(adapter);
		ColorDrawable divcolor = new ColorDrawable(Color.DKGRAY);
		list.setDivider(divcolor);
		list.setDividerHeight(2);
		addItemClickListenerToListView();
		addOnItemLongClickListenerToListView();
		container.addView(list);
		
		
	}
	
	
	public boolean dispatchKeyEvent(KeyEvent event){
		if (event.getKeyCode()==event.KEYCODE_BACK & event.getAction()==event.ACTION_DOWN){
			if(internalState==1){
				loadRoutes(selectedRoute);
				return true;
			}
		}
	return super.dispatchKeyEvent(event);
	}

	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//Borramos el menu de opciones anterior
		menu.clear();
		//Añadimos las opciones del menu
		menu.add(0,ROUTEMANAGERSTATE_ADDROUTE, 0, R.string.routemanagerstate_menu_addroute).setIcon(R.drawable.ic_menu_routeadd);
		menu.add(0,ROUTEMANAGERSTATE_DELETEROUTE, 0, R.string.routemanagerstate_menu_deleteroute).setIcon(R.drawable.ic_menu_routerem);
		
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case ROUTEMANAGERSTATE_ADDROUTE:
			loadRoutes(selectedRoute);
			return true;

		case ROUTEMANAGERSTATE_DELETEROUTE:
			loadRoutes(selectedRoute);
			return true;
		}
		return false;
	}
	
	public int getInternalState(){
		return internalState;
	}
	public int getSelectedRoute(){
		return selectedRoute;
	}
	@Override
	public void imageLoaded(int index){
		list.invalidate();		
	}
}
