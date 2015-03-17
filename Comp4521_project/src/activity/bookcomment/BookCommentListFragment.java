package activity.bookcomment;

import mockData.MockData;
import myUtil.Callable;
import hkust.comp4521.project.R;
import activity.bookview.BookViewAdaptor;
import activity.bookview.BookViewInfo;
import activity.bookview.BookView_One;
import activity.fresh.FreshPage;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;


public class BookCommentListFragment extends ListFragment
{
	private static final String TAG = "BookCommentList";
	Handler handler = new Handler();
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    final model.Book book = (model.Book)this.getActivity().getIntent().getExtras().getSerializable("book");
	    
	    book.server_list_book_view(new Callable() {

			@Override
			public void callback(Object d) {
				model.Book.ListBookViewResult res = (model.Book.ListBookViewResult) d;
				final model.BookView[] bookViewArr = res.bookViewArr;
				final model.Book[] relatedBookArr = new model.Book[]{book};
				final model.User[] relatedUserArr = res.relatedUserArr;
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						//wangbo replace the test instance
					    BookViewAdaptor adapter = new BookViewAdaptor(getActivity(),
					    		bookViewArr, relatedBookArr, relatedUserArr);
				       
					    setListAdapter(adapter);
					}
					
				});
				
			}
	    	
	    });
	   
	  }
	
	
	
	
	 @Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
	    //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	    
	 }




	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		 final model.Book book = (model.Book)this.getActivity().getIntent().getExtras().getSerializable("book");
		    
		    book.server_list_book_view(new Callable() {

				@Override
				public void callback(Object d) {
					model.Book.ListBookViewResult res = (model.Book.ListBookViewResult) d;
					final model.BookView[] bookViewArr = res.bookViewArr;
					final model.Book[] relatedBookArr = new model.Book[]{book};
					final model.User[] relatedUserArr = res.relatedUserArr;
					
					handler.post(new Runnable() {

						@Override
						public void run() {
							//wangbo replace the test instance
						    BookViewAdaptor adapter = new BookViewAdaptor(getActivity(),
						    		bookViewArr, relatedBookArr, relatedUserArr);
					       
						    setListAdapter(adapter);
						}
						
					});
					
				}
		    	
		    });
		   
	}

}
