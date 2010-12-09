package fraguel.android.maps;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.states.MapState;

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

		OverlayItem item = mOverlays.get(index);
		String title= item.getTitle();

		//Evento general del punto de inter�s
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
        

	
		//Evento espec�fico del punto de inter�s
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



 