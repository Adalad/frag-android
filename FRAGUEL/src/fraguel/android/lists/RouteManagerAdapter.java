package fraguel.android.lists;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RouteManagerAdapter extends BaseAdapter{

	private String[] authors={"Bernardo", "Alberto", "Gabriel"};
	private String[] surnames={"Pericacho Sánchez", "Guillén", "Peñas Rodríguez"};
	private String[] images={"R.drawable.museumsalango","R.drawable.museo","R.drawable.museumsalango"};
	private Context context;
	
	public RouteManagerAdapter(Context c) {
        context = c;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return authors.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewGroup row= new LinearLayout(context);
		((LinearLayout) row).setOrientation(LinearLayout.HORIZONTAL);
		
		row.setBackgroundColor((position & 1) == 1 ? Color.WHITE : Color.LTGRAY);
		
		ImageView drawable = new ImageView(context);
		drawable.setImageResource(R.drawable.museumsalango);
		drawable.setLayoutParams(new LayoutParams(40,40));
		drawable.setPadding(10, 5, 5, 5);
		
		
		LinearLayout text = new LinearLayout(context);
		text.setOrientation(LinearLayout.VERTICAL);
		text.setPadding(5, 5, 5, 5);
		
		
		TextView title = new TextView(context);
		title.setText(authors[position]);
		title.setTextSize(18);
		title.setTextColor(Color.BLACK);
		
		TextView description = new TextView(context);
		description.setText(surnames[position]);
		description.setTextColor(Color.BLACK);
		
		text.addView(title);
		text.addView(description);
		
		row.addView(drawable);
		row.addView(text);
			
		return row;
	}

}
