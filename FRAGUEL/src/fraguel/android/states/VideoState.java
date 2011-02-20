package fraguel.android.states;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.resources.ResourceManager;

public class VideoState extends State{

	public static final int STATE_ID = 3;

	// Singleton
	private static VideoState instance;

	public static final int VIDEOSTATE_MENU_DOWNLOADVIDEO=1;
	public static final int VIDEOSTATE_MENU_BACKVIDEOGALLERY=2;
	

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

	private MediaController mc;


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
		//MediaController mediaController = new MediaController(FRAGUEL.getInstance().getApplicationContext());
    	//mediaController.setAnchorView(mVideoView);
    	//mVideoView.setMediaController(mediaController);
    	//mVideoView.requestFocus();
    	//mediaController.setEnabled(true);
//    	mediaController.show();
    	
		mc = new MediaController(FRAGUEL.getInstance());
		mc.setMediaPlayer(player_interface);
		mc.setEnabled(true);
		mc.setAnchorView(mVideoView);
		//mc.show(100);
		mVideoView.setMediaController(mc);
		/*mPlay = (ImageButton) FRAGUEL.getInstance().findViewById(R.id.play);
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
		});*/

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
				mVideoView.setVideoPath(ResourceManager.getInstance().getDownloadManager().downloadFromPath(videoPath));
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
			menu.add(0, this.VIDEOSTATE_MENU_DOWNLOADVIDEO, 0, R.string.videostate_menu_downloadvideo).setIcon(R.drawable.download);
			menu.add(0, this.VIDEOSTATE_MENU_BACKVIDEOGALLERY, 0, R.string.videostate_menu_backvideogallery).setIcon(R.drawable.back);
		}
		return menu;
	}


	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case VIDEOSTATE_MENU_DOWNLOADVIDEO:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			return true;

		case VIDEOSTATE_MENU_BACKVIDEOGALLERY:
			mVideoView.pause();
			FRAGUEL.getInstance().changeState(VideoGalleryState.STATE_ID);
			return true;
		}
		return false;
	}

	
	private MediaController.MediaPlayerControl player_interface = new MediaController.MediaPlayerControl() {

		public int getBufferPercentage() {
			return 75;
		}

		public int getCurrentPosition() {
			return 25;
		}

		public int getDuration() {
			return 180;
		}

		public boolean isPlaying() {
			return true;
		}

		public void pause() {
		}

		public void seekTo(int pos) {
		}

		public void start() {
		}

		@Override
		public boolean canPause() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canSeekBackward() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean canSeekForward() {
			// TODO Auto-generated method stub
			return false;
		}
	}; 






}
