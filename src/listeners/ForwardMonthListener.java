package listeners;

import java.util.Calendar;
import java.util.GregorianCalendar;

import activities.adapters.MonthAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ForwardMonthListener implements OnClickListener {

	private GregorianCalendar userCalendar;
	private MonthAdapter adapter;
	private TextView month;
	
	public ForwardMonthListener(GregorianCalendar c, MonthAdapter a, TextView m){
		userCalendar = c;
		adapter = a;
		month = m;
	}
	
	@Override
	public void onClick(View v) {
		userCalendar.add(Calendar.MONTH, 1);
		month.setText(theMonth(userCalendar.get(Calendar.MONTH)) + " " + userCalendar.get(Calendar.YEAR));
		adapter.ChangeMonth(userCalendar);
		adapter.notifyDataSetChanged();
	}
	
    public static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

}
