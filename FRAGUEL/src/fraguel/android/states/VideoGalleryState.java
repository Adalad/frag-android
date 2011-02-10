package fraguel.android.states;


import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.gallery.ImageAdapter;
import fraguel.android.utils.InfoText;
import fraguel.android.utils.TitleTextView;

public class VideoGalleryState extends State implements SurfaceHolder.Callback{

	public static final int STATE_ID = 9;
	public static final int INFOSTATE_STOP_RECORD=1;
	public static final int INFOSTATE_REPEAT_RECORD=2;
	private TitleTextView title;
	private SurfaceView video;
	private SurfaceHolder surfaceHolder;
	private Gallery videoGallery;
	private ScrollView sv;
	private InfoText text;
	private boolean isVideoDisplayed=false;
	private int selectedItem;
	private LinearLayout container;
	private String[] videos={"http://daily3gp.com/vids/747.3gp",
			"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?gay_referee.3gp",
	"http://www.free-3gp-video.com/download.php?do-beer-not-drugs.3gp"};



	public VideoGalleryState() {
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

		video= new SurfaceView(FRAGUEL.getInstance().getApplicationContext());

		surfaceHolder=video.getHolder();

		Display display = ((WindowManager)FRAGUEL.getInstance().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth(); 
		int height= display.getHeight();
		surfaceHolder.setFixedSize(width,height);

		FRAGUEL.getInstance().getWindow().setFormat(PixelFormat.TRANSPARENT);


	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void setVideoGalleryParams(){
		container.removeAllViews();

		title= new TitleTextView(FRAGUEL.getInstance().getApplicationContext());
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
		text= new InfoText(FRAGUEL.getInstance().getApplicationContext());

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
					VideoState.getInstance().setVideoPath(videos[selectedItem]);
					FRAGUEL.getInstance().changeState(VideoState.STATE_ID);
					//playSelectedVideo(selectedItem);	
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

	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.clear();
		if (!isVideoDisplayed){
			menu.add(0, INFOSTATE_STOP_RECORD, 0, R.string.infostate_menu_stop).setIcon(R.drawable.ic_menu_talkstop);
			menu.add(0, INFOSTATE_REPEAT_RECORD, 0, R.string.infostate_menu_repeat).setIcon(R.drawable.ic_menu_talkplay);
		}
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case INFOSTATE_STOP_RECORD:
			FRAGUEL.getInstance().stopTalking();
			return true;

		case INFOSTATE_REPEAT_RECORD:
			FRAGUEL.getInstance().talk((String)text.getText());
			return true;
		}
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Surface Creada", Toast.LENGTH_LONG).show();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}






}
