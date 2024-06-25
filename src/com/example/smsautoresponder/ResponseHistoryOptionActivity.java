package com.example.smsautoresponder;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResponseHistoryOptionActivity extends Activity implements OnItemClickListener {
	Context context = ResponseHistoryOptionActivity.this;
	SimpleCursorAdapter adapter;
	String[] menuoptions = {
			"Options",			
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_responsehistory);
		LoadList();

     }
	public void LoadList() {
		DataAccess da = new DataAccess(context);
		ListView lv = (ListView) findViewById(R.id.lvsituations);
		lv.setOnItemClickListener(this);
		
		Cursor c = da.GetAllResponseHistory();
		
		adapter =new SimpleCursorAdapter(context,
				R.layout.row_response_history, da.DisplayAllResponderHistory(),
				new String[] { DataAccess.H_SNUMBER, DataAccess.H_MSG,
						DataAccess.H_RMSG, DataAccess.H_DATE,
						DataAccess.H_TIME, DataAccess.H_ID }, new int[] {
						R.id.tvNo, R.id.tvMessage, R.id.tvResponse,
						R.id.tvDate, R.id.tvTime, R.id.tvHID });
		lv.setAdapter(adapter);
		da.CloseDataAccess();
		registerForContextMenu(lv);
		
	}
	@Override
	  public boolean onContextItemSelected(MenuItem item) {
		
	    
	    if (item.getTitle() == "Delete All") {
	    	Builder builder = new Builder(context);
			
			builder.setTitle("Delete Response History")
					.setMessage("Do you want to del all Response History?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
			DataAccess da = new DataAccess(context);
			Toast.makeText(ResponseHistoryOptionActivity.this,
					da.DeleteAllResponderHistory() + " Deleted",
					Toast.LENGTH_LONG).show();
			da.CloseDataAccess();
			LoadList();
		}
								
					}).setNegativeButton("No", null).show();
		}
	    else if (item.getTitle() == "Delete") {
	    	
	    	final int iHID = Integer.parseInt(((TextView)findViewById(R.id.tvHID)).getText().toString());
			Builder builder = new Builder(context);
			builder.setTitle("Delete History")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

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
	    else {
			{
				final int iHID = Integer.parseInt(((TextView)findViewById(R.id.tvHID)).getText().toString());
				Builder builder = new Builder(context);
				builder.setTitle("Delete History")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

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
			Toast.makeText(ResponseHistoryOptionActivity.this, "Delete", Toast.LENGTH_SHORT).show();
	    
				return false;
	    }
	
		    return true;
		  }
			
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Context Menu");
		
		menu.add(0, v.getId(), 0, "Delete ALL");
		menu.add(0, v.getId(), 0, "Delete");
	  if (v.getId()==R.id.lvsituations) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle(menuoptions[info.position]);
	    String[] menuItems = getResources().getStringArray(R.array.management);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	 
	}
	    	
	    
	    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
