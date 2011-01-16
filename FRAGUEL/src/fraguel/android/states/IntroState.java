package fraguel.android.states;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;



public class IntroState extends State{
	
	public static final int STATE_ID = 0;
	
	public IntroState() {
		super();
		id = STATE_ID;
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
				
		FRAGUEL.getInstance().getView().setBackgroundResource(R.drawable.intro);
		
		 Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		        	 FRAGUEL.getInstance().getView().setBackgroundColor(25); 
		        	 FRAGUEL.getInstance().changeState(MainMenuState.STATE_ID);
		         } 
		    }, 2000); 
		
				

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
			createGpsDisabledAlert();
		}
	}
	
	private void createGpsDisabledAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FRAGUEL.getInstance());
        builder.setTitle(R.string.title_no_gps_spanish);
        builder.setMessage(R.string.alert_gps_spanish);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.gps_enable_button_spanish,
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                	Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        			FRAGUEL.getInstance().startActivity(gpsOptionsIntent);
                                                }
                                        });
        builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Toast.makeText(FRAGUEL.getInstance(), R.string.no_gps_spanish, Toast.LENGTH_LONG).show();
                                }
                        });
        AlertDialog alert = builder.create();
        alert.getWindow().setGravity(Gravity.TOP);
        alert.show();
}




}
