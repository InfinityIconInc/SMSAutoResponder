package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
	public final int DELAY = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(SplashScreenActivity.this,
						MainActivity.class);
				SplashScreenActivity.this.startActivity ( intent );
				SplashScreenActivity.this.finish();
			}
		}, DELAY);
	}
}