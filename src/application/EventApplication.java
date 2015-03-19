package application;

import android.app.Application;

public class EventApplication extends Application {
	public static final String MyPREFERENCES = "s3369633Prefs" ;
	public static final String buffer = "bufferTime"; 
	public static final String check = "checkTime";
	
	public static String getPreferences() {
		return MyPREFERENCES;
	}
	public static String getBufferField() {
		return buffer;
	}
	public static String getCheckField() {
		return check;
	} 	
	
}
