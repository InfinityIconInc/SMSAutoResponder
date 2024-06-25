package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditSituationActivity extends Activity {
	TextView tvSID;
	EditText etSN;
	EditText etSM;
	Context context = EditSituationActivity.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_situation);

		etSN = (EditText) findViewById(R.id.txtSN);
		etSM = (EditText) findViewById(R.id.txtSMessage);
		tvSID = (TextView)findViewById(R.id.txtSituationID);
		
		Button btnSave = (Button) findViewById(R.id.btnSave);
		int iSID = (getIntent().getExtras().getInt("SID"));
		DataAccess da = new DataAccess ( context );
		Situation situation = da.GetSituation(iSID);
		da.CloseDataAccess();
		etSN.setText(situation.getSituation_name());
		etSM.setText(situation.getSituation_msg());
		tvSID.setText(Integer.toString(getIntent().getExtras().getInt("SID")));
		btnSave.setOnClickListener(new EditSituationButtonClick ( ));
	}
		public class EditSituationButtonClick implements OnClickListener {

			@Override
			public void onClick(View v) {
				Context context = EditSituationActivity.this;
				DataAccess da = new DataAccess ( context );
				int iSID = Integer.parseInt(tvSID.getText().toString());
				String strSName = etSN.getText().toString().trim();
				String strSMessage = etSM.getText().toString().trim();
				Situation s = new Situation ( iSID, strSName, strSMessage, 0 );
				if ( da.EditSituation(s) > 0 ) {
					Toast.makeText(context, "Situation Successfully Edited", Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(EditSituationActivity.this ,DisplaySituationOptionActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				}
				else
					Toast.makeText(context, "Error Editing Situation", Toast.LENGTH_LONG).show();
				
				da.CloseDataAccess();
			}		
		}
	
	}

