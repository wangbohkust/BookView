package activity.social;

import java.util.Observable;

import model.User;
import myUtil.Callable;

public class AddBookBuddy_Ctrl extends Observable{

	public final static String TAG = "AddBookBuddy_Ctrl";
	public final static class State {
		public final static int loading = 1;
		public final static int ready = 2;
		public final static int success = 3;
		public final static int fail = 4;
	}
	
	public model.User[] followingArr;
	
	public AddBookBuddy_Ctrl() {
		
	}
	
	public void add(String email) {
		this.setChanged();
		this.notifyObservers(AddBookBuddy_Ctrl.State.loading);
		User.server_add_following(email, new Callable() {

			@Override
			public void callback(Object d) {
				
				if(d == null) {
					setChanged();
					notifyObservers(State.fail);
					return;
				}
				setChanged();
				notifyObservers(State.success);
				return;

			}
			
		});
		
		
	}
	
	
}