package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TourActivity_B extends Activity{
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tour_b);
		
			Button clickbtnnext = ( Button ) findViewById(R.id.btnnextb);
		
		clickbtnnext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent ( TourActivity_B.this,TourActivity_C.class);
				startActivity ( intent );
				TourActivity_B.this.finish();
			}
		});

}
}