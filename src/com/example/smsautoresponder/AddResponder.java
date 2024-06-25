package com.example.smsautoresponder;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smsautoresponder.R;

public class AddResponder extends Activity {
	public static final String TAG = "AddResponder";
	ListView lvContacts;
	Context context;
	ProgressBar pb;
	TextView tvTitle;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addresponder);

		// tv = (TextView) findViewById(R.id);//no of contacts
		context = AddResponder.this;
		lvContacts = (ListView) findViewById(R.id.lstContacts);
		Button btnrespond = (Button) findViewById(R.id.btnAddSituation);
		Button btnResponderList = (Button) findViewById(R.id.btnResponderList);
		tvTitle = (TextView) findViewById(R.id.txtTitle);
		pb = (ProgressBar) findViewById(R.id.pbProgress);

		btnrespond.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddResponder.this,
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

		
		btnResponderList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddResponder.this,
						ResponderListOptionActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
		Button btnAddNumber = (Button) findViewById(R.id.btnAddNumber);
		btnAddNumber.setOnClickListener(new OnClickAddNumberManually());

		new LoadContactsThreaded().execute();

		// List<Contact> lc = LoadContacts(AddResponder.this);
		// ContactsAdapter ca = new ContactsAdapter(AddResponder.this,
		// R.layout.row_contact_list, lc);
		// lvContacts.setAdapter(ca);

	}

	public class OnClickAddNumberManually implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AddResponder.this,
					AddResponderFormActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	}

	public class AddToResponder implements OnClickListener {

		@Override
		public void onClick(View v) {
			TextView tvNameRow = ((TextView) v.findViewById(R.id.tvName));
			TextView tvNumberRow = ((TextView) v.findViewById(R.id.tvNumber));
			final ImageView imgAlert = ((ImageView) v.findViewById(R.id.imgAlert));

			final Contact contact = new Contact(0, tvNumberRow.getText().toString(),
					tvNameRow.getText().toString());
			

			Builder builder = new Builder(AddResponder.this);
			builder.setTitle("Add/Remove From ResonderList")
					.setMessage("Are you sure?")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									
							DataAccess da = new DataAccess(AddResponder.this);
			               if (da.CheckExistingResponderContact(contact)) { // if contact is in
																// RL
				                 Log.d(TAG, "Contact already there");
				                da.RemoveResponderListByNumber(contact);
				                imgAlert.setImageResource(R.drawable.contacticon);
				                Toast.makeText(AddResponder.this,
				                "Contact " + contact.getResponder_contact()
								+ " Removed from responder list",
								Toast.LENGTH_LONG).show();

			               	} 
			               else 
			               { 
			            	   // Contact not in RL
			               
				             int rid = da.GetMaxResponderID();
				             contact.setResponder_id(rid);
				             da.AddResponderList(contact);
				             da.CloseDataAccess();
				             imgAlert.setImageResource(R.drawable.iresponder);
				             Toast.makeText( AddResponder.this,
				             "Contact: " + contact.getResponder_contact()
								+ " Added in responder list", Toast.LENGTH_LONG)
								.show();
			               }
								}
											}).setNegativeButton("No", null).show();
									
								}
								
							}

				

	   private class LoadContactsThreaded extends
			AsyncTask<Void, Integer, List<Contact>> {
		@Override
		protected List<Contact> doInBackground(Void... params) {
			Cursor c; // to hold contact list names
			Cursor cursor; // cursor to hold phone numbers for each contact
			List<Contact> contactList = new ArrayList<Contact>();// will store
																	// contacts
																	// from
																	// System
			DataAccess da = new DataAccess(context);
			String col[] = { ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME }; // What to select
																// from
																// Contacts DB
			tvTitle.setText("Loading Contacts...");
			da.ZapContacts();
			c = getContentResolver().query(
					ContactsContract.Contacts.CONTENT_URI, col, null, null,
					ContactsContract.Contacts.DISPLAY_NAME);
			// tv.setText("Total Contacts: " + c.getCount());
			c.moveToFirst();
			pb.setMax(c.getCount());
			int count = 1;// for progress bar update
			while (c.moveToNext()) {
				count++;
				publishProgress(count);
				String strID = c.getString(c.getColumnIndex(Contacts._ID));
				String strName = c.getString(c
						.getColumnIndex(Contacts.DISPLAY_NAME));
				String strNumber = "";

				if (strID == null || strName == null)
					continue;

				cursor = context.getContentResolver().query(
						CommonDataKinds.Phone.CONTENT_URI, null,
						CommonDataKinds.Phone.CONTACT_ID + "=?",
						new String[] { strID }, null);

				if (cursor == null)
					continue;

				while (cursor.moveToNext()) {
					strNumber = cursor.getString(cursor
							.getColumnIndex(CommonDataKinds.Phone.NUMBER));
				}
				Log.d(TAG, strID + " " + strName + " " + strNumber);
				Contact contact = new Contact();
				contact.setResponder_id(Integer.parseInt(strID));
				contact.setResponder_contact(strName);
				// contact.setResponder_number(strNumber.replace("-", ""));
				// //replaces - in Phone No
				String strNumber1 = strNumber.replace("-", "");
				contact.setResponder_number(strNumber1.replace(" ", "")); // replaces
																			// -
																			// in
																			// Phone
																			// No
				da.InsertContact(contact);
				da.CloseDataAccess();
				contactList.add(contact);
				cursor.close();
			}
			return contactList;
		}

		@Override
		protected void onPostExecute(List<Contact> result) {
			super.onPostExecute(result);
			ContactsAdapter ca = new ContactsAdapter(AddResponder.this,
					R.layout.row_contact_list, result);
			lvContacts.setAdapter(ca);
			tvTitle.setText("Tap to Add to Responder List");

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			pb.setProgress(values[0]);
			tvTitle.setText("Loading Contacts, Please Wait...");
		}

	}

	private class ContactsAdapter extends ArrayAdapter<Contact> {
		private int layout_file;
		private List<Contact> contactList;

		public ContactsAdapter(Context context, int resource,
				List<Contact> objects) {
			super(context, resource, objects);
			layout_file = resource;
			contactList = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(layout_file, null);
			}

			Log.d(TAG, "After Inflater");

			TextView tvNameRow = ((TextView) row.findViewById(R.id.tvName));
			TextView tvNumberRow = ((TextView) row.findViewById(R.id.tvNumber));
			ImageView imgAlert = ((ImageView) row.findViewById(R.id.imgAlert));

			tvNameRow.setText(contactList.get(position).getResponder_contact());
			tvNumberRow
					.setText(contactList.get(position).getResponder_number());

			if (contactList.get(position).getResponder_number() != "") {
				DataAccess da = new DataAccess(AddResponder.this);
				imgAlert.setImageResource(R.drawable.contacticon);
				if (da.CheckExistingResponderContact(contactList.get(position))) {
					imgAlert.setImageResource(R.drawable.iresponder);
				}
				da.CloseDataAccess();// //
				row.setOnClickListener(new AddToResponder());
			}
			return row;
		}
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
			Intent intentAddSituation = new Intent(AddResponder.this,
					AddSituationActivity.class);
			startActivity(intentAddSituation);
			return true;

		case R.id.mnuSelectContacts:
			Intent intent = new Intent(AddResponder.this, AddResponder.class);
			startActivity(intent);
			return true;

		case R.id.mnuManualContact:
			Intent intentM = new Intent(AddResponder.this,
					AddResponderFormActivity.class);
			startActivity(intentM);
			return true;

		case R.id.mnuResponderList:
			Intent intentRL = new Intent(AddResponder.this,
					ResponderListActivity.class);
			startActivity(intentRL);
			return true;

		default:
			return false;

		}
	}
}
