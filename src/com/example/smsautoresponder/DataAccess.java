package com.example.smsautoresponder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataAccess {
	public static final String TAG = "DataAccess";

	public static final String DB_NAME = "smsautoresponder";
	public static final int DB_VERSION = 1;
	// Responder
	public static final String T_RESPONDER = "responder";
	public static final String R_ID = "_id";
	public static final String R_NUMBER = "rnumber";
	public static final String R_CONTACT = "rcontact";
	// Situation
	public static final String T_SITUATION = "situation";
	public static final String S_ID = "_id";
	public static final String S_MSG = "s_msg";
	public static final String S_NAME = "s_name";
	public static final String S_ACTIVE = "s_active";
	// Options
	public static final String T_OPTIONS = "options";
	public static final String O_ID = "_id";
	public static final String O_VALUE = "o_value";
	// Contacts
	public static final String T_CONTACTS = "contacts";
	public static final String C_ID = "_id";
	public static final String C_NAME = "c_name";
	public static final String C_NUMBER = "c_number";

	// ResponderHistory
	public static final String T_HISTORY = "history";
	public static final String H_ID = "_id";
	public static final String H_DATE = "h_date";
	public static final String H_TIME = "h_time";
	public static final String H_RMSG = "h_rmessage"; // responded sms
	public static final String H_MSG = "h_message"; // sender sms
	public static final String H_SNUMBER = "h_snumber";// sender number

	Context context;
	DBHelper dbhelper;
	SQLiteDatabase db;

	public DataAccess(Context context) {
		this.context = context;
		dbhelper = new DBHelper();
	}

	public Cursor DisplayAllResponderHistory() {
		db = dbhelper.getReadableDatabase();
		return db.query(T_HISTORY, null, null, null, null, null, null);
	}

	public int GetMaxResponderHistoryID() {
		int maxID = 0;
		db = dbhelper.getReadableDatabase();
		Cursor c = db.query(T_HISTORY, new String[] { "MAX(" + H_ID + ")" },
				null, null, null, null, null);
		c.moveToFirst();
		if (c.getCount() > 0) {
			maxID = c.getInt(c.getColumnIndex("MAX(" + H_ID + ")"));
		}
		maxID = maxID + 1;
		return maxID;
	}

	public long InsertResponderHistory(ResponderHistory rh) {
		db = dbhelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(H_ID, this.GetMaxResponderHistoryID());
		cv.put(H_DATE, rh.getStrResponderHistoryDate());
		cv.put(H_TIME, rh.getStrResponderHistoryTime());
		cv.put(H_SNUMBER, rh.getStrResponderHistoryNumber());
		cv.put(H_MSG, rh.getStrResponderHistoryMessage());
		cv.put(H_RMSG, rh.getStrResponderHistoryRespondedMessage());

		return db.insert(T_HISTORY, null, cv);
	}

	public int DeleteAllResponderHistory() {
		db = dbhelper.getWritableDatabase();
		return db.delete(T_HISTORY, "1", null);
	}
	///////
	public Cursor GetAllResponseHistory() {
		db = dbhelper.getReadableDatabase();
		return db.query(T_HISTORY, null, null, null, null, null, null);
	}
	
	public int DeleteResponderHistory ( ResponderHistory rh ) {
		db = dbhelper.getWritableDatabase();
		return db.delete(T_HISTORY, H_ID + " = " + rh.getiResponderHistoryID(), null);
	}
	
	public Cursor DisplayAllContacts() {
		db = dbhelper.getReadableDatabase();
		return db.query(T_CONTACTS, null, null, null, null, null, null);
	}

	public int GetMaxContactID() {
		db = dbhelper.getReadableDatabase();
		Cursor c = db.query(T_CONTACTS, new String[] { "MAX(" + C_ID + ")" },
				null, null, null, null, null);
		c.moveToFirst();
		int i = 0;
		if (c.getCount() > 0) {
			i = c.getInt(c.getColumnIndex("MAX(" + C_ID + ")"));
		}
		i = i + 1;
		return i++;
	}

	public int GetRespondStatus() {
		db = dbhelper.getReadableDatabase();
		int iStatus;//0 respond list, 1 respond everyone, 2 respond contacts
		Cursor c = db.query(T_OPTIONS, new String[] { O_VALUE }, O_ID + " = "
				+ 1, null, null, null, null);
		c.moveToFirst();
		if (c.getInt(c.getColumnIndex(O_VALUE)) == 0)
			iStatus = 0;//Respnod List only
		else if (c.getInt(c.getColumnIndex(O_VALUE)) == 1)
			iStatus = 1;//Respond Everyone
		else
			iStatus = 2;//Respond Contacts Only

		return iStatus;
	}

	public int SetRespondStatus(int status) {
		db = dbhelper.getWritableDatabase();
		int i = 0;
		if (status==1) {//Respond Everyone
			ContentValues cvtrue = new ContentValues();
			cvtrue.put(O_ID, 1);
			cvtrue.put(O_VALUE, 1);
			i = db.update(T_OPTIONS, cvtrue, O_ID + " = " + 1, null);
		} else if ( status == 2) {//Contacts Only
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 2);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
			
		} else {//Respond ResponderList
			ContentValues cvfalse = new ContentValues();
			cvfalse.put(O_ID, 1);
			cvfalse.put(O_VALUE, 0);
			i = db.update(T_OPTIONS, cvfalse, O_ID + " = " + 1, null);
		}
		return i;
	}
	
	public boolean CheckExistingContact(String strContact) {
		db = dbhelper.getReadableDatabase();
		Log.d(TAG, "Inside CheckExistingContact");

		boolean rExists = false;
		Cursor c = db.rawQuery("SELECT * FROM " + T_CONTACTS + " WHERE "
				+ C_NUMBER + " LIKE '%" + strContact + "%'",
				null);
		if (c.getCount() == 0) {
			Log.d(TAG, "No Match Found " + c.getCount());
			rExists = false;
		} else {
			Log.d(TAG, "Match Found " + c.getCount());
			rExists = true;
		}
		return rExists;
	}

	public long InsertContact(Contact contact) {
		db = dbhelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(C_ID, contact.getResponder_id());
		cv.put(C_NAME, contact.getResponder_contact());
		cv.put(C_NUMBER, contact.getResponder_number());

		return db.insert(T_CONTACTS, null, cv);
	}

	public void ZapContacts() {
		db = dbhelper.getWritableDatabase();
		db.execSQL(String.format("DELETE FROM %s", T_CONTACTS));
	}

	public Cursor DisplayAllResponders() {
		db = dbhelper.getReadableDatabase();
		return db.query(T_RESPONDER, null, null, null, null, null, null);
	}

	public int GetMaxResponderID() {
		db = dbhelper.getReadableDatabase();
		int rid = 0;
		Cursor c = db.rawQuery(
				String.format("SELECT MAX(_id) FROM %s", T_RESPONDER), null);
		c.moveToFirst();
		Log.d(TAG, "COUT: " + c.getCount());
		if (c.getCount() > 0) {
			if (c.getString(c.getColumnIndex("MAX(_id)")) == null)
				rid = 0;
			else
				rid = Integer
						.parseInt(c.getString(c.getColumnIndex("MAX(_id)")));
		} else
			rid = 0;

		rid = rid + 1;
		return rid;
	}

	public long AddResponderList(Contact Rlist) {
		long l;
		db = dbhelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(R_ID, Rlist.getResponder_id());
		cv.put(R_NUMBER, Rlist.getResponder_number());
		cv.put(R_CONTACT, Rlist.getResponder_contact());

		l = db.insert(T_RESPONDER, null, cv);
		return l;
	}

	public long InsertResponder(Contact contact) {
		db = dbhelper.getReadableDatabase();
		long i = -1;
		if (!CheckExistingResponderContact(contact)) {
			Log.d(TAG, "Contact Not Exists in List");
			int iRID = GetMaxResponderID();
			ContentValues cv = new ContentValues();
			cv.put(R_ID, iRID);
			cv.put(R_CONTACT, contact.getResponder_contact());
			cv.put(R_NUMBER, contact.getResponder_number());
			i = db.insert(T_RESPONDER, null, cv);
			Log.d(TAG, "Contact Added");
		}
		return i;
	}

	public boolean CheckExistingResponderContact(Contact contact) {
		db = dbhelper.getReadableDatabase();
		Log.d(TAG, "Inside CheckExistingResponderContact");

		boolean rExists = false;
		Cursor c = db.rawQuery("SELECT * FROM " + T_RESPONDER + " WHERE "
				+ R_NUMBER + " LIKE '%" + contact.getResponder_number() + "%'",
				null);
		if (c.getCount() == 0) {
			Log.d(TAG, "No Match Found " + c.getCount());
			rExists = false;
		} else {
			Log.d(TAG, "Match Found " + c.getCount());
			rExists = true;
		}
		return rExists;
	}
	
	public String GetContactByNumber (String strNo) {
		db = dbhelper.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + T_CONTACTS + " WHERE "
				+ C_NUMBER + " LIKE '%" + strNo + "%'",
				null);
		c.moveToFirst();
		return c.getString(c.getColumnIndex(C_NAME)).toString();
	}


	public int EditResponderList(Contact responderlist) {
		db = dbhelper.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(R_ID, responderlist.getResponder_id());
		cv.put(R_NUMBER, responderlist.getResponder_contact());
		cv.put(R_CONTACT, responderlist.getResponder_number());

		return db.update(T_RESPONDER, cv, R_ID, new String[] { Integer
				.toString(responderlist.getResponder_id()) });
	}

	public int RemoveAllResponderList() {
		db = dbhelper.getWritableDatabase();
		//String strSQL = String.format("DELETE FROM %s", T_RESPONDER);
		//return  db.execSQL(strSQL);
		return db.delete(T_RESPONDER, "1", null);
	}

	public int RemoveResponderListByNumber(Contact contact) {
		db = dbhelper.getWritableDatabase();
		return db.delete(T_RESPONDER,
				R_NUMBER + " = '" + contact.getResponder_number() + "'", null);
		
	}

	public void RemoveResponderListByID(Contact responderlist) {
		db = dbhelper.getWritableDatabase();
		String strSQL = String.format("DELETE FROM %s WHERE %s = %s",
				T_RESPONDER, R_ID, responderlist.getResponder_id());
		db.execSQL(strSQL);
	}

	public long insertSituation(Situation sit) {
		db = dbhelper.getWritableDatabase();
		long sid;

		ContentValues sval = new ContentValues();

		sval.put(S_ID, (sit.getSituation_id()));
		sval.put(S_MSG, (sit.getSituation_msg()));
		sval.put(S_NAME, (sit.getSituation_name()));

		sid = db.insert(T_SITUATION, null, sval);
		db.close();
		return sid;
	}

	public void DeleteSituation(int iSID) {
		db = dbhelper.getWritableDatabase();

		// db.delete(TABLE_SmsAr, new String [] { SMS_ID} , " = " + id );
		db.execSQL(String.format("DELETE FROM %s WHERE %s = %s", T_SITUATION,
				S_ID, iSID));
	}

	public void DeleteAllSituations() {
		db = dbhelper.getWritableDatabase();
		db.execSQL(String.format("DELETE FROM %S", T_SITUATION));

	}
	public int ZapAllSituation ( ) {
		db = dbhelper.getWritableDatabase();
		return db.delete(T_SITUATION, "1", null);
	}


	public Cursor DisplayAllSituations() {
		db = dbhelper.getReadableDatabase();
		Cursor c;
		c = db.query(T_SITUATION, null, null, null, null, null, null);

		return c;
	}

	public Situation GetActiveSituation() {
		db = dbhelper.getReadableDatabase();
		Situation s = new Situation();

		Cursor c = db.query(T_SITUATION, null, S_ACTIVE + "= 1", null, null,
				null, null);
		c.moveToFirst();

		if (c.getCount() > 0) {
			s.setSituation_id(c.getInt(c.getColumnIndex(S_ID)));
			s.setSituation_name(c.getString(c.getColumnIndex(S_NAME)));
			s.setSituation_msg(c.getString(c.getColumnIndex(S_MSG)));
			s.setiActive(c.getInt(c.getColumnIndex(S_ACTIVE)));
		}
		return s;
	}

	public Situation GetSituation( int iSID) {
		db = dbhelper.getReadableDatabase();
		Situation s = new Situation();

		Cursor c = db.query(T_SITUATION, null, S_ID + "= " + iSID, null, null,
				null, null);
		c.moveToFirst();

		if (c.getCount() > 0) {
			s.setSituation_id(c.getInt(c.getColumnIndex(S_ID)));
			s.setSituation_name(c.getString(c.getColumnIndex(S_NAME)));
			s.setSituation_msg(c.getString(c.getColumnIndex(S_MSG)));
			s.setiActive(c.getInt(c.getColumnIndex(S_ACTIVE)));
		}
		return s;
	}

	public int EditSituation(Situation sit) {
		db = dbhelper.getWritableDatabase();
		Log.d(TAG, "Inside EditSITUATION");

		ContentValues cv = new ContentValues();
		// cv.put(S_ID, sit.getSituation_id());
		cv.put(S_MSG, sit.getSituation_msg());
		cv.put(S_NAME, sit.getSituation_name());
		cv.put(S_ACTIVE, sit.getiActive());
		// String strSQL = String.format(
		// "UPDATE %s SET %s = %s, %s = %s WHERE %s = %s", T_SITUATION,
		// S_NAME, sit.getSituation_name(), S_MSG, sit.getSituation_msg(),
		// S_ID, sit.getSituation_id());
		// db.execSQL(String.format("UPDATE %s SET %s = '%s', %s = '%s' WHERE %s = %s",
		// T_SITUATION, S_NAME, sit.getSituation_name(), S_MSG,
		// sit.getSituation_msg(), S_ID, sit.getSituation_id()));
		return db.update(T_SITUATION, cv, S_ID + " = " + sit.getSituation_id(),
				null);
	}

	public void ActivateSituation(int iSituationID) {
		db = dbhelper.getWritableDatabase();
		Log.d(TAG, "Before De-Activating All Situations");
		db.execSQL(String.format("UPDATE %s SET %s = %s", T_SITUATION,
				S_ACTIVE, 0));
		db.execSQL(String.format("UPDATE %s SET %s = %s WHERE %s = %s",
				T_SITUATION, S_ACTIVE, 1, S_ID, iSituationID));
		Log.d(TAG, "Activated Selected Situation" + iSituationID);
	}

	public int GetMaxSituationID() {
		db = dbhelper.getReadableDatabase();
		int sid = 0;
		Cursor c = db.rawQuery(
				String.format("SELECT MAX(_id) FROM %s", T_SITUATION), null);
		c.moveToFirst();
		Log.d(TAG, "COUT: " + c.getCount());
		if (c.getCount() > 0) {
			if (c.getString(c.getColumnIndex("MAX(_id)")) == null)
				sid = 0;
			else
				sid = Integer
						.parseInt(c.getString(c.getColumnIndex("MAX(_id)")));
		} else
			sid = 0;

		sid = sid + 1;
		return sid;
	}

	public void CloseDataAccess() {
		dbhelper.close();
	}

	class DBHelper extends SQLiteOpenHelper {

		public DBHelper() {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(String.format(
					"CREATE TABLE %s(%s int PRIMARY KEY, %s text, %s text)",
					T_RESPONDER, R_ID, R_NUMBER, R_CONTACT));

			db.execSQL(String
					.format("CREATE TABLE %s(%s int PRIMARY KEY, %s text, %s text, %s int)",
							T_SITUATION, S_ID, S_MSG, S_NAME, S_ACTIVE));

			db.execSQL(String
					.format("CREATE TABLE %s ( %s int PRIMARY KEY,  %s text, %s text )",
							T_CONTACTS, C_ID, C_NAME, C_NUMBER));

			db.execSQL(String
					.format("CREATE TABLE %s ( %s int PRIMARY KEY,  %s text, %s text, %s text, %s text, %s text )",
							T_HISTORY, H_ID, H_DATE, H_TIME, H_MSG, H_RMSG,
							H_SNUMBER));

			db.execSQL(String.format(
					"CREATE TABLE %s ( %s int PRIMARY KEY, %s int  )",
					T_OPTIONS, O_ID, O_VALUE));

			db.execSQL(String.format(
					"INSERT INTO %s ( %s,  %s ) VALUES ( 1, 0 )", T_OPTIONS,
					O_ID, O_VALUE));// ID=1 => Respond Everyone, ID=2 => Respond
									// Contacts
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(String.format("DROP TABLE %S", T_RESPONDER));
			db.execSQL(String.format("DROP TABLE %S", T_SITUATION));
			db.execSQL(String.format("DROP TABLE %S", T_OPTIONS));
			db.execSQL(String.format("DROP TABLE %S", T_CONTACTS));
			db.execSQL(String.format("DROP TABLE %S", T_HISTORY));
			this.onCreate(db);
		}
	}
}
