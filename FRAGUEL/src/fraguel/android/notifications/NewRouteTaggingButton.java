package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import android.content.DialogInterface;
import android.widget.Toast;

public class NewRouteTaggingButton implements DialogInterface.OnClickListener{

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Captura Coordenadas", Toast.LENGTH_SHORT).show();
	}

}
