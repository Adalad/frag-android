package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class InfoState extends State{

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
		
		
		
		viewGroup = new LinearLayout(FRAGUEL.getInstance()
				.getApplicationContext());
		((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
