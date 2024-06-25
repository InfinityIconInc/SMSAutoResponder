package com.example.smsautoresponder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TourActivity_C extends Activity{
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tour_c);
		
		Button clickbtnfinish = ( Button ) findViewById(R.id.btnfinish);
		
		clickbtnfinish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent ( TourActivity_C.this,MainActivity.class);
				startActivity ( intent );
				TourActivity_C.this.finish();
			}
		});

}
}
