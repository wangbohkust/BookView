package activity.popular;

import java.util.Observable;
import java.util.Observer;

import mockData.MockData;
import model.User;
import hkust.comp4521.project.R;
import activity.bookview.BookViewAdaptor;
import activity.bookview.BookViewInfo;
import activity.bookview.BookView_One;
import activity.bookview.TestAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import myUtil.Callable;

public class PopPage extends ListFragment implements Observer
{
	private static final String TAG = "PopPage";
	private PopPage_Ctrl ctrl = new PopPage_Ctrl();
	private Handler handler = new Handler();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ctrl.addObserver(this);


	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		ctrl.refresh();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		model.BookView item = (model.BookView) getListAdapter().getItem(position);

		Intent in = new Intent(getActivity().getApplicationContext(), BookView_One.class);
		in.putExtra("bookViewPtr", item.get_ptr());
		PopPage.this.startActivity(in);

	}

	/**
	 * the function to handle controller change. it simply calls update_sync in the main UI thread.
	 */
	@Override
	public void update(Observable observable, final Object state) {
		handler.post(new Runnable()
		{

			@Override
			public void run() {
				PopPage.this.update_sync(state);
			}

		});
	}
	/**
	 * the function to handle controller change. it will change the UI, so this function should only be called in the main UI thread.
	 * @param state
	 */
	public void update_sync(Object state) {
		switch ((Integer) state)
		{
		// when loading data from server, display a message
		case PopPage_Ctrl.State.loading:
			ArrayAdapter simpleAdapter = new ArrayAdapter<String>(getActivity(), R.layout.text, new String[]
				{ "loading..." });
			this.setListAdapter(simpleAdapter);
			break;
		// when loading complete, display the fresh information
		case PopPage_Ctrl.State.ready:
			BookViewAdaptor adapter = new BookViewAdaptor(getActivity(), this.ctrl.bookViewArr,
					this.ctrl.relatedBookArr, this.ctrl.relatedUserArr);

			setListAdapter(adapter);
			break;
		}
	}



	

}