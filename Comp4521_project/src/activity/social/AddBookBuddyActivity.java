package activity.social;

import java.util.Observable;
import java.util.Observer;

import model.User;
import hkust.comp4521.project.R;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookBuddyActivity extends Activity implements Observer {
	
	EditText emailInput;
	Button searchBtn;
	AddBookBuddy_Ctrl ctrl = new AddBookBuddy_Ctrl();
	Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfollowingview);
		
		ctrl.addObserver(this);
		
		emailInput = (EditText) findViewById(R.id.addFollowingEditText);
		searchBtn = (Button) findViewById(R.id.addFollowingButton);
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ctrl.add(emailInput.getText().toString());
			}
			
		});
		
		
	}

	@Override
	public void update(Observable observable, final Object state) {
		handler.post(new Runnable()
		{

			@Override
			public void run() {
				update_sync(state);
			}

		});
	}
	
	private void update_sync(Object state) {
		switch ((Integer) state)
		{

		case AddBookBuddy_Ctrl.State.loading:

			break;
		case AddBookBuddy_Ctrl.State.ready:
		
			break;
			
		case AddBookBuddy_Ctrl.State.success:
			Toast.makeText(this.getApplicationContext(), "add following success", Toast.LENGTH_SHORT).show();
			this.finish();
			break;
		case AddBookBuddy_Ctrl.State.fail:
			Toast.makeText(this.getApplicationContext(), "add following failed", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
}
