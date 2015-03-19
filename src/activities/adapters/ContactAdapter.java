package activities.adapters;

import java.util.ArrayList;

import com.example.assignment1.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	private static final String TAG = "ContactAdapter";
	private Context context;
	private ArrayList<Contact> contacts;
	
	public ContactAdapter(Context context){
		this.context = context;
		contacts = new ArrayList<Contact>();
		Cursor data = ((Activity)this.context).getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);
		int nameField = data.getColumnIndex(Email.DISPLAY_NAME_PRIMARY);
		int emailField = data.getColumnIndex(Email.ADDRESS);
		while(data.moveToNext()){
		contacts.add(new Contact(data.getString(nameField),data.getString(emailField)));
		}
		data.close();
	}

	@Override
	public int getCount() {
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public ArrayList<String> getCheckedEmails(){
		ArrayList<String> selected = new ArrayList<String>();
		for(Contact c : contacts){
			if(c.getChecked()==true){
				selected.add(c.getEmail());
			}
		}
        Log.v(TAG, selected.size() + " contacts were selected");
		return selected;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
	        //inflate grid item
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.contact_item, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.contactName);
		name.setText(((Contact)getItem(position)).getName());
		TextView email = (TextView) convertView.findViewById(R.id.contactEmail);
		email.setText(((Contact)getItem(position)).getEmail());
		CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.contactCheckBox);
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			private int position;
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Contact c = (Contact) getItem(position);
				c.setChecked(isChecked);
			}
			private OnCheckedChangeListener init(int pos){
				position = pos;
				return this;
			}
		}.init(position));
		return convertView;
	}
	
	
	private class Contact {
		private String email;
		private String name;
		private boolean set;
		public Contact(String n, String e){
			email = e;
			name = n;
			set = false;
		}
		
		public String getName(){
			return name;
		}
		
		public String getEmail(){
			return email;
		}
		
		public void setChecked(boolean b){
			set = b;
		}
		
		public boolean getChecked(){
			return set;
		}
	}

}
