package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smsautoresponder.R;

public class AddResponderFormActivity extends Activity implements
		OnClickListener {
	Context context;
	EditText txtCN;
	EditText txtCNumb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_responder_form);

		context = AddResponderFormActivity.this;

		txtCN = (EditText) findViewById(R.id.txtCN);
		txtCNumb = (EditText) findViewById(R.id.txtCNumb);

		Button btnSaveManual = (Button) findViewById(R.id.btnSaveManual);
		btnSaveManual.setOnClickListener(this);
		
		Button btnHome = ( Button ) findViewById(R.id.btnHome);
		btnHome.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if ( v.getId() == R.id.btnSaveManual ) {
		DataAccess da = new DataAccess(context);
		Contact contact = new Contact(0, txtCNumb.getText().toString().trim(), txtCN.getText().toString().trim());
		if ( da.InsertResponder(contact) > 0 ){
			
		
			Toast.makeText(context, "Contact Added", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(AddResponderFormActivity.this, ResponderListOptionActivity.class);
		startActivity(intent);
		 AddResponderFormActivity.this.finish();
		}
		
		else
			Toast.makeText(context, "Couldn't Add Contact...", Toast.LENGTH_LONG).show();
		}
		if ( v.getId() == R.id.btnHome) {
			Intent intentHome = new Intent ( context, MainActivity.class);
			startActivity ( intentHome);
			 AddResponderFormActivity.this.finish();
		}
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
