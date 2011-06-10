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
import android.content.DialogInterface;
import android.widget.Toast;

public class ExportPointsButton implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		MainMenuState state = (MainMenuState)FRAGUEL.getInstance().getCurrentState();
		switch(which){
		case 0:
			toTempFile();
		break;
		case 1:
			ResourceManager.getInstance().createXMLFromPoints(state.getRouteName(), state.getRouteName(), 21, state.getGeoTaggingPoints());
		break;
		}
	}
	
	private void toTempFile(){
		MainMenuState state = (MainMenuState)FRAGUEL.getInstance().getCurrentState();
		File file = new File(ResourceManager.getInstance().getRootPath()+"/user/"+state.getRouteName()+".tmp");
		if (file.exists()){
			file.delete();
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),"El archivo ya existía y se ha sobrescrito" , Toast.LENGTH_SHORT).show();
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			for (PointOI p : state.getGeoTaggingPoints()){
				oos.writeObject(p);
			}
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),"Error al grabar el archivo" , Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),"Error al grabar el archivo" , Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),"Datos guardados con éxito" , Toast.LENGTH_SHORT).show();
	}

}
