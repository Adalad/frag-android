package fraguel.android.states;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;



import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.gallery.ImageAdapter;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoGalleryState extends State implements SurfaceHolder.Callback{

	public static final int STATE_ID = 9;
	public static final int INFOSTATE_STOP_RECORD=1;
	public static final int INFOSTATE_REPEAT_RECORD=2;
	private TextView title;
	private SurfaceView video;
	private SurfaceHolder surfaceHolder;
	private Gallery videoGallery;
	private MediaPlayer mediaPlayer;
	private String currentPath="";
	private ScrollView sv;
	private TextView text;
	private boolean isVideoDisplayed=false;
	private int selectedItem;
	private LinearLayout container;
	private String[] videos={"http://daily3gp.com/vids/747.3gp",
			"http://www.free-3gp-video.com/download.php?dancing-skeleton.3gp",
			"http://www.free-3gp-video.com/download.php?gay_referee.3gp",
			"http://www.free-3gp-video.com/download.php?do-beer-not-drugs.3gp"};
	
	//VideoView vars
	private VideoView mVideoView;
	private ImageButton mPlay;
	private ImageButton mPause;
	private ImageButton mReset;
	private ImageButton mStop;
	private String current;
	
	
	
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
	
	private void createVideoView(){
		
		viewGroup.removeAllViews();
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		viewGroup= (ViewGroup) li.inflate(R.layout.video,  null);
		FRAGUEL.getInstance().addView(viewGroup);
		
		
		mVideoView = (VideoView) FRAGUEL.getInstance().findViewById(R.id.surface_view);
	
		mPlay = (ImageButton) FRAGUEL.getInstance().findViewById(R.id.play);
		mPause = (ImageButton) FRAGUEL.getInstance().findViewById(R.id.pause);
		mReset = (ImageButton) FRAGUEL.getInstance().findViewById(R.id.reset);
		mStop = (ImageButton) FRAGUEL.getInstance().findViewById(R.id.stop);

		mPlay.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				playSelectedVideo(selectedItem);
			}
		});
		mPause.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					mVideoView.pause();
				}
			}
		});
		mReset.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					mVideoView.seekTo(0);
				}
			}
		});
		mStop.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					current = null;
					mVideoView.stopPlayback();
				}
			}
		});
		//FRAGUEL.getInstance().runOnUiThread(new Runnable(){
		//	public void run() {
		//		playSelectedVideo(selectedItem);
				
		//	}
			
		//});
		
		
	}
	
	private void playSelectedVideo(int selected){
		
		createVideoView();
		
		try {
			
			String path = videos[selected];
				 
            // If the path has not changed, just start the media player
            if (path.equals(currentPath) && mediaPlayer != null) {
                mediaPlayer.start();
                isVideoDisplayed=true;
                return;
            }
            currentPath = path;
			
			
			Log.v("Error playing video" , "path: " + path);
			if (path == null || path.length() == 0) {
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "File URL/path is empty",
						Toast.LENGTH_LONG).show();

			} else {
				// If the path has not changed, just start the media player
				if (path.equals(current) && mVideoView != null) {
					mVideoView.start();
					mVideoView.requestFocus();
					return;
				}
				current = path;
				mVideoView.setVideoPath(getDataSource(path));
				mVideoView.start();
				mVideoView.requestFocus();

			}
		} catch (Exception e) {
			Log.e("Error playing video", "error: " + e.getMessage(), e);
			if (mVideoView != null) {
				mVideoView.stopPlayback();
			}
		}
				
		
		isVideoDisplayed=false;
		try {
				            String path = videos[selected];
				           				 
				            // If the path has not changed, just start the media player
				            if (path.equals(currentPath) && mediaPlayer != null) {
				                mediaPlayer.start();
				                isVideoDisplayed=true;
				                return;
				            }
				            currentPath = path;
				 
				            // Create a new media player and set the listeners
				            mediaPlayer=new MediaPlayer();
				            /*mp.setOnErrorListener(this);
				            mp.setOnBufferingUpdateListener(this);
				            mp.setOnCompletionListener(this);
				            mp.setOnPreparedListener(this);*/
				            mediaPlayer.setAudioStreamType(2);
				 
				            // Set the surface for the video output
				            mediaPlayer.setDisplay(  (SurfaceHolder) video.getHolder().getSurface());
				 
				            // Set the data source in another thread
				            // which actually downloads the mp3 or videos
				            // to a temporary location
				            Runnable r = new Runnable() {
				                public void run() {
				                    try {
				                        getDataSource(currentPath);
				                    } catch (IOException e) {
				                        
				                    }
				                    try {
										mediaPlayer.prepare();
									} catch (IllegalStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				                    Toast.makeText(FRAGUEL.getInstance().getApplicationContext(),Integer.toString(mediaPlayer.getDuration()),Toast.LENGTH_SHORT).show();
				                    isVideoDisplayed=true;
				                    mediaPlayer.start();
				                }
				            };
				            new Thread(r).start();
				        } catch (Exception e) {
				            
				            if (mediaPlayer != null) {
				                mediaPlayer.stop();
				                mediaPlayer.release();
				            }
				        }
	}


	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.clear();
		if (!isVideoDisplayed){
			menu.add(0, INFOSTATE_STOP_RECORD, 0, R.string.infostate_menu_stop).setIcon(R.drawable.stop);
			menu.add(0, INFOSTATE_REPEAT_RECORD, 0, R.string.infostate_menu_repeat).setIcon(R.drawable.play);
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


	 private String getDataSource(String path) throws IOException {
		 if (!URLUtil.isNetworkUrl(path)) {
				return path;
			} else {
				URL url = new URL(path);
				URLConnection cn = url.openConnection();
				cn.connect();
				InputStream stream = cn.getInputStream();
				if (stream == null)
					throw new RuntimeException("stream is null");
				File temp = File.createTempFile("mediaplayertmp", "dat");
				temp.deleteOnExit();
				String tempPath = temp.getAbsolutePath();
				FileOutputStream out = new FileOutputStream(temp);
				byte buf[] = new byte[128];
				do {
					int numread = stream.read(buf);
					if (numread <= 0)
						break;
					out.write(buf, 0, numread);
				} while (true);
				try {
					stream.close();
				} catch (IOException ex) {
					Log.e("Getting data source", "error: " + ex.getMessage(), ex);
				}
				return tempPath;
			}
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
