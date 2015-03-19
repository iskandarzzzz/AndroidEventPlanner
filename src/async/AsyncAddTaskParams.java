package async;

import model.AbstractEvent;
import android.content.Context;

public class AsyncAddTaskParams {
	private AbstractEvent e;
	private Context c;
	private Boolean edit;
	
	public AsyncAddTaskParams(AbstractEvent e, Context c, Boolean edit){
		this.e = e;
		this.c = c;
		this.edit = edit;
	}

	public AbstractEvent getEvent() {
		return e;
	}

	public Context getContext() {
		return c;
	}

	public Boolean getEdit() {
		return edit;
	}

	
}
