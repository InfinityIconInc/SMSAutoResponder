package com.example.smsautoresponder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LayoutActivity1 extends Activity{
	public static String TAG=".MainActivity1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout1);
		
		Button btnstartblocking = ( Button ) findViewById(R.id.startResponder);
		Button btntaketour = ( Button ) findViewById(R.id.taketour);
		
		
       btnstartblocking.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent ( LayoutActivity1.this,TabsActivity.class);
				startActivity ( intent );
			}
		});
   btntaketour.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent ( LayoutActivity1.this,SplashScreenActivity .class);
		startActivity ( intent );
	}
});

	}
	
}
