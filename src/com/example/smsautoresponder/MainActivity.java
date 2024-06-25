package com.example.smsautoresponder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.smsautoresponder.R;

public class MainActivity extends Activity {

	ToggleButton tbStartResponder;
	Button btnContacts;
	Button btnSituations;
	Button btnViewSituation;
	Button btnRL; // Responder List
	Button btnSR; // Show Response
	
	RadioGroup rgOptions;
	RadioButton rbRRL; //RespondResponderList
	RadioButton rbRE; //Respond Everyone
	
	Context context;
	DataAccess da;
	NotificationManager mNotificationManager;
	private int count = 0;
	private static final int NOTIFY_ME_ID = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;
		Button btntaketour = (Button) findViewById(R.id.taketour);
		ImageButton btnnext =(ImageButton)findViewById(R.id.btnnext);
///////////////
		
		
		btnnext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent ( MainActivity.this, TabsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity ( intent);
				//MainActivity.this.finish();
			}
		});

		btntaketour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TourActivity_A.class);
				startActivity(intent);
				MainActivity.this.finish();

			}
		});
		
		tbStartResponder = (ToggleButton) findViewById(R.id.tbtnStartResponder);
		
		rbRE = (RadioButton) findViewById(R.id.rbRE);
		rbRRL= (RadioButton) findViewById(R.id.rbRRL);
		rgOptions = (RadioGroup) findViewById(R.id.rgOptions);
		rgOptions.setOnCheckedChangeListener(new OnCheckedChangeListener () {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch ( checkedId ) {
					case R.id.rbRRL:
						da = new DataAccess(context);
						if (da.GetRespondStatus() == 0)
							Toast.makeText(context, "Status Already Set to Respond Responder List", Toast.LENGTH_SHORT).show();
						else {
							if  ( da.SetRespondStatus(0) > 0 )
								Toast.makeText(context, "Status set to Respond Responder List", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(context, "Error Setting Status to Respond Responder List", Toast.LENGTH_SHORT).show();
						}
						da.CloseDataAccess();
						break;
					
					case R.id.rbRE:
						da = new DataAccess(context);
						if (da.GetRespondStatus() == 1)
							Toast.makeText(context, "Status Already Set to Respond Everyone", Toast.LENGTH_SHORT).show();
						else {
							if  ( da.SetRespondStatus(1) > 0 )
								Toast.makeText(context, "Status set to Respond Everyone", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(context, "Error Setting Status to Respond Everyone", Toast.LENGTH_SHORT).show();
							da.CloseDataAccess();
							break;

						}
					case R.id.rbRC:
						da = new DataAccess(context);
						if (da.GetRespondStatus() == 2)
							Toast.makeText(context, "Status Already Set to Respond Contacts", Toast.LENGTH_SHORT).show();
						else {
							if  ( da.SetRespondStatus(2) > 0 )
								Toast.makeText(context, "Status set to Respond Contacts", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(context, "Error Setting Status to Respond Contacts", Toast.LENGTH_SHORT).show();
						}
						
						da.CloseDataAccess();
						break;
				}
			}
			
		});
		// /////////////////////////////////////////////TOGGLE BUTTON ON OFF
		if (ResponderService.bStarted) {
			tbStartResponder.setChecked(true);
		}
		else
			tbStartResponder.setChecked(false);
		// /////////////////////////////////////////////radiobutton checked update
		da = new DataAccess(context);
		if ( da.GetRespondStatus() == 1)
			rbRE.setChecked(true);
		else if ( da.GetRespondStatus() == 0 )
			rbRRL.setChecked(true);
		da.CloseDataAccess();
		// ////////////////////////////////////////////
		
		

		
		tbStartResponder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean bStart = tbStartResponder.isChecked();
				if (bStart) {
					Intent intentService = new Intent(MainActivity.this,
							ResponderService.class);
					startService(intentService);
					Toast.makeText(MainActivity.this, "Responder is ON",
							Toast.LENGTH_LONG).show();
				} else {
					Intent intentService = new Intent(MainActivity.this,
							ResponderService.class);
					stopService(intentService);
					Toast.makeText(MainActivity.this,
							"Responder is Turned OFF", Toast.LENGTH_LONG)
							.show();
				}

			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnuAddSituation:
			Intent intentAddSituation = new Intent(MainActivity.this,
					AddSituationActivity.class);
			startActivity(intentAddSituation);
			return true;

		case R.id.mnuSelectContacts:
			Intent intent = new Intent(MainActivity.this, AddResponder.class);
			startActivity(intent);
			return true;

		case R.id.mnuManualContact:
			Intent intentM = new Intent(MainActivity.this,
					AddResponderFormActivity.class);
			startActivity(intentM);
			return true;

		case R.id.mnuResponderList:
			Intent intentRL = new Intent(MainActivity.this,
					ResponderListActivity.class);
			startActivity(intentRL);
			return true;

		default:
			return false;

		}
	}

	private void setNotification(String notificationMessage) {

		// **add this line**
		int requestID = (int) System.currentTimeMillis();
		final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.notifyicon, "SMS Responder is On",
				System.currentTimeMillis());

		// Uri alarmSound = getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		// mNotificationManager =
		// getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

		Intent notificationIntent = new Intent(getApplicationContext(),
				MainActivity.class);

		// **add this line**
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		// **edit this line to put requestID as requestCode**
		PendingIntent i = PendingIntent.getActivity(this, requestID,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// NotificationCompat.Builder mBuilder = new
		// NotificationCompat.Builder(getApplicationContext())
		// .setSmallIcon(R.drawable.logo)
		// .setContentTitle("My Notification")
		// .setStyle(new NotificationCompat.BigTextStyle()
		// .bigText(notificationMessage))
		// .setContentText(notificationMessage).setAutoCancel(true);
		// //mBuilder.setSound(alarmSound);
		// mBuilder.setContentIntent(contentIntent);
		// //mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		note.setLatestEventInfo(this, "Notification Title",
				"This is the notification message", i);
		note.number = ++count;
		mNotificationManager.notify(NOTIFY_ME_ID, note);

	}
}
