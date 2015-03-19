package async;

import android.content.Context;

public class AsyncRemoveTaskParams {
	private String id;
	private Context context;
	
	public AsyncRemoveTaskParams(String s, Context c){
		this.id = s;
		this.context = c;
	}

	public String getId() {
		return id;
	}

	public Context getContext() {
		return context;
	}
	
	
}
