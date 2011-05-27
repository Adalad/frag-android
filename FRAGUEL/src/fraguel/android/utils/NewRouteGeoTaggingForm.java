package fraguel.android.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class NewRouteGeoTaggingForm extends LinearLayout{

	private TextView fileName,routeName;
	public NewRouteGeoTaggingForm(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.setOrientation(LinearLayout.VERTICAL);
		
		TextView fileLabel = new TextView(context);
		fileLabel.setText("Nombre del fichero:");
		fileLabel.setPadding(5, 5, 5, 5);
		this.addView(fileLabel);
		fileName = new EditText(context);
		fileName.setPadding( 5, 0, 5, 0);
		//fileName.setHint("ruta1");
		fileName.setText("ruta1");
		this.addView(fileName);
		
		
		TextView nameLabel = new TextView(context);
		nameLabel.setText("Nombre de la ruta:");
		nameLabel.setPadding(5, 5, 5, 5);
		this.addView(nameLabel);
		routeName = new EditText(context);
		routeName.setPadding( 5, 0, 5, 0);
		//routeName.setHint("nombre1");
		routeName.setText("nombre1");
		this.addView(routeName);
	}


}
