package async;

import model.EventDataSource;
import android.content.Context;
import android.os.AsyncTask;

public class RemoveEventAsync extends AsyncTask<AsyncRemoveTaskParams, Void, Boolean> {

	@Override
	protected Boolean doInBackground(AsyncRemoveTaskParams... params) {
		String eventId = params[0].getId();
		Context c = params[0].getContext();
		EventDataSource database = new EventDataSource(c);
		database.openDB();
		database.deleteEvent(eventId);
		database.closeDB();
		return true;
	}

}
