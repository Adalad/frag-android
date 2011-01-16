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

public class VideoState extends State{

	public static final int STATE_ID = 3;
	
	// Singleton
	private static VideoState instance;
	
	public static final int INFOSTATE_STOP_RECORD=1;
	public static final int INFOSTATE_REPEAT_RECORD=2;
	
	//private MediaPlayer mediaPlayer;
	private TextView text;
	private boolean isVideoDisplayed=false;
			
	//VideoView vars
	private VideoView mVideoView;
	private ImageButton mPlay;
	private ImageButton mPause;
	private ImageButton mReset;
	private ImageButton mStop;
	private String currentPath;
	private String videoPath;
	
	
	
	public VideoState() {
		super();
		id = STATE_ID;
		// Singleton
		instance = this;
	}

	public static VideoState getInstance() {
		if (instance == null)
			instance = new VideoState();
		return instance;
	}
	
	public void setVideoPath(String path){
		
		videoPath =path;
		
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
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
				playSelectedVideo();
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
					currentPath = null;
					mVideoView.stopPlayback();
				}
			}
		});
		
		playSelectedVideo();
	}

		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	private void playSelectedVideo(){
						
		try {
					     						
			Log.v("Error playing video" , "path: " + videoPath);
			if (videoPath == null || videoPath.length() == 0) {
				Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "File URL/path is empty",
						Toast.LENGTH_LONG).show();

			} else {
				// If the path has not changed, just start the media player
				if (videoPath.equals(currentPath) && mVideoView != null) {
					mVideoView.start();
					mVideoView.requestFocus();
					return;
				}
				currentPath = videoPath;
				mVideoView.setVideoPath(getDataSource(videoPath));
				mVideoView.start();
				mVideoView.requestFocus();

			}
		} catch (Exception e) {
			Log.e("Error playing video", "error: " + e.getMessage(), e);
			if (mVideoView != null) {
				mVideoView.stopPlayback();
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
		 	    


	


	

}
