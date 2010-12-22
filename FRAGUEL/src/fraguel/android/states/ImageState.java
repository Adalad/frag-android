package fraguel.android.states;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.State;
import fraguel.android.gallery.ImageAdapter;

public class ImageState extends State{
	
	private TextView title;
	private TextView text;
	private Gallery gallery;
	
	public ImageState() {
		super();
		id = 4;
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
		viewGroup = new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);
		
			title= new TextView(FRAGUEL.getInstance().getApplicationContext());
			title.setText("Facultad A");
			
			viewGroup.addView(title);
			
			gallery=new Gallery(FRAGUEL.getInstance().getApplicationContext());
			gallery.setAdapter(new ImageAdapter(FRAGUEL.getInstance().getApplicationContext()));

			gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
				text.setText("Posición: "+ position+"\n"+"\n"+"La posicion en la que se encuentra el elemento pulsado es la "+position);
			}
			});
		
		viewGroup.addView(gallery);
		
		ScrollView sv= new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		text= new TextView(FRAGUEL.getInstance().getApplicationContext());
		sv.addView(text);
		
		viewGroup.addView(sv);
		
		

		FRAGUEL.getInstance().addView(viewGroup);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
