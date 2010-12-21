package fraguel.android.xml;

public class Route {

	public int id;
	public String name;
	public String description;

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
