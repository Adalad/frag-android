package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.utils.NewRouteGeoTaggingForm;
import android.content.DialogInterface;
import android.widget.Toast;

public class GeoTaggingButton implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which){
		case 0:
			FRAGUEL.getInstance().createCustomDialog("Nueva ruta mediante GeoTagging", new NewRouteGeoTaggingForm(FRAGUEL.getInstance().getApplicationContext()), new NewRouteTaggingButton(), "Aceptar", null);
			break;
		case 1:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Falta por hacer", Toast.LENGTH_SHORT).show();
			break;
		
		}
		dialog.dismiss();
	}

}
