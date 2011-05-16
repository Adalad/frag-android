package fraguel.android.maps;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.util.Pair;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import fraguel.android.FRAGUEL;
import fraguel.android.PointOI;
import fraguel.android.Route;

public class RouteOverlay extends Overlay{

	private Route route=null;
	private ArrayList<Pair<Pair<Integer, Integer>, Pair<Float, Float>>> visited=null;
	private ArrayList<Pair<Integer, Pair<Float, Float>>> notVisited=null;
	
	private ArrayList<ArrayList<GeoPoint>> rutasVerde;
	private ArrayList<ArrayList<GeoPoint>> rutasRojo;
	private ArrayList<GeoPoint> rutaActual;
	private GeoPoint anterior,actual;
	private boolean first;
	

	public RouteOverlay (Route r){
		route=r;
	}
	public RouteOverlay(){
		first=false;
		visited= FRAGUEL.getInstance().getGPS().getRoutePointsVisited();
		notVisited=FRAGUEL.getInstance().getGPS().getRoutePointsNotVisited();
		rutasVerde= new ArrayList<ArrayList<GeoPoint>>();
		rutasRojo= new ArrayList<ArrayList<GeoPoint>>();
		
		for (Pair<Pair<Integer, Integer>, Pair<Float, Float>> p: visited){
			if (first){
				anterior=actual;
				actual= new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000));
				rutaActual= new ArrayList<GeoPoint>();
				this.GetPath(anterior, actual, rutaActual);
				rutasRojo.add(rutaActual);
			}
			else{
				first=true;
				actual= new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000));
			}
			
		}
		
		for (Pair<Integer, Pair<Float, Float>> p: notVisited){
			if (first){
				anterior=actual;
				actual= new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000));
				rutaActual= new ArrayList<GeoPoint>();
				this.GetPath(anterior, actual, rutaActual);
				rutasVerde.add(rutaActual);
			}
			else{
				first=true;
				actual= new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000));
			}
			
		}
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) { 
		Projection projection=mapView.getProjection();
		Paint paint= new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		Point actual=null;
		Point anterior=null;
		paint.setStrokeWidth(5);
		paint.setAlpha(120);
		boolean first= false;
		
		if (route==null){
			paint.setColor(Color.RED);
			
			for (ArrayList<GeoPoint> r: rutasRojo){
				
				for(GeoPoint p : r){
					anterior=actual;
					actual= new Point();
					projection.toPixels(p, actual);
					if (first){
						canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
					}
					else
						first=true;
					
				}
				
				
			}
			paint.setColor(Color.GREEN);
			for (ArrayList<GeoPoint> r: rutasVerde){
				
				for(GeoPoint p : r){
					anterior=actual;
					actual= new Point();
					projection.toPixels(p, actual);
					if (first){
						canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
					}
					else
						first=true;
					
				}
				
				
			}
			
			
			
			/*for (Pair<Pair<Integer, Integer>, Pair<Float, Float>> p: visited){
				anterior=actual;
				actual= new Point();
				projection.toPixels(new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000)), actual);
				if (first){
					canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
				}
				else
					first=true;	
			}*/
			/*paint.setColor(Color.GREEN);
			for (Pair<Integer, Pair<Float, Float>> p: notVisited){
				anterior=actual;
				actual= new Point();
				projection.toPixels(new GeoPoint((int)(p.second.first*1000000),(int)(p.second.second*1000000)), actual);
				if (first){
					canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
				}
				else
					first=true;
			}*/
				
		}else{
			paint.setColor(Color.MAGENTA);
			for (PointOI p: route.pointsOI){
				anterior=actual;
				actual= new Point();
				projection.toPixels(new GeoPoint((int)(p.coords[0]*1000000),(int)(p.coords[1]*1000000)), actual);
				if (first){
					canvas.drawLine(actual.x, actual.y, anterior.x, anterior.y, paint);
				}
				else
					first=true;			
			
			}
		}
		super.draw(canvas, mapView, shadow);
		
	}
	
	private boolean GetPath(GeoPoint src, GeoPoint dest, ArrayList<GeoPoint> ruta) {
		boolean result=false;
		// connect to map web service
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");//from
		urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 ));
		urlString.append("&daddr=");//to
		urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 ));
		urlString.append(",");
		urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 ));
		urlString.append("&dirflg=w");
		urlString.append("&ie=UTF8&0&om=0&output=kml");
		Log.d("xxx","URL="+urlString.toString());
		// get the kml (XML) doc. And parse it to get the coordinates(direction route).
		Document doc = null;
		HttpURLConnection urlConnection= null;
		URL url = null;
		try
		{ 
				url = new URL(urlString.toString());
				urlConnection=(HttpURLConnection)url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				urlConnection.connect(); 
		
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				doc = db.parse(urlConnection.getInputStream()); 
		
				if(doc.getElementsByTagName("GeometryCollection").getLength()>0)
				{
					result=true;
					//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName();
					String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ;
					//Log.d("xxx","path="+ path);
					String [] pairs = path.split(" "); 
					String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height
					// src
					//GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
					//mMapView01.getOverlays().add(new MyOverLay(startGP,startGP,1));
					//ruta.add(startGP);
					GeoPoint gp1;
					//GeoPoint gp2 = startGP; 
					ruta.add(src);
					for(int i=0;i<pairs.length;i++) // the last one would be crash
					{
							lngLat = pairs[i].split(",");
							//gp1 = gp2;
							// watch out! For GeoPoint, first:latitude, second:longitude
							gp1 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
							ruta.add(gp1);
							//mMapView01.getOverlays().add(new MyOverLay(gp1,gp2,2,color));
							//Log.d("xxx","pair:" + pairs[i]);
					}
					//mMapView01.getOverlays().add(new MyOverLay(dest,dest, 3)); // use the default color
					ruta.add(dest);
					
				} 
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}

		return result;

		}
}
