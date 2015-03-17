package activity.social;

import hkust.comp4521.project.R;

import java.util.Observable;
import java.util.Observer;

import mockData.MockData;

import activity.bookview.BookViewAdaptor;
import activity.bookview.BookView_One;
import activity.fresh.FreshPage;
import activity.fresh.FreshPage_Ctrl;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListBookBuddyActivity extends ListActivity implements Observer {
	
	
	private static final String TAG = "ListBookBuddy";
	private ListBookBuddy_Ctrl  ctrl = new ListBookBuddy_Ctrl();
	private Handler handler = new Handler();
	
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    ctrl.addObserver(this);
	    ctrl.refresh();
	  
	    
	    /*UserviewAdaptor adapter = new UserviewAdaptor(this,);
	    setListAdapter(adapter);
	    Toast.makeText(this,"finish setting useview Adaptor initially", Toast.LENGTH_LONG).show();*/
	  }

	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    String itemPosition  = (String) getListAdapter().getItem(position);
	    
		model.BookView item = (model.BookView) getListAdapter().getItem(position);

	
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	  }

	  @Override
		public void update(Observable observable, final Object state) {
			handler.post(new Runnable()
			{

				@Override
				public void run() {
					ListBookBuddyActivity.this.update_sync(state);
				}

			});
		}

	public void update_sync(Object state) {
		switch ((Integer) state)
		{
		// when loading data from server, display a message
		case ListBookBuddy_Ctrl.State.loading:
			ArrayAdapter simpleAdapter = new ArrayAdapter<String>(this, R.layout.text, new String[]
				{ "loading..." });
			this.setListAdapter(simpleAdapter);
			break;
		// when loading complete, display the fresh information
		case ListBookBuddy_Ctrl.State.ready:
			
			UserviewAdaptor adapter = new UserviewAdaptor(this, this.ctrl.followingArr);
			setListAdapter(adapter);
			//Toast.makeText(this,"finish setting useview Adaptor syn refresh", Toast.LENGTH_LONG).show();
			break;
		}
	}
}
		
