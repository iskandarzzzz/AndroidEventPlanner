package activities.adapters;

import model.EventContainer;

import com.example.assignment1.R;

import activities.ListViewActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import async.AsyncRemoveTaskParams;
import async.RemoveEventAsync;

public class DeleteDialog extends DialogFragment {
	private String message;
	private String eventId;

	public Dialog onCreateDialog(Bundle savedInstanceState){
		Bundle extras = getArguments();
		message = extras.getString("msg", "");
		eventId = extras.getString("eventId", "");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("Delete Event")
					.setMessage("Are you sure you want to delete " + message + "?")
        			.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
        				public void onClick(DialogInterface dialog, int id) {
        					OKClicked();
        				}
        			})
        			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
        				public void onClick(DialogInterface dialog, int id) {
        					
        				}
        			});		
					return builder.create();
		}
	
	public void OKClicked(){
		try {
			EventContainer.getInstance().removeEvent(eventId);
			ListViewActivity a = (ListViewActivity) getActivity();
			AsyncRemoveTaskParams params = new AsyncRemoveTaskParams(eventId, a);
			new RemoveEventAsync().execute(params);
			a.getAdapter().getNewData();
			a.getAdapter().notifyDataSetChanged();
		} catch (Exception e) {
			//event already deleted!
		}
	}
}
