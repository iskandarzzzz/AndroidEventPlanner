package activities.adapters;

import com.example.assignment1.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import listeners.DayClickListener;
import listeners.LongClickDayListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MonthAdapter extends BaseAdapter {
	
	private static final String TAG = "MonthAdapter";
	private GregorianCalendar calendar;
	private Context context;
	private ArrayList<TextView> days;
	private int month;
	private int year;
	private int day = -1;
	private final int[] monthLengths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	public MonthAdapter(Context context, GregorianCalendar calendar){
		this.calendar = calendar;
		this.context = context;
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		createTextViews(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
	    //set selected to be the day of the week for this month by default
		this.day = ((offset(this.year,this.month)-1)+(calendar.get(Calendar.DAY_OF_MONTH)-1));
		TextView tv = (TextView)getItem(this.day);
		tv.setBackgroundColor(Color.RED);
		
	}

    //creates all the TextViews required for the specified month
	//including buffer at the start to properly format days of week
	public void createTextViews(int year, int month){
		days = new ArrayList<TextView>();
		int dayOfWeek = offset(year, month);
		int numDays = monthLengths[month];
		if(month == 1 ){
			if(calendar.isLeapYear(year)){
				numDays++;
			}
		}
		for(int i=1;i<dayOfWeek;i++){
			days.add(createNewTextView(-1));
		}
		for(int i=0;i<numDays;i++){
			days.add(createNewTextView(i+1));
		}
        Log.v(TAG, "Sucessfully created TextViews");
	}

	public int getCount() {
		return days.size();
	}

	public Object getItem(int position) {
		return (Object) days.get(position);
	}

    //creates each day on the calendar and adds a listener
	@SuppressLint("InflateParams") 
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
	        //inflate grid item
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.grid_item, null);
		}
		//get linear layout in grid item
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.item_root);
		//add TextView to linear layout
		TextView item = (TextView) getItem(position);
		addItem(layout,item);
        //set listener
		item.setOnClickListener(new DayClickListener(this, position));
		item.setOnLongClickListener(new LongClickDayListener(context, this, position));
		if(item.getText()==""){
	        Log.v(TAG, "Added Blank TextView to grid item as buffer.");
		}
		else{
			Log.v(TAG, "Added day of month '" + item.getText() + "' to current month." );
		}
		return convertView;
	}
	
    //changes the month in the adapter
    public void ChangeMonth(GregorianCalendar c) {
        this.calendar = c;
        createTextViews(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        this.setDay(-1);
        Log.v(TAG, "Set selected to be invalid.");
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		Log.v(TAG, "Successfully changed months and updated adapter values");
    }
    
    //creates and formats a new TextView
    public TextView createNewTextView(int i){
    	TextView tview = new TextView(context);
    	tview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	tview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    	tview.setTextAppearance(context, android.R.style.TextAppearance_Large);
    	if(i==-1){
    		tview.setText("");
    		tview.setClickable(false);
    	}else{
    		tview.setText(Integer.toString(i));
    	}
    	return tview;
    }
    
    void addItem(LinearLayout layout, TextView item){
    	//check to make sure that TextView does not have a parent
    	if(item.getParent()!=null){
    		((LinearLayout)item.getParent()).removeView(item);
    	}
    	//clear layout to add new TextView
    	layout.removeAllViews();
    	layout.addView(item);
    }

	public int getDay() {
		return day;
	}

	public void setDay(int selected) {
		this.day = selected;
		if(selected>=0)
			Log.v(TAG, "Set selected to be " + ((TextView)getItem(selected)).getText());
	}

	@Override     //was required due to extending base adapter...
	public long getItemId(int position) {
		return position;
	}
	
	//calculates how much blank space we must put in for the calendar
	public int offset(int year, int month){
		GregorianCalendar temp = new GregorianCalendar(year, month, 1);
		return temp.get(Calendar.DAY_OF_WEEK);
	}
    
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public int positionToActualDay(int pos){
		TextView tv = (TextView) getItem(pos);
		return Integer.parseInt(tv.getText().toString());
	}
}