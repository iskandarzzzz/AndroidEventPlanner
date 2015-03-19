package activities;

import service.DistanceFromEvent;

import com.example.assignment1.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import application.EventApplication;

public class Preferences extends Activity {

	   EditText bufferTime;
	   EditText checkTime;
	   Button done;
	   Button back;
	   private static final String TAG = "Preferences";

	   SharedPreferences sharedpreferences;

	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.preferences_view);

	      bufferTime = (EditText) findViewById(R.id.bufferTime);
	      checkTime = (EditText) findViewById(R.id.checkTime);
	      done = (Button) findViewById(R.id.doneButton);
	      back = (Button) findViewById(R.id.exitButton);
	      sharedpreferences = getSharedPreferences(EventApplication.getPreferences(), Context.MODE_PRIVATE);

	      if (sharedpreferences.contains(EventApplication.getBufferField()))
	      {
	    	  bufferTime.setText(Integer.toString(sharedpreferences.getInt(EventApplication.getBufferField(), 15)));

	      }
	      if (sharedpreferences.contains(EventApplication.getCheckField()))
	      {
	         checkTime.setText(Integer.toString(sharedpreferences.getInt(EventApplication.getCheckField(), 15)));
	      }
	      
	      done.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				changePrefs();
				restartService();
			}
	      });
	      
	      back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	      });
	     
	   }

	   
	   private void changePrefs(){
		  int bt = 15;
		  int ct = 15;
		  try{
			  bt  = Integer.parseInt(bufferTime.getText().toString());
		  }catch(NumberFormatException e){
			  //this might be because the field was left blank
			  bt = 15;
		  }
		  try{
	      	ct  = Integer.parseInt(checkTime.getText().toString());
		  }catch(NumberFormatException e){
			  //this might be because the field was left blank
			  ct = 15;
		  }
	      Editor editor = sharedpreferences.edit();
	      editor.putInt(EventApplication.getBufferField(), bt);
	      editor.putInt(EventApplication.getCheckField(), ct);
	      editor.commit();
	      Toast.makeText(getApplicationContext(), "Preferences Saved", Toast.LENGTH_SHORT).show();
	   }

	   private void restartService(){
		   Log.v(TAG, "Restarting service");
		   stopService(new Intent(this, DistanceFromEvent.class));
		   startService(new Intent(this, DistanceFromEvent.class));
	   }
}
