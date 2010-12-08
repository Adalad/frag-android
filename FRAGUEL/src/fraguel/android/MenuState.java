package fraguel.android;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuState extends State {
	
	//public static final int STATE_ID = 1;

	public MenuState() {
		super();
		id = 1;
	}

	@Override
	public void load() {
		viewGroup = new LinearLayout(FRAGUEL.getInstance()
				.getApplicationContext());
		((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);

		TextView tv = new TextView(viewGroup.getContext());
		tv.setText("INTRODUCCION");
		viewGroup.addView(tv);

		Button b1 = new Button(viewGroup.getContext());
		b1.setText("Mapas");
		b1.setId(1);
		b1.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b1);

		Button b2 = new Button(viewGroup.getContext());
		b2.setText("Video");
		b2.setId(2);
		b2.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b2);

		Button b3 = new Button(viewGroup.getContext());
		b3.setText("Imagenes");
		b3.setId(3);
		b3.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b3);

		Button b4 = new Button(viewGroup.getContext());
		b4.setText("Realidad aumentada");
		b4.setId(4);
		b4.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b4);

		Button b5 = new Button(viewGroup.getContext());
		b5.setText("Informacion");
		b5.setId(5);
		b5.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b5);

		Button b6 = new Button(viewGroup.getContext());
		b6.setText("Configuración");
		b6.setId(6);
		b6.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b6);

		Button b7 = new Button(viewGroup.getContext());
		b7.setText("Salir");
		b7.setId(7);
		b7.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b7);

		
		FRAGUEL.getInstance().addView(viewGroup);
	}

	@Override
	public void onClick(View v) {
		// TODO rellenar con ids de estados
		switch (v.getId()) {
		case 1:
			FRAGUEL.getInstance().changeState(2);
			break;
		case 2:
			FRAGUEL.getInstance().changeState(3);
			break;
		case 3:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 4:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 5:
			FRAGUEL.getInstance().changeState(id);
			break;
		case 6:
			FRAGUEL.getInstance().changeState(id);
			break;
		default:
			System.exit(0);
		}
	}

}
