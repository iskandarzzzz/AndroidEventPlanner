package service;

import java.util.ArrayList;
import model.AbstractEvent;
import model.Event;
import model.EventContainer;
import broadcastrecievers.AlarmReceiver;
import broadcastrecievers.NetworkReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import application.EventApplication;

public class DistanceFromEvent extends Service {
	private final String TAG = "DistanceFromEventService";
	private boolean waiting = false;
	private AlarmReceiver ar;
	private NetworkReceiver nr;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.v(TAG, "The service has started");
		//this is the interval in seconds the service will execute the alarm receiver 
		//the alarm runs on a continuous loop every x seconds and calls doService()
		SharedPreferences sp = getSharedPreferences(EventApplication.getPreferences(), Context.MODE_PRIVATE);
		int seconds = sp.getInt(EventApplication.getCheckField(),15);
		//create and register alarm
		ar = new AlarmReceiver();
		registerReceiver(ar, new IntentFilter("broadcastrecievers.AlarmReceiver"));
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent("broadcastrecievers.AlarmReceiver"), PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), seconds*1000, pi);
		Log.v(TAG, "Set the alarm to trigger doService() in " + seconds + "secs");
		return 1;
	}
	
	@Override
	public void onDestroy(){
		Log.v(TAG, "Service is being destroyed");
		if(ar!=null)
			try{
			unregisterReceiver(ar);
			}catch(Exception e){}
		if(nr!=null)
			try{
			unregisterReceiver(nr);
			}catch(Exception e){}
	}
	
	//the waiting variable was created to see if we are waiting for the network reciever to
	//tell us when we have regained network connectivity...
	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}
	
	//this function is used and called by a repeating alarm
	public void doService(){
		if(waiting == true){
			//do nothing as broadcast receiver will call when it is done
			Log.v(TAG, "Service is still waiting to regain connection");
		}else if(!canConnect()){
			//we are not waiting and we cannot connect so call the network broadcast receiver
			//set the receiver to listen to 2 filters and set waiting to true
			Log.v(TAG, "Service cannot gain connection so is creating broadcast reciever...");
			nr = new NetworkReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction("android.net.wifi.STATE_CHANGE");
			filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
			this.registerReceiver(nr, filter);
			setWaiting(true);
		}else{
			//we can connect and are not waiting
			doLocationServices();
		}
	}
	
	//this method loops through all future events and checks if we should send a notification
	private void doLocationServices(){
		Log.v(TAG, "Doing location services");
		Location l = getCurrentLocation();
		if(l!=null){
			Log.v(TAG, "Got current location");
		}else{
			Log.v(TAG, "Current location is null.. this could be because you are using an emulator. setting location to RMIT");
			l = new Location(TAG);
			l.setLatitude(-37.8071561);
			l.setLongitude(144.9637574);
		}
		ArrayList<AbstractEvent> events = EventContainer.getInstance().getEvents();
		Log.v(TAG, events.size() + " events to run location services on");
		for(int i = 0; i < events.size(); i++){
			AbstractEvent event = events.get(i);
			if(event instanceof Event){
				Log.v(TAG, "Running distance matrix on " + events.get(i).getTitle());
				new GoogleDistanceMatrix(this, l,(Event) event).execute();
			}else{
				Log.v(TAG, "Cannot run distance matrix on " + events.get(i).getTitle() + " because it has no location");
			}
		}
	}
	
	//tests if we have an Internet connection
	public boolean canConnect(){
		Log.v(TAG, "Testing if we can connect");
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null && info.isConnected()){
			Log.v(TAG, "We can connect");
			return true;
		}else{
			Log.v(TAG, "We cannot connect");
			return false;
		}
	}
	
	private Location getCurrentLocation(){
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}	

}
