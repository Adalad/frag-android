package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.states.MapState;
import fraguel.android.states.RouteInfoState;
import android.content.DialogInterface;

public class PointSelectionNotification implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		MapState.getInstance().setChooseAnotherPoint(false);
		FRAGUEL.getInstance().getGPS().startRoute(MapState.getInstance().getContextRoute(), MapState.getInstance().getContextRoute().pointsOI.get(which));
		FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
		FRAGUEL.getInstance().getCurrentState().loadData(MapState.getInstance().getContextRoute(), MapState.getInstance().getContextRoute().pointsOI.get(which));
		MapState.getInstance().setContextRoute(null);
		dialog.dismiss();
	}

}
