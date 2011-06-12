package fraguel.android.notifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.resources.ResourceManager;
import fraguel.android.states.MainMenuState;
import fraguel.android.utils.NewRouteGeoTaggingForm;
import fraguel.android.utils.SavePointTemplate;
import android.content.DialogInterface;
import android.widget.Toast;

public class GeoTaggingButton implements DialogInterface.OnClickListener{

	private MainMenuState state = (MainMenuState)FRAGUEL.getInstance().getCurrentState();
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch(which){
		case 0:
			NewRouteGeoTaggingForm form = new NewRouteGeoTaggingForm(FRAGUEL.getInstance().getApplicationContext());
			state.setGeoTaggingForm(form);
			FRAGUEL.getInstance().createCustomDialog("Nueva ruta mediante GeoTagging", form, new NewRouteTaggingButton(), "Aceptar", null);
			break;
		default:
			String[] files = state.getTempFiles();
			String route = files[which-1].split(".xml")[0];
			ResourceManager.getInstance().fromTmpFile(ResourceManager.getInstance().getRootPath()+"/user/"+files[which-1],route);
			SavePointTemplate capturer = state.getCoordinatesCapturer();
			
			if (capturer==null){
				capturer= new SavePointTemplate(FRAGUEL.getInstance().getApplicationContext());
				state.setCoordinatesCapturer(capturer);
			}
			FRAGUEL.getInstance().createCustomDialog(state.getGeoTaggingForm().getRouteName()+": captura de coordenadas",capturer , new CaptureCoordinatesButton(), "Capturar", new EndCaptureButton(), "Finalizar", null);
			break;
		
		}
		dialog.dismiss();
	}
	
	
	
}
