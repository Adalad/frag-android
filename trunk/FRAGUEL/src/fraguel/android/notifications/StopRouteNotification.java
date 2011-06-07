package fraguel.android.notifications;

import android.content.DialogInterface;
import fraguel.android.FRAGUEL;
import fraguel.android.states.MapState;

public class StopRouteNotification implements DialogInterface.OnClickListener{

	
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
			MapState.getInstance().setContextMenuDisplayed(false);
			FRAGUEL.getInstance().getGPS().stopRoute();
			FRAGUEL.getInstance().stopTalking();
	}

}