package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class InfoState extends State{

	public static final int STATE_ID = 6;
	public static final int INFOSTATE_STOP_RECORD=1;
	public static final int INFOSTATE_REPEAT_RECORD=2;
	
	
	
	private TextView title;
	private TextView text;
	public InfoState() {
		super();
		id = STATE_ID;
	}
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		//Creamos e importamos el layout del xml
		/*LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.info,  null);*/
		LinearLayout ly= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		ly.setOrientation(LinearLayout.VERTICAL);
		viewGroup=ly;
		FRAGUEL.getInstance().addView(viewGroup);
		
		
		title= new TextView(FRAGUEL.getInstance().getApplicationContext());
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setText("INFORMACIÓN");
		title.setPadding(0, 0, 0, 10);
		
		viewGroup.addView(title);
		
		
		ScrollView container= new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		text= new TextView(FRAGUEL.getInstance().getApplicationContext());
		text.setText("Toda la información referente al punto de interés. Ahora mismo te estoy hablando, así que enciende los altavoces para que puedas oirme." +
				" Ahora voy a intentar que el usuario, es decir, tú, puedas parar y arrancar el sonido cuando desees. Espero que lo disfrutes."+
				"\n"+"Como podemos ver a en la esquina superior izquierda se divisa una gárgola singular."+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+
				"A la izquierda de nosotros, mirando en la parte inferior se ve un jabalí pardo, típico de la zona central del reino de Wisconsin (Alabama)."+"\n"+
				"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"¿Cómo va el rollo?");
		container.addView(text);
		
		FRAGUEL.getInstance().talk((String)text.getText());
		
		
		
		viewGroup.addView(container);
		
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.clear();
		
		menu.add(0, INFOSTATE_STOP_RECORD, 0, R.string.infostate_menu_stop).setIcon(R.drawable.stop);
		menu.add(0, INFOSTATE_REPEAT_RECORD, 0, R.string.infostate_menu_repeat).setIcon(R.drawable.play);
		
		return menu;
		
	}


	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {

		case INFOSTATE_STOP_RECORD:
			FRAGUEL.getInstance().stopTalking();
			return true;

		case INFOSTATE_REPEAT_RECORD:
			FRAGUEL.getInstance().talk((String)text.getText());
			return true;
		}
		return false;
	}


}
