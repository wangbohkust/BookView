package activity.popular;

import java.util.Observable;

import android.util.Log;

import model.User;
import myUtil.Callable;

public class PopPage_Ctrl extends Observable
{
	public final static String TAG = "PopPage_Ctrl";
	public final static class State {
		public final static int loading = 1;
		public final static int ready = 2;
	}
	
	public model.BookView[] bookViewArr = null;
	public model.Book[] relatedBookArr = null;
	public model.User[] relatedUserArr = null;

	public PopPage_Ctrl() {
		
	}

	public void refresh() {
		this.setChanged();
		this.notifyObservers(PopPage_Ctrl.State.loading);
		User.server_get_pop(new Callable() {

			@Override
			public void callback(Object d) {
				User.GetFreshResult res = (User.GetFreshResult)d;
				PopPage_Ctrl.this.bookViewArr = res.bookViewArr;
				PopPage_Ctrl.this.relatedBookArr = res.relatedBookArr;
				PopPage_Ctrl.this.relatedUserArr = res.relatedUserArr;
				PopPage_Ctrl.this.setChanged();
				PopPage_Ctrl.this.notifyObservers(PopPage_Ctrl.State.ready);
			}
			
		});
	}
	
	
	
}
