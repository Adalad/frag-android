package fraguel.android;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuState extends State {

	public MenuState() {
		super();
		id = 1;
	}

	@Override
	public void load() {
		viewGroup = new LinearLayout(FRAGUEL.getInstance()
				.getApplicationContext());

		TextView tv = new TextView(viewGroup.getContext());
		tv.setText("INTRODUCCION");
		viewGroup.addView(tv);

		Button b1 = new Button(viewGroup.getContext());
		b1.setText("Free Mode");
		b1.setId(1);
		b1.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b1);

		Button b4 = new Button(viewGroup.getContext());
		b4.setText("Video Mode");
		b4.setId(4);
		b4.setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		viewGroup.addView(b4);

		FRAGUEL.getInstance().addView(viewGroup);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

}
