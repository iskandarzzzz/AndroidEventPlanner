package listeners;

import activities.SelectContactsActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


public class ContactsButtonListener implements OnClickListener {
	private static final String TAG = "contactsButtonListener";
	Context context;
	
	public ContactsButtonListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
        Log.v(TAG, "Contacts button clicked");
        Intent intent = new Intent(context, SelectContactsActivity.class);
        ((Activity)context).startActivityForResult(intent, 2);	
	}

}
