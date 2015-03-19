package model;

import java.text.DateFormat;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.UUID;

public abstract class AbstractEvent implements Comparable<AbstractEvent> {
	private String id;
	private String title;
	private GregorianCalendar date;
	private boolean notified;
	
	public AbstractEvent(String id, String title, GregorianCalendar date){
		this.id = id;
		this.title = title;
		this.date = date;
		this.notified = false;
	}

	public AbstractEvent(String title, GregorianCalendar date){
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.date = date;
	}
	
	public int compareTo(AbstractEvent another) {
		return this.getDate().compareTo(another.getDate());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
		Collections.sort(EventContainer.getInstance().getEvents());
	}
	
	public boolean eventOccured(){
		GregorianCalendar currentDateTime = new GregorianCalendar();
		if(this.date.getTimeInMillis() < currentDateTime.getTimeInMillis()){
			return true;
		}
		return false;
	}
	
	public String niceTime(){
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date.getTime());
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}
	
	

}
