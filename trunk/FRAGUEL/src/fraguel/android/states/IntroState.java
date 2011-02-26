package fraguel.android.states;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.notifications.GPSNotificationButton;
import fraguel.android.notifications.WarningNotificationButton;



public class IntroState extends State{
	
	public static final int STATE_ID = 0;
	private ImageView imgView;
	
	public IntroState() {
		super();
		id = STATE_ID;
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		/*viewGroup= new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		viewGroup.setBackgroundColor(0);
		imgView= new ImageView(FRAGUEL.getInstance().getApplicationContext());
		imgView.setImageResource(R.drawable.fraguellogo);
		
		viewGroup.addView(imgView);*/
		
		//viewGroup= (LinearLayout)FRAGUEL.getInstance().findViewById(R.layout.intro);
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.intro,  null);
		viewGroup.setBackgroundResource(R.drawable.white);
		 AnimationSet set = new AnimationSet(true);
		 	
		 	  
		  //Mueve la View
		  Animation animation3 = new TranslateAnimation(
		      Animation.RELATIVE_TO_SELF, -5.0f, Animation.RELATIVE_TO_SELF, 0.0f,
		      Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
		  );
		  animation3.setDuration(2000);
		 
		  
		  //Mueve la View
		  Animation animation2 = new TranslateAnimation(
		      Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 5.0f,
		      Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
		  );
		  animation2.setDuration(2000);
		  animation2.setStartOffset(3000);
		  
		  
		  
		 //Juega con la transparencia de la View 
		 /* Animation animation = new AlphaAnimation(1.0f, 0.0f);
		  animation.setDuration(1000);
		  animation.setStartTime(1000);*/
		  
		  
		  
		  set.addAnimation(animation3);
		  set.addAnimation(animation2);
		  
		  LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);
		  viewGroup.setLayoutAnimation(controller);
		  
		  
		  FRAGUEL.getInstance().addView(viewGroup);
		
		  
		  
		  
		Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		        	FRAGUEL.getInstance().changeState(MainMenuState.STATE_ID);
		         } 
		    }, 5000); 
		
				

	}


	
        
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
	
	@Override
	public void unload(){
		super.unload();
		//comprobamos si tiene activado el GPS
		if (!FRAGUEL.getInstance().getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)) {	
			FRAGUEL.getInstance().createTwoButtonNotification(R.string.title_no_gps_spanish, R.string.alert_gps_spanish, R.string.gps_enable_button_spanish, R.string.gps_cancel_button_spanish, new GPSNotificationButton(), new WarningNotificationButton());
		}
		
	}
	
}
