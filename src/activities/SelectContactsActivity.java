package activities;

import listeners.ReturnContactsListener;
import com.example.assignment1.R;

import activities.adapters.ContactAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
public class SelectContactsActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_view);
        ListView list = (ListView) this.findViewById(R.id.contactList);
        ContactAdapter adapter = new ContactAdapter(this);
        list.setAdapter(adapter);
        Button finish = (Button) this.findViewById(R.id.done);
        finish.setOnClickListener(new ReturnContactsListener(this,adapter));
	}

}
