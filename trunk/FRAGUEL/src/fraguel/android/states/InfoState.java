package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoState extends State{

	private TextView title;
	public InfoState() {
		super();
		id = 6;
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
		
		viewGroup.addView(title);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
