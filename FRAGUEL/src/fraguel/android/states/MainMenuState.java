package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
		
		Button bfm = (Button) FRAGUEL.getInstance().findViewById(R.id.btn_freemode);
		Button brm = (Button) FRAGUEL.getInstance().findViewById(R.id.btn_routemode);
		Button bim = (Button) FRAGUEL.getInstance().findViewById(R.id.btn_interactivemode);
		
		//bfm.setTextSize(20);
		//brm.setTextColor(100);
		//bim.;
		/*
		viewGroup = new LinearLayout(FRAGUEL.getInstance()
				.getApplicationContext());
		((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);
		

		TextView tv = new TextView(viewGroup.getContext());
		tv.setText("");
		viewGroup.addView(tv);

		Button b1 = new Button(viewGroup.getContext());
		b1.setText("Modo Libre");
		b1.setId(1);
		b1.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b1);

		Button b2 = new Button(viewGroup.getContext());
		b2.setText("Modo Ruta");
		b2.setId(2);
		b2.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b2);

		Button b3 = new Button(viewGroup.getContext());
		b3.setText("Modo Interactivo");
		b3.setId(3);
		b3.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b3);
		
		FRAGUEL.getInstance().addView(viewGroup);*/
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO rellenar con ids de los demas modos de juego
		switch (v.getId()) {
		case 1:
			FRAGUEL.getInstance().changeState(MapState.STATE_ID);
			break;
		case 2:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case 3:
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
