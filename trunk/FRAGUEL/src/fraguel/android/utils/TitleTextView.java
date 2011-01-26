package fraguel.android.utils;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import android.content.Context;
import android.view.Gravity;

public class TitleTextView extends AutomaticScrollTextView{

	public TitleTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setGravity(Gravity.CENTER_HORIZONTAL);
		this.setTextAppearance(FRAGUEL.getInstance().getApplicationContext(), R.style.StateTitle);

	}

}
