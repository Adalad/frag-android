package fraguel.android.utils;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class InfoText extends TextView{

	public InfoText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setTextAppearance(FRAGUEL.getInstance().getApplicationContext(), R.style.ScrollText);
	}

}
