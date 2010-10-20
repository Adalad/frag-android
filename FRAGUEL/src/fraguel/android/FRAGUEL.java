package fraguel.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FRAGUEL extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button b = (Button)this.findViewById(R.id.btn_close);
        b.setOnClickListener(new OnClickListener(){ 
        	public void onClick(View arg0) { 
        		System.exit(0); 
        		} });
    }
}