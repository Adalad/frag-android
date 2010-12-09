package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.view.View;

public class IntroState extends State{
	public IntroState() {
		super();
		id = 0;
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
		FRAGUEL.getInstance().view.setBackgroundResource(R.drawable.intro);
		/*viewGroup= new RelativeLayout(FRAGUEL.getInstance().getApplicationContext());
		viewGroup.setBackgroundResource(R.drawable.intro);
		viewGroup.setId(99);
		FRAGUEL.getInstance().addView(viewGroup);
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(5000); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				FRAGUEL.getInstance().changeState(1);
			}
		};
		t.start();*/

	}


	
        
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FRAGUEL.getInstance().view.setBackgroundColor(255);
		FRAGUEL.getInstance().changeState(1);
		
	}


}
