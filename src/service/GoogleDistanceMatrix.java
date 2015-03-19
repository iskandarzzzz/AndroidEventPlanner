package service;

import java.util.GregorianCalendar;
import com.example.assignment1.R;
import model.Event;
import activities.AddEventActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import application.EventApplication;

public class GoogleDistanceMatrix extends AsyncTask<Void, Void, Void> {
	private final String TAG = "GoogleDistanceMatrix";
	private Event event;
	private Location location;
	private Context context;

	public GoogleDistanceMatrix(Context c, Location l, Event e){
		this.context = c;
		this.event = e;
		this.location = l;
	}

	public long timeToGetToEvent(){
		String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + location.getLatitude() + "," + location.getLongitude() + "&destination=" + event.getLocation() + "&mode=driving";
		DistanceRequest dr = new DistanceRequest(url);
		return dr.timeToGetHere();
	}

	private void checkAlert(){
		SharedPreferences sp = context.getSharedPreferences(EventApplication.getPreferences(), Context.MODE_PRIVATE);
		if(!event.eventOccured() && !event.isNotified()){
			long currentTime = new GregorianCalendar().getTimeInMillis();
			long buffer = sp.getInt(EventApplication.getBufferField(),15) * 60 * 1000;
			if(event instanceof Event){
				//get the time to event in seconds and convert to milliseconds
				long travelTime = timeToGetToEvent() * 1000;
				long leaveTime = currentTime + travelTime + buffer;
				if(travelTime < 0){
					Log.v(TAG, "Could not get distance to event in seconds");
				}else if(leaveTime >= event.getDate().getTimeInMillis()){
					Log.v(TAG, event.getTitle() + " is happening soon send notification");
					sendNotification();
					event.setNotified(true);
				}else{
					Log.v(TAG, event.getTitle() + " will not be missed");
				}
			}else{
				Log.v(TAG, "The event doesn't have a location so we can't do location services");
			}
		}else{
			Log.v(TAG, "The event " + event.getTitle() + " has occured so do not check");
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		Log.v(TAG, "Starting async task for " + event.getTitle());;
		checkAlert();
		return null;
	}
	
	
	private void sendNotification(){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this.context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Event occuring soon")
		        .setContentText(event.getTitle() + " is starting soon!");
		Intent resultIntent = new Intent(context, AddEventActivity.class);
		resultIntent.putExtra("id", event.getId());
		Log.v(TAG, "Event id for notification is " + event.getId());
		resultIntent.putExtra("view", true);
		//disabled this so could work with my tablet for demo...
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//		stackBuilder.addParentStack(StartActivity.class);
//		// Adds the Intent that starts the Activity to the top of the stack
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent =
//		        stackBuilder.getPendingIntent(
//		            0,
//		            PendingIntent.FLAG_UPDATE_CURRENT
//		        );
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this.context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1234, mBuilder.build());
	}
}
