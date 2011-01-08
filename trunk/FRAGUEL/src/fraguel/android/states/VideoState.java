package fraguel.android.states;


import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.R.id;
import fraguel.android.R.layout;
import fraguel.android.gallery.ImageAdapter;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoState extends State{

	public static final int STATE_ID = 3;
	private TextView title;
	private VideoView video;
	private Gallery videoGallery;
	private ScrollView sv;
	private TextView text;
	private boolean isVideoDisplayed=false;
	private int selectedItem;
	private LinearLayout container;
	private String[] videos={"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?gay_referee.3gp",
			"http://www.free-3gp-video.com/download.php?do-beer-not-drugs.3gp"};
	
	public VideoState() {
		super();
		id = STATE_ID;
	}

	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		//Creamos e importamos el layout del xml
		

		container = new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		container.setOrientation(LinearLayout.VERTICAL);
		
		setVideoGalleryParams();
		viewGroup=container;
		FRAGUEL.getInstance().addView(viewGroup);	
		
		
		//Preparamos la vista de video
		
		video= new VideoView(FRAGUEL.getInstance().getApplicationContext());
		
    	MediaController mediaController = new MediaController(FRAGUEL.getInstance());
    	mediaController.setAnchorView(video);
    	video.setMediaController(mediaController);
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private void setVideoGalleryParams(){
		container.removeAllViews();
		
		title= new TextView(FRAGUEL.getInstance().getApplicationContext());
		title.setText("Galería de vídeo");
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		
		container.addView(title);
		
		
		videoGallery=new Gallery(FRAGUEL.getInstance().getApplicationContext());
		videoGallery.setAdapter(new ImageAdapter(FRAGUEL.getInstance().getApplicationContext()));
		videoGallery.setHorizontalScrollBarEnabled(true);
		setVideoGalleryListeners();
		
		container.addView(videoGallery);
		
		
		sv= new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		text= new TextView(FRAGUEL.getInstance().getApplicationContext());
		
		sv.addView(text);
		
		container.addView(sv);
		
		isVideoDisplayed=false;
		selectedItem=-1;
		
	}
	
	private void setVideoGalleryListeners(){
		videoGallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				if (selectedItem==arg2){
					playSelectedVideo(selectedItem);	
				}
				selectedItem=arg2;
				
			}});
		
		
		videoGallery.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				text.setText("Posición: "+position+"\n"+"\n"+"El elemento que está usted visualizando está en la posición "+
						position+" dentro de la galería.");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
	}
	
	private void playSelectedVideo(int selected){
		
		container.removeAllViews();
		Uri uri = Uri.parse(videos[selected]);
		video.setVideoURI(uri);
		container.addView(video);
		video.requestFocus();
		isVideoDisplayed=true;
		video.start();
	}


	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}


	

}
