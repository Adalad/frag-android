package fraguel.android.states;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fraguel.android.FRAGUEL;
import fraguel.android.R;
import fraguel.android.Route;
import fraguel.android.State;
import fraguel.android.resources.ResourceManager;

public class MainMenuState extends State {

	public static final int STATE_ID = 15;

	// Variables menu de opciones
	private static final int MENU_MAIN = 1;
	private static final int MENU_EXIT = 2;

	protected TextView gps;
	protected TextView orientation;
	final CharSequence[] options = {"Plantilla en blanco", "Mediante 'GeoTagging'"};
	public MainMenuState() {
		super();
		id = STATE_ID;
	}

	@Override
	public void load() {
		LayoutInflater li=  FRAGUEL.getInstance().getLayoutInflater();
		if(viewGroup==null)
			viewGroup= (ViewGroup) li.inflate(R.layout.mainmenu,  null);

		

		FrameLayout title=(FrameLayout) viewGroup.getChildAt(0);
		FrameLayout btn_1=(FrameLayout) viewGroup.getChildAt(1);
		FrameLayout btn_2=(FrameLayout) viewGroup.getChildAt(2);
		FrameLayout btn_3=(FrameLayout) viewGroup.getChildAt(3);
		FrameLayout btn_4=(FrameLayout) viewGroup.getChildAt(4);


		AnimationSet setTitle = new AnimationSet(true);
		AnimationSet setBtn = new AnimationSet(true);

		Animation titleAnim = new AlphaAnimation(0.0f, 1.0f);
		titleAnim.setDuration(2000);
		//titleAnim.setRepeatCount(-1);


		Animation btnAnim1 = new AlphaAnimation(0.0f, 1.0f);
		btnAnim1.setDuration(1000);

		Animation btnAnim2 = new RotateAnimation(45, 0 );
		btnAnim2.setDuration(1000);

		Animation btnAnim3 = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -3.0f, Animation.RELATIVE_TO_SELF, 0.0f
		);
		btnAnim3.setDuration(1000);


		setTitle.addAnimation(titleAnim);
		setBtn.addAnimation(btnAnim1);
		//setBtn.addAnimation(btnAnim2);
		setBtn.addAnimation(btnAnim3);

		LayoutAnimationController controllerTitle = new LayoutAnimationController(setTitle, 0.25f);
		LayoutAnimationController controllerBtn = new LayoutAnimationController(setBtn, 0.25f);
		title.setLayoutAnimation(controllerTitle);
		btn_1.setLayoutAnimation(controllerBtn);
		btn_2.setLayoutAnimation(controllerBtn);
		btn_3.setLayoutAnimation(controllerBtn);
		btn_4.setLayoutAnimation(controllerBtn);


		FRAGUEL.getInstance().addView(viewGroup);


		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_freemode)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_routemode)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_interactivemode)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		//((Button) FRAGUEL.getInstance().findViewById(R.id.btn_credits)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		//((Button) FRAGUEL.getInstance().findViewById(R.id.btn_config)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		((Button) FRAGUEL.getInstance().findViewById(R.id.btn_manager)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());
		//((Button) FRAGUEL.getInstance().findViewById(R.id.btn_exit)).setOnClickListener((OnClickListener) FRAGUEL.getInstance());


	}

	@Override
	public void onClick(View v) {
		// TODO rellenar con ids de los demas modos de juego
		switch (v.getId()) {
		case R.id.btn_freemode:
			FRAGUEL.getInstance().changeState(MapState.STATE_ID);
			break;
		case R.id.btn_routemode:
			FRAGUEL.getInstance().changeState(RouteManagerState.STATE_ID);
			break;
		case R.id.btn_interactivemode:
			FRAGUEL.getInstance().cleanCache();
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Caché borrada con éxito", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_manager:
			this.createDialog("Opciones", options);
			break;
		/*case R.id.btn_config:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_credits:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;*/
		
		default:
			System.exit(0);
		}
	}
	
	
	@Override
	public Menu onCreateStateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.clear();
		// Menu de opciones creado por defecto
		//menu.add(0, MENU_MAIN, 0, R.string.menu_menu).setIcon(R.drawable.info);
		menu.add(0, MENU_EXIT, 0, R.string.menu_exit).setIcon(R.drawable.info);
		
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case MENU_EXIT:
			System.exit(0);
			return true;
		}
		return false;
	}
	
	private void createDialog(String title,final CharSequence[] items){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(FRAGUEL.getInstance());
		builder.setTitle(title);
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	
		    	switch(item){
				case 0:
					createXMLTemplate("new",20);
					break;
				case 1: 
					Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
					break;
			
		    	}
		    	
		        dialog.dismiss();
		    }
		});
		
		builder.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg2.getKeyCode()==KeyEvent.KEYCODE_BACK){
					Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Para atrás!!!", Toast.LENGTH_SHORT).show();
					return false;
				}
				return false;
			}
			
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void createXMLTemplate(String fileName,int routeId){
		
		File file = new File(ResourceManager.getInstance().getRootPath()+"/tmp/"+fileName+".xml");
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOutputStream fileos = null;
		
		try {
			fileos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XmlSerializer serializer = Xml.newSerializer();
		
		try {
			
			serializer.setOutput(fileos, "UTF-8");
			serializer.startDocument(null, null);
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
			
			serializer.startTag(null, "route");
			serializer.attribute(null, "id", Integer.toString(routeId));
					
					serializer.startTag(null, "name");
		            serializer.endTag(null, "name");
					serializer.startTag(null, "description");
		            serializer.endTag(null, "description");
					serializer.startTag(null, "icon");
		            serializer.endTag(null, "icon");
		            
					serializer.startTag(null, "points");
							
							serializer.startTag(null, "point");
							serializer.attribute(null, "id", "1");
							
									serializer.startTag(null, "coords");
									serializer.attribute(null, "x", "0");
									serializer.attribute(null, "y", "0");
						            serializer.endTag(null, "coords");
						            
									serializer.startTag(null, "title");
						            serializer.endTag(null, "title");
						            
									serializer.startTag(null, "pointdescription");
						            serializer.endTag(null, "pointdescription");
						            
									serializer.startTag(null, "pointicon");
						            serializer.endTag(null, "pointicon");
						            
									serializer.startTag(null, "image");
						            serializer.endTag(null, "image");
						            
									serializer.startTag(null, "video");
						            serializer.endTag(null, "video");
						            
									serializer.startTag(null, "ar");
						            serializer.endTag(null, "ar");
							
				            serializer.endTag(null, "point");
					
					
		            serializer.endTag(null, "points");
		            
		
			serializer.endTag(null, "route");
			
			serializer.endDocument();
			serializer.flush();
			fileos.close();
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Plantilla Creada", Toast.LENGTH_SHORT).show();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
