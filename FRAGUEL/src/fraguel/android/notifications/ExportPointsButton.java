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
			ResourceManager.getInstance().toTempFile();
		break;
		case 1:
			ResourceManager.getInstance().createXMLFromPoints(state.getRouteName(), state.getRouteName(), -1, state.getGeoTaggingPoints());
		break;
		}
	}
	

}
