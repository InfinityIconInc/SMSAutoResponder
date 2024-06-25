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

public class ResponderListOptionActivity extends Activity implements
  OnItemClickListener {
  Context context;
  SimpleCursorAdapter adapter;
  ListView lvRL;
  String[] menuoptions = {
			"Options",			
	};

   @Override
   protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_responderlist_option);
   context = ResponderListOptionActivity.this;
  lvRL = (ListView) findViewById(R.id.lvRL);

   LoadListView ( );	
  lvRL.setOnItemClickListener(this);
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
		da.CloseDataAccess();
		registerForContextMenu(lvRL);
	

}
  public boolean onContextItemSelected(MenuItem item) {
	   
	    if (item.getTitle() == "Remove") {
	    	{
	    	final TextView tvNumber = ((TextView) findViewById(android.R.id.text2));
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
		}}
	    
	    else if (item.getTitle() == "Delete ALL") {
			
			{
		
	    Builder builder = new Builder(context);
		
		builder.setTitle("Delete Responder Contact")
				.setMessage("Do you want to del all Responder Contact?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								
		                     DataAccess da = new DataAccess(context);
		                     Toast.makeText(ResponderListOptionActivity.this,
				              da.RemoveAllResponderList() + " Deleted",
			                 	Toast.LENGTH_LONG).show();
		                        da.CloseDataAccess();
		                         LoadListView();
                              	}
							
				               }).setNegativeButton("No", null).show();
	                       }
			             }

                 else {
		                {
			           Builder builder = new Builder(context);
			
			            builder.setTitle("Delete Responder Contact")
				    	.setMessage("Do you want to del all Responder Contact?")
					   .setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									
			                     DataAccess da = new DataAccess(context);
			                     Toast.makeText(ResponderListOptionActivity.this,
					              da.RemoveAllResponderList() + " Deleted",
				                 	Toast.LENGTH_LONG).show();
			                        da.CloseDataAccess();
			                         LoadListView();
	                              	}
								
					               }).setNegativeButton("No", null).show();
		                        }
		
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
	              		  if (v.getId()== R.id.lvSavedSituations) {
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
