package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import fraguel.android.resources.ResourceManager;
import android.content.DialogInterface;
import android.widget.Toast;

public class UserRouteNotification implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch(arg1){
		case 0:
			ResourceManager.getInstance().createXMLTemplate("new", 20, 5);
			break;
		case 1: 
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
	
    	}
    	
        arg0.dismiss();
	}

}
