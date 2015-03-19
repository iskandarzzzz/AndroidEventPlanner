package activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import listeners.ContactsButtonListener;
import listeners.LocationButtonListener;
import model.Event;
import model.EventContainer;
import com.example.assignment1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import async.AddOrEditEventAsync;
import async.AsyncAddTaskParams;

public class AddEventActivity extends Activity {
	//this activity should only be called if you want to make an 'Event'
	//will not work with other types of AbstractEvent
	private static final String TAG = "AddEventActivity";
	private TextView header;
	private String id;
	private ArrayList<String> emails = new ArrayList<String>();
	private EditText title;
	private DatePicker date;
	private TimePicker time;
	private EditText venue;
	private TextView location;
	private EditText note;
	private TextView attendees;
	private Button locationButton;
	private Button contactsButton;
	private Button createButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//create layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event_view);
		getViews();
		Bundle extras = getIntent().getExtras();
		// if we are adding event on a specific day
		if(getIntent().hasExtra("day") && getIntent().hasExtra("month")
				&& getIntent().hasExtra("year")){
			int day = extras.getInt("day");
			int month = extras.getInt("month");
			int year = extras.getInt("year");
			date.init(year, month, day, null);
			date.setEnabled(false);
		}
		//if we are editing a specific id
		if(getIntent().hasExtra("id")){
			id = extras.getString("id");
			Log.v(TAG, "Recieved id " + id);
			try {
				Event e = (Event) EventContainer.getInstance().getEvent(id);
				populateFields(e);
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				finish();
			}
		}
		//if we are viewing and not editing
		if(getIntent().hasExtra("view")){
			viewMode();
		}else{
			locationButton.setOnClickListener(new LocationButtonListener(this));
			contactsButton.setOnClickListener(new ContactsButtonListener(this));
			createButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if(checkAllFieldsCorrect()==true){
						if(id!=null){
							finishWithEdit();
							finish();
						}else{
							createNewEvent();
							finish();
						}
					}
				}
			});
		}
		Log.v(TAG, "layout created");
	}

	private void getViews(){
		header = (TextView)this.findViewById(R.id.addEventHead);
		title = (EditText)this.findViewById(R.id.titleEdit);
		date = ((DatePicker) this.findViewById(R.id.dateEdit));
		time = (TimePicker)this.findViewById(R.id.timeEdit);
		venue = (EditText)this.findViewById(R.id.venueEdit);
		location = (TextView)this.findViewById(R.id.latLongText);
		note= (EditText)this.findViewById(R.id.noteEdit);
		attendees = (TextView)this.findViewById(R.id.emailsSelected);
		locationButton = (Button) this.findViewById(R.id.locationEdit);
		contactsButton = (Button) this.findViewById(R.id.attendeesEdit);
		createButton = (Button) this.findViewById(R.id.addEvent);
	}

	private void populateFields(Event e) {
		//title
		title.setText(e.getTitle());
		//date
		date.init(e.getDate().get(Calendar.YEAR), e.getDate().get(Calendar.MONTH), 
				e.getDate().get(Calendar.DAY_OF_MONTH), null);
		//time
		time.setCurrentHour(e.getDate().get(Calendar.HOUR_OF_DAY));
		time.setCurrentMinute(e.getDate().get(Calendar.MINUTE));
		//venue
		venue.setText(e.getVenue());
		//lat/long
		location.setText(e.getLocation());
		//note
		note.setText(e.getNote());
		//attendees
		emails = e.getAttendees();
		setAttendees();		
	}

	public boolean checkAllFieldsCorrect(){
		if(title.getText().toString().length()==0){
			Toast.makeText(this, "Enter a title", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(venue.getText().toString().length()==0){
			Toast.makeText(this, "Enter a venue", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(location.getText().toString().length()==0){
			Toast.makeText(this, "Please use google maps to select a location", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(emails.size()==0){
			Toast.makeText(this, "You need at least 1 attendee", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void setAttendees(){
		StringBuilder emailString = new StringBuilder();
		for(String s : emails)
			emailString.append(s+"\n");
		attendees.setText(emailString.toString());
	}

	public void finishWithEdit(){
		Event e = null;
		try {
			e = (Event) EventContainer.getInstance().getEvent(id);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(e != null){
			e.setTitle(title.getText().toString());
			e.setDate(getCalendar());
			e.setVenue(venue.getText().toString());
			e.setLocation(location.getText().toString());
			e.setNote(note.getText().toString());
			e.setAttendees(emails);
			e.setNotified(false);
			AsyncAddTaskParams params = new AsyncAddTaskParams(e, this, true);
			//start async task
			new AddOrEditEventAsync().execute(params);
		}
	}

	public void createNewEvent(){
		//create the event
		Event e = new Event(title.getText().toString(),
				getCalendar(),venue.getText().toString(), location.getText().toString(),
				note.getText().toString(), emails);
		//set paramaters for async event
		AsyncAddTaskParams params = new AsyncAddTaskParams(e, this, false);
		EventContainer.getInstance().addEvent(e);
		//start async task
		new AddOrEditEventAsync().execute(params);
	}

	public GregorianCalendar getCalendar(){
		return new GregorianCalendar(date.getYear(), date.getMonth(),date.getDayOfMonth(), 
				time.getCurrentHour(), time.getCurrentMinute());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				((TextView)this.findViewById(R.id.latLongText)).setText(data.getStringExtra("loc"));
			}else{
				Toast.makeText(this, "Google Play unavaliable or not updated.", Toast.LENGTH_LONG).show();
			}
		}
		if(requestCode == 2){
			if(resultCode == RESULT_OK){
				emails = data.getStringArrayListExtra("emails");
				setAttendees();
			}
		}
	}

	public void viewMode(){
		header.setText("View Event");
		title.setEnabled(false);
		date.setEnabled(false);
		time.setEnabled(false);
		venue.setEnabled(false);
		note.setEnabled(false);
		//locationButton.setVisibility(4);
		locationButton.setText("View Location in google maps");
		locationButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddEventActivity.this, LocationActivity.class);
            	intent.putExtra("loc", location.getText().toString());
            	intent.putExtra("eventLocName", venue.getText().toString());
				startActivity(intent);
			}
			
		});
		contactsButton.setVisibility(4);
		createButton.setText("Finished");
		createButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();		
			}
		});
	}

}
