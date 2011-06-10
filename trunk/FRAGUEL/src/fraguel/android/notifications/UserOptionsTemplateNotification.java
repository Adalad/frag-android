package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.states.MainMenuState;
import fraguel.android.utils.NewRouteForm;
import android.content.DialogInterface;

public class UserOptionsTemplateNotification implements DialogInterface.OnClickListener{

	private final CharSequence[] options = {"Nueva ruta", "Continuar ruta: "};
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch(arg1){
		case 0:
			MainMenuState state=(MainMenuState) (FRAGUEL.getInstance().getCurrentState());
			NewRouteForm form = new NewRouteForm(FRAGUEL.getInstance());
			state.setBlankForm(form);
			FRAGUEL.getInstance().createCustomDialog("Nueva ruta: plantilla en blanco", form, new NewRouteNotification(),"Aceptar", null);
			break;
		case 1: 
			FRAGUEL.getInstance().createDialog("Nueva ruta: Mediante GeoTagging", options, new GeoTaggingButton(), null);
			//FRAGUEL.getInstance().createCustomDialog("Nueva ruta: capturar punto", new SavePointTemplate(FRAGUEL.getInstance()),new WarningNotificationButton(), "Aceptar", null);
			//Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
	
    	}
    	
        arg0.dismiss();
	}
	
}
