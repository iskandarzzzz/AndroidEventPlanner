package listeners;

import activities.AddEventActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class EditEventListener implements OnClickListener {
	private Context context;
	private String id;
	
	public EditEventListener(Context c, String id){
		this.context = c;
		this.id = id;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, AddEventActivity.class);
		intent.putExtra("id", this.id);
		((Activity)context).startActivity(intent);		
	}

}
