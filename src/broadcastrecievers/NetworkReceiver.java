package broadcastrecievers;

import service.DistanceFromEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver 
{
	private final String TAG = "NetworkReceiver";
	
	
	@Override
    public void onReceive(Context context, Intent intent) {
		if(canConnect(context)){
			Log.v(TAG, "We can now connect, doing service and destroying broadcast receiver");
			((DistanceFromEvent) context).setWaiting(false);
			((DistanceFromEvent) context).doService();
			context.unregisterReceiver(this);
		}
    }
	
	
	//tests if we have an Internet connection
	public boolean canConnect(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null && info.isConnected()){
			return true;
		}else{
			return false;
		}
	}
}