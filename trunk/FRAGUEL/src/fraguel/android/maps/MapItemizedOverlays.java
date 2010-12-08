package fraguel.android.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import fraguel.android.FRAGUEL;
import fraguel.android.MapState;
import fraguel.android.R;

public class MapItemizedOverlays extends ItemizedOverlay implements OnClickListener{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext=null;

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



	@SuppressWarnings("deprecation")
	@Override
	protected boolean onTap(int index) {

		OverlayItem item = mOverlays.get(index);
		String title= item.getTitle();

		//Evento general del punto de interés
		Toast t= Toast.makeText(mContext, title, Toast.LENGTH_SHORT);
		t.show();

		Point point = MapState.getInstance().getMapView().getProjection().toPixels(item.getPoint(), null);
		//int height=item.getMarker(0).getIntrinsicHeight();
		//int width=item.getMarker(0).getIntrinsicWidth();
		View popup= MapState.getInstance().getPopupView();
		((TextView) popup.findViewById(R.id.popupPI_texto1)).setText(item.getTitle());
		//MapState.getInstance().getMapView().addView(popup);
		popup.layout(point.x - 25, point.y -60,point.x + 140, point.y +50);
		boolean isPopup= false;
		for (int i=0 ; i<FRAGUEL.getInstance().view.getChildCount(); i++){
			if (FRAGUEL.getInstance().view.getChildAt(i).getId()== popup.getId())
				isPopup= true;
		}
		if (!isPopup)
			FRAGUEL.getInstance().addView(popup);
        

	
		//Evento específico del punto de interés
		if(title=="Facultad A"){ 

		}
		if(title=="Facultad B"){

		}
		else{


		}


		return true;


	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}



 