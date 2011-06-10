package fraguel.android.notifications;

import fraguel.android.FRAGUEL;
import android.app.Dialog;
import android.content.DialogInterface;

public class EndCaptureButton implements DialogInterface.OnClickListener{

	private final CharSequence[] options = {"Guardar temporalmente", "Exportar a XML"};
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		FRAGUEL.getInstance().createDialog("Finalizar ruta", options, new ExportPointsButton(), null);
	}

}
