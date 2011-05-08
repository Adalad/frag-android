package fraguel.android.maps;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.states.MapState;
import fraguel.android.threads.ImageDownloadingThread;

public class MapItemizedOverlays extends ItemizedOverlay implements OnClickListener{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private Activity act;

	public MapItemizedOverlays(Drawable arg0) {
		super(boundCenterBottom(arg0));
		// TODO Auto-generated constructor stub
	}

	public MapItemizedOverlays(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;

	}

	public MapItemizedOverlays(Drawable defaultMarker, Activity actividad) {
		super(boundCenterBottom(defaultMarker));
		act = actividad;
		mContext= actividad.getApplicationContext();	

	}

	@Override
	protected OverlayItem createItem(int arg0) {
		// TODO Auto-generated method stub
		return mOverlays.get(arg0);

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}



	@Override
	protected boolean onTap(int index) {


		PointOverlay i= (PointOverlay) mOverlays.get(index);
		MapState.getInstance().loadData(i.getRoute(), i.getPointOI());


		//Sacamos el popup del punto de interés
		showPopup(i);
        
		return true;


	}

	
	
	public void showPopup(PointOverlay item){

		PointOverlay overlay= item;
		
		View popup= MapState.getInstance().getPopupPI();
		((TextView) popup.findViewById(R.id.popupPI_texto1)).setText(overlay.getTitle());
		
		ImageView v = ((ImageView) popup.findViewById(R.id.popupPI_imagen2));
		v.setAdjustViewBounds(true);
		

		
		MapState.getInstance().loadData(overlay.getRoute(), overlay.getPointOI());
		
		String path="route"+Integer.toString(overlay.getRoute().id)+"point"+overlay.getPointOI().id+"image";
		
		File f= new File(path);
		if (f.exists()){
			Bitmap bmp = BitmapFactory.decodeFile(path);
			v.setImageBitmap(bmp);
		}else{
			v.setImageDrawable(FRAGUEL.getInstance().getResources().getDrawable(R.drawable.loading));
			ImageDownloadingThread t = MapState.getInstance().getImageThread();
			t= new ImageDownloadingThread(overlay.getPointOI().icon,0,path);
			t.start();
		}
		
		
		
		popup.findViewById(R.id.btn_popupPI_info).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_photo).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_ar).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_video).setOnClickListener((OnClickListener) FRAGUEL.getInstance());

		
		boolean isPopup= false;
		for (int i=0 ; i<FRAGUEL.getInstance().getView().getChildCount(); i++){
			if (FRAGUEL.getInstance().getView().getChildAt(i).getId()== popup.getId())
				isPopup= true;
		}
		if (MapState.getInstance().isAnyPopUp()){
			MapState.getInstance().removePopUpOnRoute();
			MapState.getInstance().removePopUpPIOnRoute();
			MapState.getInstance().removePopUpPI();
		}
		if (!isPopup)
			MapState.getInstance().setPopupPI();

	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	

}



 