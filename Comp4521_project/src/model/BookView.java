package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import myUtil.Callable;
import myUtil.Server;

public class BookView extends Data
{
	public static final String TAG = "BookView";
	public String authorPtr = "";
	public String bookPtr = "";
	public String refText = "";
	public String content = "";
	public long createTime = 0;
	
	/**
	 * fetch a BookView from server by its pointer
	 * 
	 * @param ptr the pointer of the BookView
	 * @param callback 
	 * 
	 * callback
	 * @param {GetResult}
	 */
	public static void server_get(String ptr, final Callable callback) {
		Server.post("BookView/get", new String[]{ptr}, new Callable() {

			@Override
			public void callback(Object d) {
				if(!(d instanceof JSONObject)) {
					Log.e(TAG, "an error occurred");
					callback.callback(null);
					return;
				}
				JSONObject res = (JSONObject) d;
				try {
					JSONObject bookViewJson = res.getJSONObject("bookView");
					JSONObject relatedUserJson = res.getJSONObject("relatedUser");
					JSONObject relatedBookJson = res.getJSONObject("relatedBook");
					BookView bookView = (BookView)Data.from_json(bookViewJson);
					User relatedUser = (User)Data.from_json(relatedUserJson);
					Book relatedBook = (Book)Data.from_json(relatedBookJson);
					GetResult rtn = new GetResult();
					rtn.bookView = bookView;
					rtn.relatedUser = relatedUser;
					rtn.relatedBook = relatedBook;
					callback.callback(rtn);
					return;
				} catch (JSONException e) {
					Log.e(TAG, "server_get bad response");
					callback.callback(null);
					return;
				}
				
			}
			
		});
	}
	public static class GetResult {
		public BookView bookView;
		public User relatedUser;
		public Book relatedBook;
	}
	/**
	 * upload the change of this book-view.
	 * @param callback
	 * 
	 * callback
	 * @param {BookView} this
	 */
	public void server_put(final Callable callback ) {
		final BookView that = this;
		Server.post("BookView/put", new String[]{this.get_ptr(), this.bookPtr, this.refText, this.content}, new Callable() {

			@Override
			public void callback(Object d) {
				
				BookView rtn = (BookView) Data.from_json((JSONObject)d);
				if(rtn == null) {
					callback.callback(null);
				}
				else {
					that._thisPtr = rtn.get_ptr();
					that.createTime = rtn.createTime;
					that.authorPtr = rtn.authorPtr;
					callback.callback(this);
				}
				
				
			}
			
		});
	}
	
	/**
	 * list the comment of this book-view
	 * @param callback
	 * 
	 * callback
	 * @param {ListCommentResult}
	 */
	public void server_list_comment(final Callable callback) {
		Server.post("BookView/list_comment", new String[]{this.get_ptr()}, new Callable() {

			@Override
			public void callback(Object d) {
				
				
				JSONObject res = (JSONObject)d;
				JSONArray commentJsonArr = null;
				JSONArray relatedUserJsonArr = null;
				try {
					commentJsonArr = res.getJSONArray("commentArr");
					relatedUserJsonArr = res.getJSONArray("relatedUserArr");
				} catch (JSONException e) {
					Log.e(TAG, "list_comment bad response");
					callback.callback(null);
					return;
				}
				
				ListCommentResult rtn = new ListCommentResult();
				rtn.commentArr = Data.from_json_arr(BookView_Comment.class, commentJsonArr);
				rtn.relatedUserArr = Data.from_json_arr(User.class, relatedUserJsonArr);
				callback.callback(rtn);
				
				
			}
			
		});
	}
	public static class ListCommentResult {
		public BookView_Comment[] commentArr;
		public User[] relatedUserArr;
	}
}
