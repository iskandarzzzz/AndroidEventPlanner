package async;

import model.EventContainer;
import model.EventDataSource;
import android.content.Context;
import android.os.AsyncTask;

public class LoadEventsAsync extends AsyncTask<Context, Void, Void> {

	@Override
	protected Void doInBackground(Context... params) {
		if(EventContainer.getInstance().getEvents().size()==0){
			EventDataSource data = new EventDataSource(params[0]);
			data.openDB();
			data.loadAllEventsIntoMemory();
			data.closeDB();
		}
		return null;
	}

}
