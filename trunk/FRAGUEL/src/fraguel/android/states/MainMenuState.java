package fraguel.android.states;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import fraguel.android.State;

public class MainMenuState extends State {

	public static final int STATE_ID = 15;

	// Variables menu de opciones
	private static final int MENU_MAIN = 1;
	private static final int MENU_CONFIG = 2;
	private static final int MENU_ROUTE = 3;
	private static final int MENU_EXIT = 4;

	protected TextView gps;
	protected TextView orientation;
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
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_interactivemode:
			Toast.makeText(FRAGUEL.getInstance().getApplicationContext(), "Por definir", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_manager:
			FRAGUEL.getInstance().changeState(RouteManagerState.STATE_ID);
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
		return menu;
	}

	@Override
	public boolean onStateOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
