package fraguel.android.notifications;

import java.io.File;

import fraguel.android.FRAGUEL;
import fraguel.android.resources.ResourceManager;
import fraguel.android.states.MainMenuState;
import fraguel.android.utils.NewRouteForm;
import fraguel.android.utils.NumberPicker;
import fraguel.android.utils.SavePointTemplate;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UserOptionsTemplateNotification implements DialogInterface.OnClickListener{

	private final CharSequence[] options = {"Nueva ruta", "Continuar ruta: "};
	private String[] rutas= new File(ResourceManager.getInstance().getRootPath()+"/user").list();
	private final CharSequence[] op = new CharSequence[1];
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
