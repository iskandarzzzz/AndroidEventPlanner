package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHelper";
	private static final String DATABASE_NAME = "WDTassignment2.db";
	//tables
	final String TABLE_EVENTS = "events";
	final String TABLE_CONTACTS = "contacts";
	//columns
	final String COLUMN_ID = "id";
	final String COLUMN_TITLE = "title";
	final String COLUMN_DATE = "date";
	final String COLUMN_VENUE = "venue";
	final String COLUMN_LOC = "location";
	final String COLUMN_NOTE = "note";
	final String COLUMN_ATTENDEES = "attendees";
	
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, 2);
	}
	

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.v(TAG, "Creating database tables");
		database.execSQL("CREATE TABLE " + TABLE_EVENTS + "(" + COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_TITLE 
				+" TEXT, " + COLUMN_DATE + " INTEGER, " + COLUMN_VENUE + " TEXT, " + COLUMN_LOC + " TEXT, " 
				+ COLUMN_NOTE + " TEXT, " + COLUMN_ATTENDEES + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int v1, int v2) {
		Log.v(TAG, "Upgrading database, dropping tables and calling oncreate again");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		onCreate(database);	
	}
}
