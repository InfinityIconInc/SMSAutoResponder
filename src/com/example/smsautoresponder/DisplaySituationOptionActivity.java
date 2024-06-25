package com.example.smsautoresponder;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DisplaySituationOptionActivity extends Activity {
	SimpleCursorAdapter adapter;
	Context context;
	String[] menuoptions = { "Options", };

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_situation);
		LoadHistory();
	}

	@SuppressWarnings("deprecation")
	public void LoadHistory() {
		ListView lv = (ListView) findViewById(R.id.lvSavedSituations);
		// lv.setOnItemClickListener(this);
		context = DisplaySituationOptionActivity.this;

		DataAccess ds = new DataAccess(DisplaySituationOptionActivity.this);
		// ListView lv = (ListView) findViewById(R.id.lvSavedSituations);
		Cursor c = ds.DisplayAllSituations();
		adapter = new SimpleCursorAdapter(DisplaySituationOptionActivity.this,
				R.layout.row_situation, c, new String[] { DataAccess.S_NAME,
						DataAccess.S_MSG, DataAccess.S_ID }, new int[] {
						R.id.txtST, R.id.txtSM, R.id.txtSID });
		// /lv.setOnItemClickListener(this);2
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
		.getMenuInfo();
		if (item.getTitle() == "Activate") {
//			Toast.makeText(this, "ID:" + info.id + " Pos:" + info.position,
//					Toast.LENGTH_SHORT).show();
			Context context = DisplaySituationOptionActivity.this;
			DataAccess da = new DataAccess(context);
			//TextView txtSID = (TextView) findViewById(R.id.txtSID);

			// da.ActivateSituation(Integer.parseInt(txtSID.getText().toString()));
			da.ActivateSituation((int) (info.id));
			da.CloseDataAccess();
			Toast.makeText(context, info.id + "Situation Activated", Toast.LENGTH_LONG)
					.show();
		} 
		
		else if (item.getTitle() == "Delete ALL") {
			Builder builder = new Builder(context);
			builder.setTitle("Delete All Situations")
					.setMessage("Do you want to del all Situations?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DataAccess da = new DataAccess(context);
									int i = da.ZapAllSituation();
									Toast.makeText(
											context,
											"All " + i
													+ " Situation Deleted...",
											Toast.LENGTH_LONG).show();
									da.CloseDataAccess();
									LoadHistory();
								}
							}).setNegativeButton("No", null).show();

			// Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
		}

		else if (item.getTitle() == "Edit") {
			
			Intent intent = new Intent(this, EditSituationActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("SID", (int)info.id);
			startActivity(intent);
			Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
		}

		else if (item.getTitle() == "Delete") {
			final int iSID = (int) info.id;
					DataAccess da = new DataAccess(context);
					da.DeleteSituation(iSID);
					Toast.makeText(context, " Situation Successfully Deleted",
							Toast.LENGTH_SHORT).show();
					LoadHistory();
					da.CloseDataAccess();
		}

		else {
			Builder builder = new Builder(context);
			builder.setTitle("Delete Situation")
					.setMessage("Do you want to Del all Situations?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DataAccess da = new DataAccess(context);
									int i = da.ZapAllSituation();
									Toast.makeText(
											context,
											"All " + i
													+ " Situation Deleted...",
											Toast.LENGTH_LONG).show();
									da.CloseDataAccess();
									LoadHistory();
								}
							}).setNegativeButton("No", null).show();

			Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.lvSavedSituations) {// /////////////////////////
			String[] menuItems = { "Edit", "Activate", "Delete All", "Delete" };
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(0, i, i, menuItems[i]);
			}
		}

	}
}
