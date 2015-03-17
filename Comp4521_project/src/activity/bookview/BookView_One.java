package activity.bookview;

import java.util.Observable;
import java.util.Observer;

import mockData.MockData;
import hkust.comp4521.project.R;
import activity.fresh.FreshPage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BookView_One extends Activity implements Observer
{

	BookView_One_Ctrl ctrl;
	TextView refTextDom;
	TextView contentDom;
	TextView bookNameDom;
	TextView bookIdDom;
	ImageView imageDom;

	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent in = this.getIntent();
		String bookViewPtr = in.getStringExtra("bookViewPtr");
		this.ctrl = new BookView_One_Ctrl(bookViewPtr);

		this.ctrl.addObserver(this);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.fragment_bookview_total);

		refTextDom = (TextView) findViewById(R.id.firstLine);
		contentDom = (TextView) findViewById(R.id.Bottom);
		bookNameDom = (TextView) findViewById(R.id.bookname);
		bookIdDom = (TextView) findViewById(R.id.bookid);
		imageDom = (ImageView) findViewById(R.id.icon);

		this.ctrl.refresh();
	}

	/**
	 * the function to handle controller change. it simply calls update_sync in
	 * the main UI thread.
	 */
	@Override
	public void update(Observable observable, final Object state) {
		handler.post(new Runnable()
		{

			@Override
			public void run() {
				BookView_One.this.update_sync(state);
			}

		});
	}

	public void update_sync(Object state) {
		switch ((Integer) state)
		{
		case BookView_One_Ctrl.State.loading:
			refTextDom.setText("");
			contentDom.setText("");
			bookNameDom.setText("loading data");
			bookIdDom.setText("");
			break;
		case BookView_One_Ctrl.State.ready:
			bookNameDom.setText(this.ctrl.book.name);
			bookIdDom.setText(this.ctrl.book.ISBN);
			refTextDom.setText(this.ctrl.bookView.refText);
			contentDom.setText(this.ctrl.bookView.content);
			imageDom.setImageBitmap(this.ctrl.author.get_image());
			break;
		}
	}
}