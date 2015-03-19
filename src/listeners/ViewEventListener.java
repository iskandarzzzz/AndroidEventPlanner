package listeners;

import activities.AddEventActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;

public class ViewEventListener implements OnLongClickListener {
	private Context context;
	private String id;
	
	public ViewEventListener(Context c, String id){
		context = c;
		this.id = id;
	}

	@Override
	public boolean onLongClick(View v) {
		Intent intent = new Intent(context, AddEventActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("view", true);
		((Activity)context).startActivity(intent);
		return true;
	}
}
