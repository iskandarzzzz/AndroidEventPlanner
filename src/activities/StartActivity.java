package activities;

import service.DistanceFromEvent;

import com.example.assignment1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import async.LoadEventsAsync;

public class StartActivity extends Activity {
	private final String TAG = "StartActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Loading events from database into memory");
        new LoadEventsAsync().execute(this);
        Log.v(TAG, "Starting distance from event service");
        Intent i = new Intent(this, DistanceFromEvent.class);
        this.startService(i);
        setContentView(R.layout.activity_main);
        Button all = (Button) findViewById(R.id.allButton);
        all.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this, ListViewActivity.class);
				startActivity(intent);
			}
        	
        });
        Button calendar = (Button) findViewById(R.id.calendarButton);
        calendar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this, CalendarActivity.class);
				startActivity(intent);
			}
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
            	Intent intent = new Intent(StartActivity.this, Preferences.class);
            	startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
