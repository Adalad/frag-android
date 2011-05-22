package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.Route;
import fraguel.android.states.MapState;
import fraguel.android.states.RouteInfoState;
import android.content.DialogInterface;

public class StartRouteNotification implements DialogInterface.OnClickListener{
	final CharSequence[] options = new CharSequence[FRAGUEL.getInstance().routes.size()];
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which){
		case 0:
			MapState.getInstance().setContextMenuDisplayed(false);
			FRAGUEL.getInstance().getGPS().startRoute(MapState.getInstance().getRoute(), MapState.getInstance().getRoute().pointsOI.get(0));
			FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(MapState.getInstance().getRoute(), MapState.getInstance().getRoute().pointsOI.get(0) );
			break;
		case 1: 
			MapState.getInstance().setContextMenuDisplayed(false);
			FRAGUEL.getInstance().getGPS().startRoute(MapState.getInstance().getRoute(), MapState.getInstance().getPointOI());
			FRAGUEL.getInstance().changeState(RouteInfoState.STATE_ID);
			FRAGUEL.getInstance().getCurrentState().loadData(MapState.getInstance().getRoute(), MapState.getInstance().getPointOI());
			break;
		case 2: 
			MapState.getInstance().setContextMenuDisplayed(false);
			MapState.getInstance().setChooseAnotherRoute(true);
			int i=0;
			for (Route r: FRAGUEL.getInstance().routes){
				options[i]=r.name;
				i++;
			}
			
			FRAGUEL.getInstance().createDialog("Elegir ruta", options, new RouteSelectionNotification(), new BackKeyNotification());
			
			break;
	
    	}
        dialog.dismiss();
	}

}
