package listeners;

import activities.LocationActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class LocationButtonListener implements OnClickListener {
	
	private static final String TAG = "locationButtonListener";
	private Context context;

	public LocationButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
        Log.v(TAG, "Location button clicked");
        Intent intent = new Intent(this.context, LocationActivity.class);
       ((Activity)context).startActivityForResult(intent, 1);
	}

}
