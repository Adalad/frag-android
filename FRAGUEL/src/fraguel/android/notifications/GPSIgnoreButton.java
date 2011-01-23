package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import android.content.DialogInterface;

public class GPSIgnoreButton implements DialogInterface.OnClickListener{

	
	private Route route=null;
	private PointOI point=null;
	
	public GPSIgnoreButton(Route r,PointOI p){
		route=r;
		point=p;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		FRAGUEL.getInstance().getGPS().setDialogDisplayed(false);
		FRAGUEL.getInstance().getGPS().setPointVisited(route, point);
		dialog.dismiss();
	}

}
