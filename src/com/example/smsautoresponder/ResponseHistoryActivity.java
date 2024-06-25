package com.example.smsautoresponder;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smsautoresponder.R;

public class ResponseHistoryActivity extends ListActivity {
	Context context = ResponseHistoryActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_responsehistory);
		LoadList();

		Button btnDelAll = (Button) findViewById(R.id.btnDeleteAll);
		btnDelAll.setOnClickListener(new View.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				
				Builder builder = new Builder(context);
				
				builder.setTitle("Delete Response History")
						.setMessage("Do you want to del all Response History?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
				DataAccess da = new DataAccess(context);
				Toast.makeText(ResponseHistoryActivity.this,
						da.DeleteAllResponderHistory() + " Deleted",
						Toast.LENGTH_LONG).show();
				da.CloseDataAccess();
				LoadList();
			}
									
						}).setNegativeButton("No", null).show();
			}
			
		});
			
	     LoadList();
		
	}

	void LoadList() {
		DataAccess da = new DataAccess(context);
		setListAdapter(new SimpleCursorAdapter(context,
				R.layout.row_response_history, da.DisplayAllResponderHistory(),
				new String[] { DataAccess.H_SNUMBER, DataAccess.H_MSG,
						DataAccess.H_RMSG, DataAccess.H_DATE,
						DataAccess.H_TIME, DataAccess.H_ID }, new int[] {
						R.id.tvNo, R.id.tvMessage, R.id.tvResponse,
						R.id.tvDate, R.id.tvTime, R.id.tvHID }));
		da.CloseDataAccess();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final int iHID = Integer.parseInt(((TextView) v
				.findViewById(R.id.tvHID)).getText().toString());
		Builder builder = new Builder(context);
		builder.setTitle("Delete History")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DataAccess da = new DataAccess(context);
								da.DeleteResponderHistory(new ResponderHistory(
										iHID, null, null, null, null, null));
								LoadList();
								da.CloseDataAccess();
							}
						}).setNegativeButton("No", null).show();
	}
}
