package fraguel.android.states;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.gallery.ImageAdapter;

public class ImageState extends State{
	
	public ImageState() {
		super();
		id = 4;
	}
	
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		viewGroup = new Gallery(FRAGUEL.getInstance().getApplicationContext());
		((Gallery) viewGroup).setAdapter(new ImageAdapter(FRAGUEL.getInstance().getApplicationContext()));

	    ((Gallery)viewGroup).setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	            Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });

	    FRAGUEL.getInstance().addView(viewGroup);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
