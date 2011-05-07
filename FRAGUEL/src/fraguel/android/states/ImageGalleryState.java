package fraguel.android.states;

import android.content.res.Configuration;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SpinnerAdapter;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.State;
import fraguel.android.gallery.BigImageAdapter;
import fraguel.android.gallery.FullScreenGallery;
import fraguel.android.gallery.ImageAdapter;
import fraguel.android.utils.InfoText;
import fraguel.android.utils.TitleTextView;

public class ImageGalleryState extends State{
	
	public static final int STATE_ID = 4;
	public static final int INFOSTATE_STOP_RECORD=1;
	public static final int INFOSTATE_REPEAT_RECORD=2;
	public static final int INFOSTATE_SPEECH=3;
	public static final int INFOSTATE_STOP_SPEECH=4;
	
	private TitleTextView title;
	private InfoText text;
	//private Gallery gallery;
	private ScrollView sv;
	private FullScreenGallery bigGallery;
	private int currentIndex;
	private boolean isBigGalleryDisplayed,isPresentation,automaticChange,stop,orientationChange;
	private int presentationIndex=0;
	
	public ImageGalleryState() {
		super();
		id = STATE_ID;
	}


	@Override
	public void load() {
		// TODO Auto-generated method stub
		
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.imagegallery,  null);
		((GridView) ( viewGroup).getChildAt(0)).setAdapter(new ImageAdapter(FRAGUEL.getInstance().getApplicationContext()));
		
		setParamsSmallGallery();
				
		setParamsBigGallery();
		
		//GridView gridview = (GridView) FRAGUEL.getInstance().findViewById(R.layout.imagegallery);
	    //gridview.setAdapter(new ImageAdapter(FRAGUEL.getInstance().getApplicationContext()));

		FRAGUEL.getInstance().addView(viewGroup);
		
		
		
		/*viewGroup = new LinearLayout(FRAGUEL.getInstance().getApplicationContext());
		((LinearLayout) viewGroup).setOrientation(LinearLayout.VERTICAL);
		
			title= new TitleTextView(FRAGUEL.getInstance().getApplicationContext());
			title.setText("Facultad A - Galer�a de fotos");
			title.setGravity(Gravity.CENTER_HORIZONTAL);
			
			isBigGalleryDisplayed=false;
			isPresentation=false;
			automaticChange=false;
			stop=false;
			orientationChange=false;
			
			setParamsSmallGallery();
			
			
			setParamsBigGallery();
		
		
		sv= new ScrollView(FRAGUEL.getInstance().getApplicationContext());
		sv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		
		text= new InfoText(FRAGUEL.getInstance().getApplicationContext());
		
		sv.addView(text);
		
		loadViews();
		
		currentIndex=-1;
		
		
		FRAGUEL.getInstance().addView(viewGroup);
		gallery.setSelection(0, true);*/
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
		
	@Override
	public void onUtteranceCompleted(String id){
	
		if (!stop){
			presentationIndex++;
			if (presentationIndex<bigGallery.getCount()){
				automaticChange=true;
				bigGallery.setSelection(presentationIndex, true);
				
			}
			else{
				isPresentation=false;
				bigGallery.setKeepScreenOn(false);
			
			}
			
		}else
			stop=false;
	}
	
	
	private void setParamsSmallGallery(){
		
		
		

		((GridView) ( viewGroup).getChildAt(0)).setOnItemClickListener(new OnItemClickListener() {
		

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			if (currentIndex==position){
				//FRAGUEL.getInstance().addView(bigGallery);
				viewGroup.removeAllViews();
				
				FRAGUEL.getInstance().changeState(ImageState.STATE_ID);
				viewGroup.addView(bigGallery);
				//bigGallery.setSelection(position, true);
				currentIndex=-1;
				isBigGalleryDisplayed=true;
				FRAGUEL.getInstance().stopTalking();
			}
			else
				currentIndex=position;
		}
		});
		
		((GridView) ( viewGroup).getChildAt(0)).setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				text.setText("Posici�n: "+ position+"\n"+"\n"+"La posici�n en la que se encuentra el elemento pulsado es la "+position);
				if (FRAGUEL.getInstance().isTalking())
					FRAGUEL.getInstance().stopTalking();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		
	}
	
	private void setParamsBigGallery(){
		bigGallery=new FullScreenGallery(FRAGUEL.getInstance().getApplicationContext());
		bigGallery.setAdapter(new BigImageAdapter(FRAGUEL.getInstance().getApplicationContext()));
		bigGallery.setHorizontalScrollBarEnabled(true);
		

		bigGallery.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			if (currentIndex==position){
				viewGroup.removeAllViews();
				//loadViews();
				//((GridView) viewGroup).setSelection(position, true);
				currentIndex=-1;
				isBigGalleryDisplayed=false;
				FRAGUEL.getInstance().stopTalking();
			}else
				currentIndex=position;
			
		}
		});
		
		bigGallery.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
//				text.setText("Posici�n: "+ position+"\n"+"\n"+"La posici�n en la que se encuentra el elemento pulsado es la "+position);
				if (!isPresentation){
					if (FRAGUEL.getInstance().isTalking())
						FRAGUEL.getInstance().stopTalking();
				}else{
					
					if(orientationChange){
						//wait until gallery complete it's orientation change
					}else if (automaticChange){
						FRAGUEL.getInstance().talkSpeech((String)text.getText(),position);
						automaticChange=false;
					}
					else{
						presentationIndex=position;
						FRAGUEL.getInstance().stopTalking();
						FRAGUEL.getInstance().talkSpeech((String)text.getText(),position);
						stop=true;
					}
						
					
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
			
	}
	
	public void changeOrientationFinished(){
		orientationChange=false;
	}
	
	
	private void loadViews(){
		viewGroup.addView(title);
		viewGroup.addView(((GridView) viewGroup));
		viewGroup.addView(sv);
		
	}
	
	@Override
	public boolean onConfigurationChanged(Configuration newConfig) {
		
		if (isBigGalleryDisplayed){
			switch (newConfig.orientation){
			case Configuration.ORIENTATION_LANDSCAPE: 
				bigGallery.setOrientationChanged(true);
				orientationChange=true;
				break;
				
			case Configuration.ORIENTATION_PORTRAIT:
				bigGallery.setOrientationChanged(true);
				orientationChange=true;
				break;
			
			}
			
		}

	return true;    
	}


	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.clear();
		if (!isPresentation)
			menu.add(0, INFOSTATE_SPEECH, 0, R.string.infostate_menu_speechPresentation).setIcon(R.drawable.ic_menu_talkplay);
		else
			menu.add(0, INFOSTATE_STOP_SPEECH, 0, R.string.infostate_menu_stopPresentation).setIcon(R.drawable.ic_menu_talkstop);
		menu.add(0, INFOSTATE_STOP_RECORD, 0, R.string.infostate_menu_stop).setIcon(R.drawable.ic_menu_talkstop);
		menu.add(0, INFOSTATE_REPEAT_RECORD, 0, R.string.infostate_menu_repeat).setIcon(R.drawable.ic_menu_talkplay);
		
		
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
		case INFOSTATE_SPEECH:
			if (!isBigGalleryDisplayed){
				viewGroup.removeAllViews();
				viewGroup.addView(bigGallery);
				currentIndex=-1;
				isBigGalleryDisplayed=true;
			}
			bigGallery.setSelection(0, true);
			presentationIndex=0;
			FRAGUEL.getInstance().talkSpeech((String)text.getText(),0);
			bigGallery.setKeepScreenOn(true);
			isPresentation=true;
			return true;
		case INFOSTATE_STOP_SPEECH:
			FRAGUEL.getInstance().stopTalking();
			isPresentation=false;
			viewGroup.removeAllViews();
			//loadViews();
			//((GridView) viewGroup).setSelection(0, true);
			currentIndex=-1;
			isBigGalleryDisplayed=false;
			return true;
		
		}
		return false;
	}


	

}