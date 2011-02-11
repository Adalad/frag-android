package fraguel.android.lists;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fraguel.android.FRAGUEL;
import fraguel.android.R;

public class RouteManagerAdapter extends BaseAdapter{

	private ArrayList<String> titles=new ArrayList<String>();
	private ArrayList<String> descriptions=new ArrayList<String>();
	private ArrayList<String> images=new ArrayList<String>();
	private Context context;
	
	public RouteManagerAdapter(Context c) {
        context = c;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.size();
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
	
	public void setTitle(ArrayList<String> s){
		titles=s;
	}
	public void setDescription(ArrayList<String> s){
		descriptions=s;
	}
	public void setImages(ArrayList<String> s){
		images=s;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewGroup row= new LinearLayout(context);
		((LinearLayout) row).setOrientation(LinearLayout.HORIZONTAL);
		
		row.setBackgroundColor((position & 1) == 1 ? Color.WHITE : Color.rgb(0, 200, 0));
		
		ImageView drawable = new ImageView(context);
		drawable.setImageResource(R.drawable.museumsalango);
		drawable.setLayoutParams(new LayoutParams(40,40));
		drawable.setPadding(10, 5, 5, 5);
		
		
		LinearLayout text = new LinearLayout(context);
		text.setOrientation(LinearLayout.VERTICAL);
		text.setPadding(5, 5, 5, 5);
		
		
		TextView title = new TextView(context);
		title.setText(titles.get(position));
		title.setTextAppearance(FRAGUEL.getInstance().getApplicationContext(), R.style.ListTitle);

		
		TextView description = new TextView(context);
		description.setText(descriptions.get(position));
		description.setTextAppearance(FRAGUEL.getInstance().getApplicationContext(), R.style.ListText);
		
		text.addView(title);
		text.addView(description);
		
		row.addView(drawable);
		row.addView(text);
			
		return row;
	}

}
