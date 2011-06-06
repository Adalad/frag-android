package fraguel.android.notifications;

import android.content.DialogInterface;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.states.MapState;

public class RouteSelectionNotification implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		MapState.getInstance().setContextRoute(FRAGUEL.getInstance().routes.get(which));
		
		MapState.getInstance().setChooseAnotherRoute(false);
		MapState.getInstance().setChooseAnotherPoint(true);
		
		final CharSequence[] options = new CharSequence[MapState.getInstance().getContextRoute().pointsOI.size()];
		
		int i=0;
		for (PointOI p: MapState.getInstance().getContextRoute().pointsOI){
			options[i]=p.title;
			i++;
		}
		dialog.dismiss();
		FRAGUEL.getInstance().createDialog("Elegir punto de comienzo", options, new PointSelectionNotification(), new BackKeyNotification());
		
	}

}