package activities;

import com.example.assignment1.R;
import java.util.Calendar;
import java.util.GregorianCalendar;
import listeners.BackMonthListener;
import listeners.ForwardMonthListener;
import listeners.ScheduleEventListener;
import activities.adapters.MonthAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class CalendarActivity extends Activity {
	
	private static final String TAG = "CalendarActivity";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	//create layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calander_view);
        Log.v(TAG, "layout created");
        //get todays date
    	GregorianCalendar userCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
    	//find stuff for main layout
    	GridView calendar = (GridView) findViewById(R.id.calanderGridView);
    	TextView month = (TextView) findViewById(R.id.calanderMonthView);
        Button back = (Button) findViewById(R.id.backMonth);
        Button forward =  (Button) findViewById(R.id.forwardMonth);
        Button schedule = (Button) findViewById(R.id.addButtonView);
        Log.v(TAG, "found all elements in layout");
    	//set the month to this month
        month.setText(theMonth(userCalendar.get(Calendar.MONTH)) + " " + userCalendar.get(Calendar.YEAR));
        Log.v(TAG, "successfully set month");
    	//create grid adapter and set it
    	MonthAdapter adapter = new MonthAdapter(this, userCalendar);
        calendar.setAdapter(adapter);
        Log.v(TAG, "created and added adapter");
        //add listeners
        back.setOnClickListener(new BackMonthListener(userCalendar, adapter, month));
        forward.setOnClickListener(new ForwardMonthListener(userCalendar, adapter, month));
        schedule.setOnClickListener(new ScheduleEventListener(this, adapter));
        Log.v(TAG, "set listeners");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
    
}
