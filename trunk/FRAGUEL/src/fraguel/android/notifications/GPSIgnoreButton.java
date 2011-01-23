package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;
import android.content.DialogInterface;

public class GPSIgnoreButton implements DialogInterface.OnClickListener{

	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		FRAGUEL.getInstance().getGPS().setDialogDisplayed(false);
		dialog.dismiss();
	}

}
