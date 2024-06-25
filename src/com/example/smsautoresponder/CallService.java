package com.example.smsautoresponder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;
import com.example.smsautoresponder.R;

public class CallService extends Service {
	NotificationManager mNotificationManager;
	private int count = 0;
	private static final int NOTIFY_ME_ID = 1327;

	public static boolean bStarted;
	SMSListener sl = new SMSListener ( );
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter ("android.provider.Telephony.CALL_RECEIVED" );
		//SMSListener sl = new SMSListener ( );
		registerReceiver ( sl, filter );
		bStarted = true;
		Toast.makeText(CallService.this, "Receiver Started", Toast.LENGTH_LONG).show();
		setNotification ("SMS Responder Started");
	}

	@Override
	public void onDestroy() {
		unregisterReceiver ( sl );
		super.onDestroy();
		bStarted = false;
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