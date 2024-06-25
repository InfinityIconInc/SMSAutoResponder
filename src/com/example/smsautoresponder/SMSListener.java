package com.example.smsautoresponder;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class SMSListener extends BroadcastReceiver {
	DataAccess da;
	Contact contact;
	public static final String TAG = "SMSListener";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("BR", "onReceive");
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras();
			SmsMessage[] msg = null;
			String msg_sender = "";
			String msg_senderSUB = "";
			String msg_body = "";
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msg = new SmsMessage[pdus.length];
				for (int i = 0; i < msg.length; i++) {
					msg[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					msg_sender = msg[i].getOriginatingAddress();
					msg_body = msg[i].getMessageBody();
				}
			}
			msg_senderSUB = msg_sender;
			msg_senderSUB = msg_senderSUB.substring(3);// /countrycode elimination
			contact = new Contact(0, msg_senderSUB, "");
			Toast.makeText(context, msg_sender + " " + msg_body,
					Toast.LENGTH_LONG).show();
			da = new DataAccess(context);
			if (da.CheckExistingResponderContact(contact)
					|| da.GetRespondStatus() == 1) {
				Log.d("BR", "Inside Reply");
				Situation s = da.GetActiveSituation();
				SmsManager smsManager = SmsManager.getDefault();
				Log.d(TAG, "BEING SENT TO: " + msg_sender);
				Log.d(TAG, "Message: " + s.getSituation_msg());
				smsManager.sendTextMessage(msg_sender, null,
						s.getSituation_msg(), null, null);
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
				SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss");
				String strDate = sdf.format(new Date());
				String strTime = stf.format(new Date());
				String contact_name = da.GetContactByNumber(msg_senderSUB);
				da.InsertResponderHistory(new ResponderHistory(0, strDate,
						strTime, msg_body, s.getSituation_msg(), contact_name + ": " + msg_sender));
				Toast.makeText(context, "Replying", Toast.LENGTH_LONG).show();
			}
			else if ( da.GetRespondStatus() == 2 ) {
				if ( da.CheckExistingContact(msg_senderSUB)) {
					Log.d("BR", "Inside Contact Reply");
					Situation s = da.GetActiveSituation();
					SmsManager smsManager = SmsManager.getDefault();
					Log.d(TAG, "BEING SENT TO: " + msg_sender);
					Log.d(TAG, "Message: " + s.getSituation_msg());
					smsManager.sendTextMessage(msg_sender, null,
							s.getSituation_msg(), null, null);
					SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
					SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss");
					String strDate = sdf.format(new Date());
					String strTime = stf.format(new Date());
					da.InsertResponderHistory(new ResponderHistory(0, strDate,
							strTime, msg_body, s.getSituation_msg(), msg_sender));
					Toast.makeText(context, "Replying", Toast.LENGTH_LONG).show();
				}			
			}
				da.CloseDataAccess();
			}
		}
	}

