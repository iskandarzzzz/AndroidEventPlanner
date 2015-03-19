package activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment1.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

public class LocationActivity extends Activity{
	private static final String TAG = "LocationActivity";
	TextView searchText;
	Context context;
	Button searchButton;
	Button finishButton;
	EditText address;
	TextView latLong;
	MapView mv;
	GoogleMap map;
	LatLng loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		boolean working = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);
		initilizeValues();
		mv.onCreate(savedInstanceState);
		working = startGoogleMap();
		if(working){
			Bundle extras = getIntent().getExtras();
			if(getIntent().hasExtra("loc")){
				String eventLocName = extras.getString("EventLocName");
				String locs = extras.getString("loc");
				latLong.setText(locs);
				String[] tokens = locs.split(",");
				loc = new LatLng(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(loc, 16);
				Log.v(TAG, "Created Camera update");
				map.animateCamera(update);
				Log.v(TAG, "Updated camera");
				searchButton.setVisibility(View.GONE);
				address.setVisibility(View.GONE);
				searchText.setText(eventLocName);
				finishButton.setText("DONE");
				finishButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						finish();
					}	
				});
			}else{
				//set default map view to melbourne
				loc = new LatLng(-37.814107, 144.96328);
				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(loc, 4);
				Log.v(TAG, "Created Camera update");
				map.animateCamera(update);
				Log.v(TAG, "Updated camera");
				searchButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {			
						String search = address.getText().toString();
						Geocoder geo = new Geocoder(context, Locale.getDefault());
						try{
							List<Address> addresses = geo.getFromLocationName(search, 1);
							if (addresses.size() == 1) {
								loc = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
								if(map!=null){
									CameraUpdate update = CameraUpdateFactory.newLatLngZoom(loc, 16);
									map.animateCamera(update);
								}
								latLong.setText(String.valueOf(loc.latitude)+","+String.valueOf(loc.longitude));
							}else{
								Toast.makeText(context, "Could not find a location", Toast.LENGTH_LONG).show();
							}
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}  	
				});

				finishButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if(latLong.getText()!=""){
							Intent result = new Intent();
							result.putExtra("loc", latLong.getText().toString());
							setResult(Activity.RESULT_OK, result);
							finish();
						}
					}	
				});
			}
		}else{
			latLong.setText("Google API's not working correctly (check logcat). Enter lat long into address field instead and click submit.");
			searchButton.setEnabled(false);
			finishButton.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					Intent result = new Intent();
					result.putExtra("loc", address.getText().toString());
					setResult(Activity.RESULT_OK, result);
					finish();	
				}

			});

		}
	}


	@Override
	public void onResume() {
		super.onResume();
		if(mv!=null)
			mv.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mv!=null)
			mv.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		if(mv!=null)
			mv.onLowMemory();
	}


	private void initilizeValues(){
		context = this;
		searchText = (TextView) this.findViewById(R.id.textView1);
		searchButton = (Button) this.findViewById(R.id.allButton);
		address = (EditText) this.findViewById(R.id.editText1);
		finishButton = (Button) this.findViewById(R.id.calendarButton);
		latLong = (TextView) this.findViewById(R.id.textView3);
		mv = (MapView) this.findViewById(R.id.map);
	}


	private boolean startGoogleMap(){
		try {
			MapsInitializer.initialize(this);
		} catch (Exception e){
			Log.v(TAG, "Google Play services not avaliable");
			return false;
		}
		if(map==null){
			map = mv.getMap();
		}
		if(map == null){
			Log.v(TAG, "Unable to create google map");
			return false;
		}
		return true;
	}
}
