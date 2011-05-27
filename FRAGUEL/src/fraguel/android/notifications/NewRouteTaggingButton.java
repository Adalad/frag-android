package fraguel.android.notifications;

import java.util.ArrayList;

import android.content.DialogInterface;
import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.states.MainMenuState;
import fraguel.android.utils.SavePointTemplate;

public class NewRouteTaggingButton implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		MainMenuState state = (MainMenuState)FRAGUEL.getInstance().getCurrentState();
		state.setRouteName(state.getGeoTaggingForm().getRouteName());
		state.setGeoTaggingPoints(new ArrayList<PointOI>());
		
		FRAGUEL.getInstance().createCustomDialog(state.getGeoTaggingForm().getRouteName()+": captura de coordenadas", new SavePointTemplate(FRAGUEL.getInstance().getApplicationContext()), new CaptureCoordinatesButton(), "Capturar", null, "Finalizar ruta", null);
		dialog.dismiss();
	}

}
