package fraguel.android.xml;

import java.util.ArrayList;

public class Route {

	public int id;
	public String name;
	public String description;
	public ArrayList<PointOI> points;

	public Route() {
		id = 0;
		name = "default";
		description = "none";
	}

	public Route(int i, String n, String d) {
		id = i;
		name = n;
		description = d;
	}
}
