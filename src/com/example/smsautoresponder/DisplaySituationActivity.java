package com.example.smsautoresponder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.example.smsautoresponder.R;

public class DisplaySituationActivity extends Activity implements
		OnItemClickListener {
	SimpleCursorAdapter adapter;
	Context context;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_situation);

		context = DisplaySituationActivity.this;

		DataAccess ds = new DataAccess(DisplaySituationActivity.this);
		ListView lv = (ListView) findViewById(R.id.lvSavedSituations);
		Cursor c = ds.DisplayAllSituations();
		adapter = new SimpleCursorAdapter(DisplaySituationActivity.this,
				R.layout.row_situation, c, new String[] { DataAccess.S_NAME, DataAccess.S_MSG,
						DataAccess.S_ID }, new int[] { R.id.txtST, R.id.txtSM,
						R.id.txtSID });
		lv.setOnItemClickListener(this);
		lv.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView tvST = ((TextView) view.findViewById(R.id.txtST));
		TextView tvSM = ((TextView) view.findViewById(R.id.txtSM));
		TextView tvSID = ((TextView) view.findViewById(R.id.txtSID));

		Intent intent = new Intent(DisplaySituationActivity.this,
				ManageSituationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("STitle", tvST.getText().toString().trim());
		intent.putExtra("SMessage", tvSM.getText().toString().trim());
		intent.putExtra("SID",
				Integer.parseInt(tvSID.getText().toString().trim()));
		startActivity(intent);
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
			Intent intentAddSituation = new Intent ( DisplaySituationActivity.this, AddSituationActivity.class);
			startActivity ( intentAddSituation );
			return true;
			
		case R.id.mnuSelectContacts:
			Intent intent = new Intent ( DisplaySituationActivity.this, AddResponder.class);
			startActivity ( intent );
			return true;
			
		case R.id.mnuManualContact:
			Intent intentM = new Intent ( DisplaySituationActivity.this, AddResponderFormActivity.class);
			startActivity (intentM );
			return true;

		case R.id.mnuResponderList:
			Intent intentRL = new Intent ( DisplaySituationActivity.this, ResponderListActivity.class );
			startActivity ( intentRL );
			return true;
			
		default:
			return false;
		}
	}
}
