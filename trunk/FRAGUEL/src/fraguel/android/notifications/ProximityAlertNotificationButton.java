package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import android.content.DialogInterface;
import android.widget.Toast;

public class ProximityAlertNotificationButton implements DialogInterface.OnClickListener{

	private Route route=null;
	private PointOI point=null;
	
	public ProximityAlertNotificationButton(Route r,PointOI p){
		route=r;
		point=p;
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		FRAGUEL.getInstance().changeState(20);
		FRAGUEL.getInstance().getGPS().setPointVisited(route, point);
		FRAGUEL.getInstance().getCurrentState().loadData(route, point);
	}

}
