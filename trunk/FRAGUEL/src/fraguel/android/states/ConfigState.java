package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.State;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ConfigState extends State{
	
	public ConfigState() {
		super();
		id = 7;
	}
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		viewGroup=new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);
		
		ListView lv= new ListView(FRAGUEL.getInstance().getApplicationContext());
		TextView tv=new TextView(FRAGUEL.getInstance().getApplicationContext());
		tv.setText("Configuración");
		viewGroup.addView(tv);
		
		
		FRAGUEL.getInstance().addView(viewGroup);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
