package fraguel.android.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Gallery;

public class FullScreenGallery extends Gallery{

	private boolean orientationChanged;
	public FullScreenGallery(Context context) {
		super(context);
		orientationChanged=false;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (orientationChanged){
			int i=this.getSelectedItemPosition();		
			orientationChanged=false;
			super.draw(canvas);
			this.setSelection(0,true);
			this.setSelection(this.getCount()-1,true);
			this.setSelection(i,true);
		}else
				super.draw(canvas);
		
		
	}
	public void setOrientationChanged(boolean b){
		orientationChanged=b;
	}

}
