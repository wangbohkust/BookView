package activity.bookview;

import java.util.Observable;

import model.Book;
import model.BookView;
import myUtil.Callable;

public class BookView_One_Ctrl extends Observable
{
	public final static String TAG = "BookView_One_Ctrl";
	public final static class State {
		public final static int loading = 1;
		public final static int ready = 2;
	}
	
	public String bookViewPtr = "";
	public model.BookView bookView;
	public model.Book book;
	public model.User author;
	public model.BookView_Comment[] commentArr;
	public model.User[] relatedUserArr;
	
	public BookView_One_Ctrl(String bookViewPtr) {
		this.bookViewPtr = bookViewPtr;
	}
	
	public void refresh() {
		this.setChanged();
		this.notifyObservers(BookView_One_Ctrl.State.loading);
		model.BookView.server_get(this.bookViewPtr, new Callable() {

			@Override
			public void callback(Object d) {
				model.BookView.GetResult res = (model.BookView.GetResult)d;
				BookView_One_Ctrl.this.bookView = res.bookView;
				BookView_One_Ctrl.this.author = res.relatedUser;
				BookView_One_Ctrl.this.book = res.relatedBook;
				
				BookView_One_Ctrl.this.bookView.server_list_comment(new Callable() {

					@Override
					public void callback(Object d) {
						model.BookView.ListCommentResult res = (model.BookView.ListCommentResult) d;
						BookView_One_Ctrl.this.commentArr = res.commentArr;
						BookView_One_Ctrl.this.relatedUserArr = res.relatedUserArr;
						BookView_One_Ctrl.this.setChanged();
						BookView_One_Ctrl.this.notifyObservers(BookView_One_Ctrl.State.ready);
					}
					
				});
				
			}
			
		});
	}
	
}
