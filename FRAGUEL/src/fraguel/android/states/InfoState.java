package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.utils.InfoText;
import fraguel.android.utils.TitleTextView;
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
	
	
	
	private TitleTextView title;
	private InfoText text;
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
		
		title= new TitleTextView(FRAGUEL.getInstance().getApplicationContext());
		title.setText("INFORMACIÓN");
		
		viewGroup.addView(title);
		
		
		ScrollView container= new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		text= new InfoText(FRAGUEL.getInstance().getApplicationContext());
		text.setText("");
		if (route!=null && point!=null)
			this.loadData(route, point);
		container.addView(text);
		
		
		
		
		
		viewGroup.addView(container);
		
		
	}

	@Override
	public boolean loadData(Route r, PointOI p){
			this.route=r;
			this.point=p;
			
			text.setText(point.pointdescription);
			FRAGUEL.getInstance().talk((String)text.getText());
			return true;
		
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
