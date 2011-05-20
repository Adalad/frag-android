package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.states.MapState;
import fraguel.android.states.RouteInfoState;
import android.content.DialogInterface;

public class StartRouteNotification implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which){
		case 0:
			MapState.getInstance().setContextMenuDisplayed(false);
			FRAGUEL.getInstance().closeContextMenu();
			FRAGUEL.getInstance().getGPS().startRoute(MapState.getInstance().getRoute(), MapState.getInstance().getRoute().pointsOI.get(0));
			FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(MapState.getInstance().getRoute(), MapState.getInstance().getRoute().pointsOI.get(0) );
			break;
		case 1: 
			MapState.getInstance().setContextMenuDisplayed(false);
			FRAGUEL.getInstance().closeContextMenu();
			FRAGUEL.getInstance().getGPS().startRoute(MapState.getInstance().getRoute(), MapState.getInstance().getPointOI());
			FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(MapState.getInstance().getRoute(), MapState.getInstance().getPointOI());
			break;
		case 2: 
			MapState.getInstance().setContextMenuDisplayed(false);
			FRAGUEL.getInstance().closeContextMenu();
			MapState.getInstance().setChooseAnotherRoute(true);
			FRAGUEL.getInstance().openContextMenu(MapState.getInstance().getMapView());
			break;
	
    	}
    	
        dialog.dismiss();
	}

}
