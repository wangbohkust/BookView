package activity.home;

import java.util.Observable;

import android.util.Log;

import model.User;
import myUtil.Callable;

public class HomePage_Ctrl extends Observable
{
	public final static String TAG = "HomePage_Ctrl";
	public final static class State {
		public final static int loading = 1;
		public final static int ready = 2;
	}
	
	public model.BookView[] bookViewArr = null;
	public model.Book[] relatedBookArr = null;
	public model.User[] relatedUserArr = null;

	public HomePage_Ctrl() {
		
	}

	public void refresh() {
		this.setChanged();
		this.notifyObservers(HomePage_Ctrl.State.loading);
		User.server_get_home(new Callable() {

			@Override
			public void callback(Object d) {
				User.GetFreshResult res = (User.GetFreshResult)d;
				HomePage_Ctrl.this.bookViewArr = res.bookViewArr;
				HomePage_Ctrl.this.relatedBookArr = res.relatedBookArr;
				HomePage_Ctrl.this.relatedUserArr = res.relatedUserArr;
				HomePage_Ctrl.this.setChanged();
				HomePage_Ctrl.this.notifyObservers(HomePage_Ctrl.State.ready);
			}
			
		});
	}
	
	
	
}
