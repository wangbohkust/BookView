package activity.search;

import java.util.ArrayList;

import model.Book;
import myUtil.Callable;
import hkust.comp4521.project.R;
import activity.book.BookAdaptor;
import activity.bookcomment.bookcomment;
import activity.bookview.BookViewAdaptor;
import activity.bookview.BookViewInfo;
import activity.compose.Compose;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/*public class SearchActivity extends ListActivity {


 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.fragment_search);
 // Get the SearchView and set the searchable configuration
 SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
 //SearchView searchView = (SearchView) findItemById(R.id.);
 //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
 //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

 // Get the intent, verify the action and get the query
 Intent intent = getIntent();
 if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
 String query = intent.getStringExtra(SearchManager.QUERY);
 doMySearch(query);
 }
 }

 private void doMySearch(String query) {
 // TODO Auto-generated method stub

 }

 }*/

/*
 * class that performs searches based on a query string and presents the search results.
 * Retrieve the query
 * Search your data
 * Presenting your result
 */
public class SearchActivity extends ListActivity {

	//private BookViewInfo[] searchResult;
	Handler handler = new Handler();
	private Book[] booklist;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_search);

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		handleIntent(getIntent());

		// display the result
		/*BookAdaptor adapter = new BookAdaptor(this, booklist);
		setListAdapter(adapter);*/
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
	}

	/*
	 * @param do the search according to your query, suppose the query is the
	 * name of the book
	 */
	private void doMySearch(String query) {
		// TODO Auto-generated method stub
        //test instance
		//test instance
				Book.server_search(query, new Callable() {

					@Override
					public void callback(Object d) {
						booklist = (Book[])d;
						handler.post(new Runnable() {

							@Override
							public void run() {
								if(booklist.length<=0) {
									Toast.makeText(getApplicationContext(), "nothing found", Toast.LENGTH_SHORT).show();
									return;
								}
								BookAdaptor adapter = new BookAdaptor(SearchActivity.this, booklist);
								setListAdapter(adapter);
								//Toast.makeText(getApplicationContext(), booklist[0].name, Toast.LENGTH_SHORT).show();
								
							}
							
						});
						
					}
					
				});

	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		// call detail activity for clicked entry
		model.Book item = (model.Book)getListAdapter().getItem(position);
		
		Intent intent = new Intent(this, bookcomment.class);
		intent.putExtra("book", item);
		this.startActivity(intent);
	}
	
	/*
	 * Should a user start a new search from within your search activity, 
	 * Android would recycle the instance and call the method onNewIntent()
	 *  with the new search intent as its parameter.
	 */
	public void onNewIntent(Intent intent) { 
		setIntent(intent);
		handleIntent(intent);
	}

}
