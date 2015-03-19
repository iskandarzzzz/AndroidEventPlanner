package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import android.util.Log;

public class EventContainer {
	
	private ArrayList<AbstractEvent> events = new ArrayList<AbstractEvent>();
	private static EventContainer instance = new EventContainer();

	public EventContainer(){
	}
	
	
	public ArrayList<AbstractEvent> getEvents() {
		return events;
	}
	
	public AbstractEvent getEvent(String UID) throws Exception{
		for(AbstractEvent e : events){
			Log.v("EventContainer", "Comparing " + e.getId() + " to " + UID + " the difference is " + UID.compareTo(e.getId()));
			if(UID.compareTo(e.getId())==0){
				Log.v("EventContainer", "MATCH!");
				return e;
			}
		}
		throw new Exception("Could not find the specified event");
	}

	public void addEvent(AbstractEvent e) {
		events.add(e);
		Collections.sort(events);
	}	
	
	public void removeEvent(String UID) throws Exception{
		for(AbstractEvent e : events){
			if(UID.equals(e.getId())){
				events.remove(e);
				return;
			}
		}
		throw new Exception("Could not find the specified event to remove");
	}
	
	public ArrayList<AbstractEvent> getEventsOnDay(int day, int month, int year){
		ArrayList<AbstractEvent> eventsOnDay = new ArrayList<AbstractEvent>();
		for(AbstractEvent e : events){
			GregorianCalendar eventDate = e.getDate();
			if(year==eventDate.get(Calendar.YEAR))
				if(month==eventDate.get(Calendar.MONTH))
					if(day==eventDate.get(Calendar.DAY_OF_MONTH))
						eventsOnDay.add(e);
		}
		return eventsOnDay;
	}
	
	public void sort(){
		Collections.sort(events);
	}
	
	public static EventContainer getInstance(){
		return instance;
	}

}
