package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TourActivity_A extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_a);
	
	Button clickbtnnext = ( Button ) findViewById(R.id.btnnexta);
	
	clickbtnnext.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent ( TourActivity_A.this,TourActivity_B.class);
			startActivity ( intent );
			TourActivity_A.this.finish();
		}
	});

}
}
