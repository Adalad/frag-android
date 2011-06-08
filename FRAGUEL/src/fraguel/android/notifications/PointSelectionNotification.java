package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.states.MapState;
import fraguel.android.states.RouteInfoState;
import fraguel.android.utils.RouteInfoDialog;
import android.content.DialogInterface;

public class PointSelectionNotification implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		if (which==0)
			MapState.getInstance().getGPS().startRoute(MapState.getInstance().getContextRoute(), MapState.getInstance().getContextRoute().pointsOI.get(0));
		else
			MapState.getInstance().getGPS().startRoute(MapState.getInstance().getContextRoute(), MapState.getInstance().getContextRoute().pointsOI.get(which-1));
		
		RouteInfoDialog routeDialog= new RouteInfoDialog(FRAGUEL.getInstance(),MapState.getInstance().getContextRoute(),true);
		MapState.getInstance().setRouteInfoDialog(routeDialog);
		routeDialog.show();
		MapState.getInstance().setContextRoute(null);
		dialog.dismiss();
	}

}
