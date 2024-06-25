package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.smsautoresponder.R;

public class AddSituationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_situation);
           
            
    		Button button = (Button) findViewById(R.id.btnSaveSituation);
    		button.setOnClickListener(new View.OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				EditText sname = (EditText) findViewById(R.id.txtname);
    				EditText msg = (EditText) findViewById(R.id.txtmsg);

    				DataAccess ds = new DataAccess(AddSituationActivity.this);
    				int sid = ds.GetMaxSituationID();
    				Situation sit = new Situation(sid, sname.getText()
    						.toString(), msg.getText().toString(), 0);
    				long id = ds.insertSituation(sit);
    				Toast.makeText(AddSituationActivity.this,"Situation " + id + " is Added" ,Toast.LENGTH_LONG).show();
    				ds.CloseDataAccess();
    				Intent intent = new Intent(AddSituationActivity.this,DisplaySituationOptionActivity.class);
    				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    				startActivity(intent);
    				
    			}
    		});
	}}