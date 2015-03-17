package activity.social;

import java.util.Observable;

import model.User;
import myUtil.Callable;
import activity.fresh.FreshPage_Ctrl;
import android.util.Log;

public class ListBookBuddy_Ctrl extends Observable{

	public final static String TAG = "ListBookBuddy_Ctrl";
	public final static class State {
		public final static int loading = 1;
		public final static int ready = 2;
	}
	
	public model.User[] followingArr;
	
	public ListBookBuddy_Ctrl() {
		
	}
	
	public void refresh() {
		this.setChanged();
		this.notifyObservers(ListBookBuddy_Ctrl.State.loading);
		User.server_list_following(new Callable() {

			@Override
			public void callback(Object d) {
				
				
				
				// TODO Auto-generated method stub
				followingArr = (User[]) d;
				
				
				setChanged();
				notifyObservers(ListBookBuddy_Ctrl.State.ready);

			}
			
		});
		
		
	}
	
	
}
