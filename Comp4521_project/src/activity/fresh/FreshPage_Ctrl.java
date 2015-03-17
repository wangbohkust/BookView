package activity.fresh;

import java.util.Observable;

import android.util.Log;

import model.User;
import myUtil.Callable;

public class FreshPage_Ctrl extends Observable
{
	public final static String TAG = "FreshPage_Ctrl";
	public final static class State {
		public final static int loading = 1;
		public final static int ready = 2;
	}
	
	public model.BookView[] bookViewArr = null;
	public model.Book[] relatedBookArr = null;
	public model.User[] relatedUserArr = null;

	public FreshPage_Ctrl() {
		
	}

	public void refresh() {
		this.setChanged();
		this.notifyObservers(FreshPage_Ctrl.State.loading);
		User.server_get_fresh(5, 0, new Callable() {

			@Override
			public void callback(Object d) {
				User.GetFreshResult res = (User.GetFreshResult)d;
				FreshPage_Ctrl.this.bookViewArr = res.bookViewArr;
				FreshPage_Ctrl.this.relatedBookArr = res.relatedBookArr;
				FreshPage_Ctrl.this.relatedUserArr = res.relatedUserArr;
				FreshPage_Ctrl.this.setChanged();
				FreshPage_Ctrl.this.notifyObservers(FreshPage_Ctrl.State.ready);
			}
			
		});
	}
	
	
	
}
