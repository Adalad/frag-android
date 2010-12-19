package fraguel.android.states;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import android.os.Handler;
import android.view.View;
import android.widget.AbsoluteLayout;

public class IntroState extends State{
	public IntroState() {
		super();
		id = 0;
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
				
		FRAGUEL.getInstance().getView().setBackgroundResource(R.drawable.intro);
		
		 Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		        	 FRAGUEL.getInstance().getView().setBackgroundColor(25); 
		        	 FRAGUEL.getInstance().changeState(1);
		         } 
		    }, 2000); 
		
				

	}


	
        
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
