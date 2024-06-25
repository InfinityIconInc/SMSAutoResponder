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
import android.widget.TextView;
import android.widget.Toast;
import com.example.smsautoresponder.R;

public class ManageSituationActivity extends Activity {
	TextView tvSID;
	EditText etSN;
	EditText etSM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_situation);

		etSN = (EditText) findViewById(R.id.txtSN);
		etSM = (EditText) findViewById(R.id.txtSMessage);
		tvSID = (TextView)findViewById(R.id.txtSituationID);
		Button btnSave = (Button) findViewById(R.id.btnSave);
		Button btnActivate = (Button) findViewById(R.id.btnActivate);
		Button btnDelete = ( Button ) findViewById(R.id.btnDelete);
		
		etSN.setText(getIntent().getExtras().getString("STitle"));
		etSM.setText(getIntent().getExtras().getString("SMessage"));
		tvSID.setText(Integer.toString(getIntent().getExtras().getInt("SID")));
		
		btnActivate.setOnClickListener(new ActivateSituationButtonClick ( ));
		btnSave.setOnClickListener(new EditSituationButtonClick ( ));
		btnDelete.setOnClickListener(new DeleteSituationButtonClick ( ));
	}

	public class ActivateSituationButtonClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			Context context = ManageSituationActivity.this;
			DataAccess da = new DataAccess(context);
			da.ActivateSituation(Integer.parseInt(tvSID.getText().toString()));
			da.CloseDataAccess();
			Toast.makeText(context, "Situation Activated", Toast.LENGTH_LONG)
					.show();
			Intent intentActive = new Intent(ManageSituationActivity.this ,MainActivity.class);
			intentActive.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentActive);
		}
	}
	
	public class EditSituationButtonClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			Context context = ManageSituationActivity.this;
			DataAccess da = new DataAccess ( context );
			int iSID = Integer.parseInt(tvSID.getText().toString());
			String strSName = etSN.getText().toString().trim();
			String strSMessage = etSM.getText().toString().trim();
			Situation s = new Situation ( iSID, strSName, strSMessage, 0 );
			if ( da.EditSituation(s) > 0 ) {
				Toast.makeText(context, "Situation Successfully Edited", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(ManageSituationActivity.this ,DisplaySituationActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			}
			else
				Toast.makeText(context, "Error Editing Situation", Toast.LENGTH_LONG).show();
			
			da.CloseDataAccess();
		}		
	}
	
	public class DeleteSituationButtonClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			Context context = ManageSituationActivity.this;
			DataAccess da = new DataAccess ( context );
			int iSID = Integer.parseInt(tvSID.getText().toString());
			da.DeleteSituation(iSID);
			Toast.makeText(context, "Situation Deleted", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(ManageSituationActivity.this ,DisplaySituationActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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
			Intent intentAddSituation = new Intent ( ManageSituationActivity.this, AddSituationActivity.class);
			startActivity ( intentAddSituation );
			return true;
			
		case R.id.mnuSelectContacts:
			Intent intent = new Intent ( ManageSituationActivity.this, AddResponder.class);
			startActivity ( intent );
			return true;
			
		case R.id.mnuManualContact:
			Intent intentM = new Intent ( ManageSituationActivity.this, AddResponderFormActivity.class);
			startActivity (intentM );
			return true;

		case R.id.mnuResponderList:
			Intent intentRL = new Intent ( ManageSituationActivity.this, ResponderListActivity.class );
			startActivity ( intentRL );
			return true;
			
		default:
			return false;
			
		}
	}
}
