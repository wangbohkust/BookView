package model;

import myUtil.Callable;
import myUtil.Server;

import org.json.JSONObject;

public class BookView_Comment extends Data
{
	public static final String TAG = "BookView_Comment";
	public String bookViewPtr = "";
	public String authorPtr = "";
	public String content = "";
	public long createTime = 0;
	public void server_put(final Callable callback ) {
		final BookView_Comment that = this;
		Server.post("BookView/Comment/put", new String[]{this.get_ptr(), this.bookViewPtr,  this.content}, new Callable() {

			@Override
			public void callback(Object d) {
				
				BookView_Comment rtn = (BookView_Comment) Data.from_json((JSONObject)d);
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
}
