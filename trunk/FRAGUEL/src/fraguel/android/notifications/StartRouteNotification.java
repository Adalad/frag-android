package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.Route;
import fraguel.android.states.MapState;
import fraguel.android.states.RouteInfoState;
import fraguel.android.utils.RouteInfoDialog;
import android.content.DialogInterface;

public class StartRouteNotification implements DialogInterface.OnClickListener{
	//final CharSequence[] options = new CharSequence[FRAGUEL.getInstance().routes.size()];
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		RouteInfoDialog routeDialog;
		switch(which){
		case 0:
			MapState.getInstance().setContextMenuDisplayed(false);
			MapState.getInstance().getGPS().startRoute(MapState.getInstance().getRoute(), MapState.getInstance().getRoute().pointsOI.get(0));
			routeDialog= new RouteInfoDialog(FRAGUEL.getInstance(),MapState.getInstance().getRoute(),true);
			MapState.getInstance().setRouteInfoDialog(routeDialog);
			routeDialog.show();
			break;
		case 1: 
			MapState.getInstance().setContextMenuDisplayed(false);
			MapState.getInstance().getGPS().startRoute(MapState.getInstance().getRoute(), MapState.getInstance().getPointOI());
			routeDialog= new RouteInfoDialog(FRAGUEL.getInstance(),MapState.getInstance().getRoute(),true);
			MapState.getInstance().setRouteInfoDialog(routeDialog);
			routeDialog.show();
			break;
		case 2: 
			MapState.getInstance().setContextMenuDisplayed(false);
			int i=0;
			
			for (Route r: FRAGUEL.getInstance().routes){
				MapState.getInstance().rutas[i]=r.name;
				i++;
			}
			
			FRAGUEL.getInstance().createDialog("Elegir ruta", MapState.getInstance().rutas, new RouteSelectionNotification(), new BackKeyNotification());
			
			break;
	
    	}
        dialog.dismiss();
	}

}
