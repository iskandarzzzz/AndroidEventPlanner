package broadcastrecievers;


import service.DistanceFromEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	private final String TAG = "AlarmReciever";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.v(TAG, "The alarm just rang... running service");
		DistanceFromEvent service = (DistanceFromEvent)context;
		service.doService();
	}
	

}
