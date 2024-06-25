package com.example.smsautoresponder;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smsautoresponder.R;

public class ResponderListActivity extends Activity implements
		OnItemClickListener {
	Context context;
	SimpleCursorAdapter adapter;
	ListView lvRL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_responder_list);
		context = ResponderListActivity.this;
		lvRL = (ListView) findViewById(R.id.lvRL);

		LoadListView ( );	
		lvRL.setOnItemClickListener(this);
		
		
		
		
		Button btnDeleteAllResponder = (Button)findViewById(R.id.btnDeleteAllResponder);
		btnDeleteAllResponder.setOnClickListener(new View.OnClickListener() {
			

			public void onClick(View v) {
				
				Builder builder = new Builder(context);
				
				builder.setTitle("Delete Responder Contact")
						.setMessage("Do you want to del all Responder Contact?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
				                     DataAccess da = new DataAccess(context);
				                     Toast.makeText(ResponderListActivity.this,
						              da.RemoveAllResponderList() + " Deleted",
					                 	Toast.LENGTH_LONG).show();
				                        da.CloseDataAccess();
				                         LoadListView();
		                              	}
									
						               }).setNegativeButton("No", null).show();
			                       }
			
		                        });
			
	                          LoadListView();
		
	                       }
	
	
	@SuppressWarnings("deprecation")
	private void LoadListView () {
		DataAccess da = new DataAccess(context);
		Cursor c = da.DisplayAllResponders();
		adapter = new SimpleCursorAdapter(context,
				android.R.layout.simple_list_item_2, c, new String[] {
						DataAccess.R_CONTACT, DataAccess.R_NUMBER }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		              
	
		lvRL.setAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		final TextView tvNumber = ((TextView) view
				.findViewById(android.R.id.text2));
		final Contact contact = new Contact(0, tvNumber.getText()
				.toString().trim(), tvNumber.getText()
				.toString().trim());

		Builder builder = new Builder(context);
		builder.setTitle("Delete Responder")
				.setMessage(
						"Are you sure you want to delete "
								+ tvNumber.getText().toString() + "?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								DataAccess da = new DataAccess(context);
								
								Toast.makeText(
										context,
										"Contact Deleted: "
												+ da.RemoveResponderListByNumber(contact),
										Toast.LENGTH_LONG).show();
								LoadListView ( );
							}
						}).setNegativeButton("No", null).show();
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
		case R.id.mnuAddSituation:
			Intent intentAddSituation = new Intent ( context, AddSituationActivity.class);
			startActivity ( intentAddSituation );
			return true;
			
		case R.id.mnuSelectContacts:
			Intent intent = new Intent ( context, AddResponder.class);
			startActivity ( intent );
			return true;
			
		case R.id.mnuManualContact:
			Intent intentM = new Intent ( context, AddResponderFormActivity.class);
			startActivity (intentM );
			return true;

		case R.id.mnuResponderList:
			Intent intentRL = new Intent ( context, ResponderListActivity.class );
			startActivity ( intentRL );
			return true;

		default:
			return false;
			
		}
	}
}
