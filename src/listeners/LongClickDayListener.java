package listeners;

import activities.ListViewActivity;
import activities.adapters.MonthAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class LongClickDayListener implements OnLongClickListener {
	
	private static final String TAG = "longClickDayListener";
	private Context context;
	private MonthAdapter adapter;
	private int position;

	public LongClickDayListener(Context c, MonthAdapter monthAdapter, int position) {
		context = c; 
		adapter = monthAdapter;
		this.position = position;
	}

	@Override
	public boolean onLongClick(View v) {
        Log.v(TAG, "Day long clicked on position " + position);
		TextView dayV = (TextView) adapter.getItem(position);
		if(dayV.getText()!=""){
			int day = Integer.parseInt(dayV.getText().toString());
			int month = adapter.getMonth();
			int year = adapter.getYear();
	        Log.v(TAG, "Creating intent and loading up data");
			Intent intent = new Intent(this.context, ListViewActivity.class);
			intent.putExtra("day", day);
			intent.putExtra("month", month);
			intent.putExtra("year", year);
	        Log.v(TAG, "Opened up new event list for a day");
			context.startActivity(intent);
			return true;
		}
        Log.v(TAG, "But a valid day is not set...");
        return false;
	}

}
