package fraguel.android.states;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.lists.RouteManagerAdapter;


public class RouteManagerState extends State {

	public RouteManagerState() {
		super();
		id = 8;
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		LinearLayout ly= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		ly.setOrientation(LinearLayout.VERTICAL);
		
		TextView tv= new TextView(FRAGUEL.getInstance().getApplicationContext());
		tv.setText("Rutas descargadas en su terminal");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		ly.addView(tv);
		
		ListView container= new ListView(FRAGUEL.getInstance().getApplicationContext());
		container.setAdapter(new RouteManagerAdapter(FRAGUEL.getInstance().getApplicationContext()));
		ColorDrawable divcolor = new ColorDrawable(Color.DKGRAY);
		container.setDivider(divcolor);
		container.setDividerHeight(2);
		ly.addView(container);
		viewGroup=ly;
		
		FRAGUEL.getInstance().addView(viewGroup);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
