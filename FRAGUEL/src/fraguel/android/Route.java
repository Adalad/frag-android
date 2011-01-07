package fraguel.android;

import java.util.ArrayList;


public class Route {

	public int id;
	public String name;
	public String description;
	public ArrayList<PointOI> pointsOI;

	public Route() {
		id = 0;
		name = "default";
		description = "none";
		pointsOI = new ArrayList<PointOI>();
	}

	public Route(int i, String n, String d) {
		id = i;
		name = n;
		description = d;
	}
}
