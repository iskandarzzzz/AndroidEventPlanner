package listeners;

import activities.ListViewActivity;
import activities.adapters.MonthAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ScheduleEventListener implements OnClickListener {
	
	private static final String TAG = "scheduleEventListener";
	private Context context;
	private MonthAdapter adapter;

	public ScheduleEventListener(Context context, MonthAdapter adapter) {
		this.context = context;
		this.adapter = adapter;
	}

	@Override
	public void onClick(View v) {
        Log.v(TAG, "Schedule event clicked");
		int day = adapter.getDay();
		if(day!=-1){
			int month = adapter.getMonth();
			int year = adapter.getYear();
	        Log.v(TAG, "Creating intent and loading up data");
			Intent intent = new Intent(this.context, ListViewActivity.class);
			intent.putExtra("day", adapter.positionToActualDay(adapter.getDay()));
			intent.putExtra("month", month);
			intent.putExtra("year", year);
	        Log.v(TAG, "Opened up new event list for a day");
			context.startActivity(intent);
			return;
		}
        Log.v(TAG, "But a valid day is not set...");
	}

}
