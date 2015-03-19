package listeners;

import activities.adapters.DeleteDialog;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class DelEventListener implements OnClickListener {
	private Context context;
	private String message;
	private String eventId;
	
	public DelEventListener(Context c, String m, String e){
		context = c;
		message = m;
		eventId = e;
	}

	@Override
	public void onClick(View v) {
		DeleteDialog dialog = new DeleteDialog();
	    Bundle args = new Bundle();
	    args.putString("msg", message);
	    args.putString("eventId", eventId);
	    dialog.setArguments(args);
		dialog.show(((Activity)context).getFragmentManager(), "tag");
	}
	
	

}
