package fraguel.android;

import java.io.Serializable;

public class PointOI implements Serializable{
	private static final long serialVersionUID = 8799656478674716638L;
	public int id;
	public float[] coords = { 0.0f, 0.0f };
	public String title;
	public String icon;
	public String[] images;
	public String pointdescription;
	public String image;
	public String video;
	public String ar;

	public PointOI() {
		id = 0;
		title = "";
		pointdescription = "";
	}
	
	
	public void setImages(String urls){
		
		this.images= urls.split(",\\s*");
		
	}
}

