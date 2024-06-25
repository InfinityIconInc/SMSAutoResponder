package com.example.smsautoresponder;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class TabsActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView ( R.layout.activity_tab);
		
		TabHost host = getTabHost();
		/*
		host.addTab(host.newTabSpec("tag1")
				.setIndicator("on/off")
				.setContent(new Intent ( this, MainActivity.class))
				);*/
		
		host.addTab(host.newTabSpec("tag2")
				.setIndicator("Responder")
				.setContent(new Intent (this, AddResponder.class)));
		
		host.addTab(host.newTabSpec("tag3")
				.setIndicator("Situation")
				.setContent(new Intent ( this, 	SituationActivity.class))
				);
		
		host.addTab(host.newTabSpec("tag3")
				.setIndicator("Responces")
				.setContent(new Intent ( this, ResponseHistoryActivity.class))
				);
		
	}
}
