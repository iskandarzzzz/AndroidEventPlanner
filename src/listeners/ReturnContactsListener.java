package listeners;

import java.util.ArrayList;

import activities.adapters.ContactAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ReturnContactsListener implements OnClickListener  {
	
	private static final String TAG = "returnContactsListener";
	private Context context;
	private ContactAdapter adapter;

	public ReturnContactsListener(Context context, ContactAdapter adapter) {
		this.context = context;
		this.adapter = adapter;
	}

	@Override
	public void onClick(View v) {
		ArrayList<String> emails = adapter.getCheckedEmails();
		Intent result = new Intent();
		result.putStringArrayListExtra("emails", emails);
		((Activity)context).setResult(Activity.RESULT_OK, result);
        Log.v(TAG, "sent " + emails.size() + " contact emails");
        ((Activity)context).finish();
		
	}
	
	

}
