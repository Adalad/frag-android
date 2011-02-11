package fraguel.android.states;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;

public class MainMenuState extends State {
	
	public static final int STATE_ID = 15;
	
	// Variables menu de opciones
	private static final int MENU_MAIN = 1;
	private static final int MENU_CONFIG = 2;
	private static final int MENU_ROUTE = 3;
	private static final int MENU_EXIT = 4;
	
	protected TextView gps;
	protected TextView orientation;
	public MainMenuState() {
		super();
		id = STATE_ID;
	}

	@Override
	public void load() {
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.mainmenu,  null);
		
		FRAGUEL.getInstance().addView(viewGroup);
		
						
		
		//bfm.setTextSize(20);
		//brm.setTextColor(100);
		//bim.;
		
		//viewGroup = new LinearLayout(FRAGUEL.getInstance()
		//		.getApplicationContext());
		//((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);
		

//		TextView tv = new TextView(viewGroup.getContext());
//		tv.setText("");
//		viewGroup.addView(tv);

		
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_freemode)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_routemode)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_interactivemode)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_credits)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_config)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_manager)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_exit)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		
		
		
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO rellenar con ids de los demas modos de juego
		switch (v.getId()) {
		case R.id.btn_freemode:
			FRAGUEL.getInstance().changeState(MapState.STATE_ID);
			break;
		case R.id.btn_routemode:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_interactivemode:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_manager:
			FRAGUEL.getInstance().changeState(RouteManagerState.STATE_ID);
			break;
		case R.id.btn_config:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_credits:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		
		default:
			System.exit(0);
		}
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
