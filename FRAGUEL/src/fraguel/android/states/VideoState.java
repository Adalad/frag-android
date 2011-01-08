package fraguel.android.states;


import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.R.id;
import fraguel.android.R.layout;
import fraguel.android.gallery.ImageAdapter;
import fraguel.android.gallery.VideoAdapter;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
	
	public VideoState() {
		super();
		id = STATE_ID;
	}

	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		//Creamos e importamos el layout del xml
		/*LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		viewGroup= (ViewGroup) li.inflate(R.layout.video,  null);
		FRAGUEL.getInstance().addView(viewGroup);*/
		
		LinearLayout container = new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		container.setOrientation(LinearLayout.VERTICAL);
		
		title= new TextView(FRAGUEL.getInstance().getApplicationContext());
		title.setText("Galería de vídeo");
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		
		container.addView(title);
		
		videoGallery=new Gallery(FRAGUEL.getInstance().getApplicationContext());
		videoGallery.setAdapter(new VideoAdapter(FRAGUEL.getInstance().getApplicationContext()));
		videoGallery.setHorizontalScrollBarEnabled(true);
		
		container.addView(videoGallery);
		
		viewGroup=container;
				
    	/*VideoView videoView = (VideoView) FRAGUEL.getInstance().findViewById(R.id.VideoView);
    	MediaController mediaController = new MediaController(FRAGUEL.getInstance());
    	mediaController.setAnchorView(videoView);
    	// Introducimos el video link (mp4 o 3gp )
    	Uri video = Uri.parse("http://m.youtube.com/setpref?gl=ES&client=mv-google&hl=es&pref=serve_hq&value=on&next=%2Fwatch%3Fgl%3DES%26client%3Dmv-google%26hl%3Des%26rl%3Dyes%26v%3DNRNxV0bTa4s");
    	videoView.setMediaController(mediaController);
    	videoView.setVideoURI(video);
    	//videoView.setVideoPath("/sdcard/download/empire.3gp");
    	videoView.start();*/
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	

}
