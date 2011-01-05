package fraguel.android.lists;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RouteManagerAdapter extends BaseAdapter{

	private String[] authors={"Bernardo", "Alberto", "Gabriel"};
	private String[] surnames={"Pericacho Sánchez", "Guillén", "Peñas Rodríguez"};
	private String[] images={};
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
