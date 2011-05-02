package fraguel.android.maps;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
		//Drawable drawable = FRAGUEL.getInstance().getResources().getDrawable(R.drawable.icon);
		//item.setMarker(drawable);
	
		//Evento general del punto de interés
		Toast t= Toast.makeText(mContext, title, Toast.LENGTH_SHORT);
		//t.show();

		//Sacamos el popup del punto de interés
		showPopup(item);
        
		return true;


	}

	
	
	public void showPopup(OverlayItem item){

		//Point point = MapState.getInstance().getMapView().getProjection().toPixels(item.getPoint(), null);
		
		View popup= MapState.getInstance().getPopupPI();
		((TextView) popup.findViewById(R.id.popupPI_texto1)).setText(item.getTitle());
				
		((ImageView) popup.findViewById(R.id.popupPI_imagen2)).setScaleType(ScaleType.CENTER_INSIDE);
		((ImageView) popup.findViewById(R.id.popupPI_imagen2)).setImageResource(R.drawable.popup_facultad1);
		popup.findViewById(R.id.btn_popupPI_info).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_photo).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_ar).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		popup.findViewById(R.id.btn_popupPI_video).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
       
		//MapState.getInstance().getMapView().addView(popup);
		//popup.layout(point.x - 25, point.y -60,point.x + 140, point.y +50);
		
		boolean isPopup= false;
		for (int i=0 ; i<FRAGUEL.getInstance().getView().getChildCount(); i++){
			if (FRAGUEL.getInstance().getView().getChildAt(i).getId()== popup.getId())
				isPopup= true;
		}
		if (!isPopup)
			MapState.getInstance().setPopupPI();

	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}



 