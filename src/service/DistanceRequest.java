package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DistanceRequest {
	private final String TAG = "DistanceRequest";
	private String url;


	public DistanceRequest(String url){
		this.url = url;
	}

	public long timeToGetHere(){
		long time = -1;
		JSONObject json;
		try{
			json = new JSONObject(doGetRequest());
		}catch(JSONException e){
			Log.v(TAG, "The JSON Object Recieved is malformed");
			return time;
		}
		if(json!=null){
			time = parseJsonTime(json);
		}
		return time;
	}

	public long parseJsonTime(JSONObject json){
		long result = -1;
		String timeInSeconds;
		try{
			JSONArray array = json.getJSONArray("routes");
			JSONObject routes = array.getJSONObject(0);
			JSONArray array2 = routes.getJSONArray("legs");
			JSONObject legs = array2.getJSONObject(0);
			JSONObject duration = legs.getJSONObject("duration");
			timeInSeconds = duration.getString("value");
			Log.v(TAG, "Time to get to event is " + duration.getString("value"));
		}catch(JSONException e){
			Log.v(TAG, "Error parseJsonTime");
			return result;
		}
		try{
			result = Long.parseLong(timeInSeconds);
		}catch(NumberFormatException e){
			Log.v(TAG, "Error parsing the recieved duration to a long");
			return result;
		}
		return result;
	}

	public String doGetRequest(){
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		StringBuilder sb = new StringBuilder();
		try{
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
				String line;
				while ((line = br.readLine()) != null){
					sb.append(line);
				}
			}
		}catch(Exception exception){
			Log.v(TAG, "Something went wrong when trying to get the time taken to travel to the event");
		}
		return sb.toString();
	}

}
