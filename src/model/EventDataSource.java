package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventDataSource {
	private static final String TAG = "EventDataSource";
	private SQLiteDatabase database;
	private DatabaseHelper helper;
	
	public EventDataSource(Context context){
		helper = new DatabaseHelper(context);
	}
	
	public void openDB() throws SQLException {
		database = helper.getWritableDatabase();
	}
	
	public void closeDB(){
		helper.close();
	}
	
	public void createEvent(Event e){
		Log.v(TAG, "Creating event");
		ContentValues values = new ContentValues();
		values.put(helper.COLUMN_ID, e.getId());
		values.put(helper.COLUMN_TITLE, e.getTitle());
	    values.put(helper.COLUMN_DATE, e.getDate().getTimeInMillis());
	    values.put(helper.COLUMN_VENUE, e.getVenue());
	    values.put(helper.COLUMN_LOC, e.getLocation());
	    values.put(helper.COLUMN_NOTE, e.getNote());
	    StringBuilder sb = new StringBuilder();
	    for(String email : e.getAttendees()){
	    	sb.append(email);
	    	sb.append(',');
	    }
	    Log.v(TAG, "Contacts are " + sb.toString());
	    values.put(helper.COLUMN_ATTENDEES, sb.toString());
	    database.insert(helper.TABLE_EVENTS, null, values);
	}
	
	public void updateEvent(Event e){
		//add values for new event
		ContentValues values = new ContentValues();
		values.put(helper.COLUMN_ID, e.getId());
		values.put(helper.COLUMN_TITLE, e.getTitle());
	    values.put(helper.COLUMN_DATE, e.getDate().getTimeInMillis());
	    values.put(helper.COLUMN_VENUE, e.getVenue());
	    values.put(helper.COLUMN_LOC, e.getLocation());
	    values.put(helper.COLUMN_NOTE, e.getNote());
	    StringBuilder sb = new StringBuilder();
	    for(String email : e.getAttendees()){
	    	sb.append(email);
	    	sb.append(',');
	    }
	    values.put(helper.COLUMN_ATTENDEES, sb.toString());
		database.update(helper.TABLE_EVENTS, values, helper.COLUMN_ID + " = '" + e.getId() + "'", null);
		
	}
	
	public void deleteEvent(String id){
		database.delete(helper.TABLE_EVENTS, helper.COLUMN_ID + " = " + '"' + id + '"', null);
	}
	
	public void loadAllEventsIntoMemory(){
		Log.v(TAG, "Begun adding events in database to memory");
		Cursor c = database.query(helper.TABLE_EVENTS, null, null, null, null, null, null);
		c.moveToFirst();
		while(!c.isAfterLast()){
			EventContainer.getInstance().addEvent(createEventFromCursor(c));
			c.moveToNext();
		}
		c.close();
		Log.v(TAG, "Added events in database to memory");
	}
	
	public Event createEventFromCursor(Cursor c){
		Log.v(TAG, "Fetching event from database");
		String id = c.getString(c.getColumnIndex(helper.COLUMN_ID));
		String title = c.getString(c.getColumnIndex(helper.COLUMN_TITLE));
		GregorianCalendar date = new GregorianCalendar();
		date.setTimeInMillis(c.getLong(c.getColumnIndex(helper.COLUMN_DATE)));
		String venue = c.getString(c.getColumnIndex(helper.COLUMN_VENUE));
		String location = c.getString(c.getColumnIndex(helper.COLUMN_LOC));
		String note = c.getString(c.getColumnIndex(helper.COLUMN_NOTE));
		String tokenizedAttendees = c.getString(c.getColumnIndex(helper.COLUMN_ATTENDEES));
		ArrayList<String> attendees = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(tokenizedAttendees, ",");
		while(st.hasMoreElements()){
			attendees.add(st.nextToken());
		}
		Log.v(TAG, "Event " + title + " has been fetched from database");
		return new Event(id, title, date, venue, location, note, attendees);
	}
	
}
