package listeners;

import activities.AddEventActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AddEventListener implements OnClickListener {
	
	private static final String TAG = "addEventListener";
	private Context context;
	private int day = -1;
	private int month = -1;
	private int year = -1;

	public AddEventListener(Context context, int d, int m, int y) {
		this.context = context;
		day = d;
		month = m;
		year = y;
	}
	
	public AddEventListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
        Log.v(TAG, "Add event clicked");
        Intent intent = new Intent(this.context, AddEventActivity.class);
        //if we have set the day/month/year
		if(year!=-1 && day!=-1 && month!=-1){
			intent.putExtra("day", day);
			intent.putExtra("month", month);
			intent.putExtra("year", year);
			context.startActivity(intent);
		}else{
			context.startActivity(intent);
		}
	}

}
