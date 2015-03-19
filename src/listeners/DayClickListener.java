package listeners;

import activities.adapters.MonthAdapter;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DayClickListener implements OnClickListener {
	private MonthAdapter adapter;
	private int position;
	
	public DayClickListener(MonthAdapter a, int pos){
		adapter = a;
		position = pos;
	}
	@Override
	public void onClick(View v) {
		for(int i=0; i< adapter.getCount(); i++){
			TextView day = (TextView) adapter.getItem(i);
			if(i==position && day.getText()==""){
				return;
			}
			if(i==position){
				day.setBackgroundColor(Color.RED);
				adapter.setDay(i);
			}else{
				day.setBackgroundColor(Color.WHITE);
			}
		}
	}

}
