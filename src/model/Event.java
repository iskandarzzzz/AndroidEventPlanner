package model;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.util.Log;

public class Event extends AbstractEvent {
	private String venue;
	private String location;
	private String note;
	private ArrayList<String> attendees;


	public Event(String title, GregorianCalendar date, String venue,
			String location, String note, ArrayList<String> attendees) {
		super(title, date);
		this.venue = venue;
		this.location = location;
		this.note = note;
		this.attendees = attendees;
	}

	public Event(String id, String title, GregorianCalendar date, String venue,
			String location, String note, ArrayList<String> attendees) {
		super(id, title, date);
		this.venue = venue;
		this.location = location;
		this.note = note;
		this.attendees = attendees;
	}

	public ArrayList<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(ArrayList<String> attend){
		attendees = attend;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	

	public boolean parseLocation(double[] results){
		if(results.length!=2){
			Log.v(this.getTitle(), "The size of the results array given was invalid");
			return false;
		}
		String[] tokens = this.location.split(",");
		if(tokens.length!=2){
			Log.v(this.getTitle(), "The amount of tokens recieved was invalid");
			return false;
		}
		for(int i =0; i < 2; i++){
			try{
				results[i] = Double.parseDouble(tokens[i]);
			}catch(NumberFormatException e){
				Log.v(this.getTitle(), "The location given is invalid");
				return false;
			}catch(Exception e){
				Log.v(this.getTitle(), "Some unknown error occured");
				return false;
			}
		}
		return true;
	}

}
