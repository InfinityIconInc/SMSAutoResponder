package com.example.smsautoresponder;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class SituationActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView ( R.layout.activity_tab);
		
		TabHost host = getTabHost();
		
		host.addTab(host.newTabSpec("tag1")
				.setIndicator("ADD")
				.setContent(new Intent ( this, AddSituationActivity.class))
				);
		
		host.addTab(host.newTabSpec("tag2")
				.setIndicator("Display")
				.setContent(new Intent (this, DisplaySituationOptionActivity.class)));
		
		
	}
}
