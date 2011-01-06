package fraguel.android.states;

import java.util.Locale;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoState extends State implements  TextToSpeech.OnInitListener{

	public static final int STATE_ID = 6;
	
	private TextView title;
	private TextToSpeech tts;
	private TextView text;
	public InfoState() {
		super();
		id = STATE_ID;
	}
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		//Creamos e importamos el layout del xml
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.info,  null);
		FRAGUEL.getInstance().addView(viewGroup);
		
		
		title= new TextView(FRAGUEL.getInstance().getApplicationContext());
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setText("INFORMACIÓN");
		title.setPadding(0, 0, 0, 10);
		
		viewGroup.addView(title);
		
		
		ScrollView container= new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		text= new TextView(FRAGUEL.getInstance().getApplicationContext());
		text.setText("Toda la información referente al punto de interés. Ahora mismo te estoy hablando, así que enciende los altavoces para que puedas oirme." +
				" Ahora voy a intentar que el usuario, es decir, tú, puedas parar y arrancar el sonido cuando desees. Espero que lo disfrutes.");
		container.addView(text);
		
		tts= new TextToSpeech(FRAGUEL.getInstance().getApplicationContext(), this);
		
		
		viewGroup.addView(container);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		Locale loc = new Locale("es", "","");
		if(tts.isLanguageAvailable(loc)==TextToSpeech.LANG_AVAILABLE){
			tts.setLanguage(loc);
			tts.speak((String)text.getText(), TextToSpeech.QUEUE_FLUSH, null);
		}
		else
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Lengua española no disponible", Toast.LENGTH_SHORT).show();
		
	}


	@Override
	public void unload() {
		// TODO Auto-generated method stub
		tts.shutdown();
		super.unload();
		
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
