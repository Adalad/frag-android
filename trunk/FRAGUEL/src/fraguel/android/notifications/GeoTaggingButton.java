package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.states.MainMenuState;
import fraguel.android.utils.NewRouteGeoTaggingForm;
import android.content.DialogInterface;
import android.widget.Toast;

public class GeoTaggingButton implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which){
		case 0:
			NewRouteGeoTaggingForm form = new NewRouteGeoTaggingForm(FRAGUEL.getInstance().getApplicationContext());
			MainMenuState state = (MainMenuState)FRAGUEL.getInstance().getCurrentState();
			state.setGeoTaggingForm(form);
			FRAGUEL.getInstance().createCustomDialog("Nueva ruta mediante GeoTagging", form, new NewRouteTaggingButton(), "Aceptar", null);
			break;
		case 1:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Falta por hacer", Toast.LENGTH_SHORT).show();
			break;
		
		}
		dialog.dismiss();
	}

}
