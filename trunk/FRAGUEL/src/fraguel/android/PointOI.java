package fraguel.android;

public class PointOI {
	public int id;
	public float[] coords = { 0.0f, 0.0f };
	public String title;
	public String icon;
	public String[] images;
	public String image;
	public String video;
	public String ar;

	public PointOI() {
		id = 0;
		title = "";
	}
	
	
	public void setImages(String urls){
		
		this.images= urls.split(",\\s*");
		
	}
}
