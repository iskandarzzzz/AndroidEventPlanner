package async;
import model.Event;
import model.EventDataSource;
import android.content.Context;
import android.os.AsyncTask;

public class AddOrEditEventAsync extends AsyncTask<AsyncAddTaskParams, Void, Boolean> {
	private Event e;
	private Context c; 
	
	@Override
	protected void onPreExecute(){
		
	}
	
	protected Boolean doInBackground(AsyncAddTaskParams... params) {
		this.c = params[0].getContext();
		//cast the abstract event into event...
		if(params[0].getEvent() instanceof Event){
			this.e = (Event)params[0].getEvent();
		}else{
			//cannot cast something is wrong
			return false;
		}
		EventDataSource db = new EventDataSource(c);
		db.openDB();
		if(!params[0].getEdit()){
			db.createEvent(e);
		}else{
			db.updateEvent(e);
		}
		db.closeDB();
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// to do
	}

}
