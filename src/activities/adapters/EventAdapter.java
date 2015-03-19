package activities.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.assignment1.R;

import listeners.DelEventListener;
import listeners.EditEventListener;
import listeners.ViewEventListener;
import model.AbstractEvent;
import model.Event;
import model.EventContainer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter  {
	private Context context;
	private ArrayList<AbstractEvent> events;
	private int day;
	private int month;
	private int year;
	private boolean getAll;
	
	public EventAdapter(Context c, int d, int m, int y){
		this.context = c;
		day = d;
		month = m;
		year = y;
		if(y == -1){
			getAll = true;
			events = EventContainer.getInstance().getEvents();
		}
		else{
			getAll = false;
			events = EventContainer.getInstance().getEventsOnDay(day,month,year);
		}
	}


	@Override
	public int getCount() {
		return events.size();
	}
	
	public int getViewTypeCount(){
		if(events.size()==0){
			return 1;
		}else{
			return events.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		AbstractEvent e = (AbstractEvent) getItem(position);
		if(v==null){
	        //inflate the correct grid item depending on event type
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(e instanceof Event)
				v = inf.inflate(R.layout.event_item, null);
		}
		//add event name to event_item
		TextView eventName = (TextView) v.findViewById(R.id.eventTitle);
		eventName.setText(e.getTitle());
		//set the date and time
		TextView dateView = (TextView) v.findViewById(R.id.dateView);
		dateView.setText(calendarToHumanReadable(e.getDate()));
		//get number of attendees
		if(e instanceof Event){
			TextView attendees = (TextView) v.findViewById(R.id.eventAttendees);
			attendees.setText(Integer.toString(((Event) e).getAttendees().size()));
		}
		//get buttons and add listeners
		Button edit = (Button) v.findViewById(R.id.eventEditBtn);
		edit.setOnClickListener(new EditEventListener(context, e.getId()));
		Button del = (Button) v.findViewById(R.id.eventDelBtn);
		del.setOnClickListener(new DelEventListener(context, e.getTitle(), e.getId()));
		v.setOnLongClickListener(new ViewEventListener(context, e.getId()));
		return v;
	}


	@Override
	public boolean isEmpty() {
		if(events.size()==0)
			return true;
		return false;
	}
	
	public void getNewData(){
		if(getAll){
			events = EventContainer.getInstance().getEvents();
			return;
		}
		events = EventContainer.getInstance().getEventsOnDay(day,month,year);
	}

	
	public String calendarToHumanReadable(GregorianCalendar c){
		StringBuilder sb = new StringBuilder();
		sb.append(c.get(Calendar.DAY_OF_MONTH));
		sb.append("/");
		sb.append(c.get(Calendar.MONTH)+1);
		sb.append("/");
		sb.append(c.get(Calendar.YEAR));
		sb.append(" ");
		sb.append(c.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		if(c.get(Calendar.MINUTE)<10)
			sb.append(0);
		sb.append(c.get(Calendar.MINUTE));
		return sb.toString();
	}
}
