package activities;

import com.example.assignment1.R;

import listeners.AddEventListener;
import activities.adapters.EventAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends Activity {
	private static final String TAG = "ListViewActivity";
	EventAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_view);
		TextView text = (TextView) this.findViewById(R.id.dayViewHead);
		Button newEvent = (Button) this.findViewById(R.id.addButton);
		//get the values passed by listener in bundle and set title
		if(getIntent().hasExtra("day") && getIntent().hasExtra("month") 
				&& getIntent().hasExtra("year")){
			Bundle extras = getIntent().getExtras();
			int day = extras.getInt("day");
			int month = extras.getInt("month");
			int year = extras.getInt("year");
			text.setText("Schedule for " + Integer.toString(day) + "/" + Integer.toString(month+1) +
					"/" + Integer.toString(year));
			adapter = new EventAdapter(this, day, month, year);
			newEvent.setOnClickListener(new AddEventListener(this, day, month, year));
			Log.v(TAG, "Got events");
			// if no values past we want to show all events :)
		}else{
			text.setText("Viewing all events scheduled");
			newEvent.setText("Add Event");
			newEvent.setOnClickListener(new AddEventListener(this, -1, -1, -1));
			adapter = new EventAdapter(this, -1, -1, -1);
		}
		//get the list view
		ListView list = (ListView) this.findViewById(R.id.event_list);
		list.setAdapter(adapter);
	}

	public void onRestart() {
		super.onRestart();
		adapter.getNewData();
		adapter.notifyDataSetChanged();
	}

	public EventAdapter getAdapter(){
		return adapter;
	}
}
