package activity.MainActivity;

import java.util.Observable;

import model.User;
import myUtil.Callable;
import myUtil.Server;

/**
 * a controller for the login activity
 * @author cirah
 *
 */
public class MainActivityController extends Observable
{
	public static final int STATE_DEFAULT = 0;
	public static final int STATE_LOGIN_SUCCESS = 1;
	public static final int STATE_LOGIN_FAIL = 2;
	public static final int STATE_LOGGING_IN = 3; // waiting for server response
	/**
	 * perform login action
	 * 
	 * @param email
	 * @param password
	 */
	public void login(String email,String password) {
		this.setChanged();
		this.notifyObservers(STATE_LOGGING_IN);
		User.server_login(email, password, new Callable()
		{
			@Override
			public void callback(final Object d) {
				if(d == null) {
					setChanged();
					notifyObservers(STATE_LOGIN_FAIL);
					return;
				}
				setChanged();
				
				// do some debug action after logged in
				Server.server_do_debug();
				
				notifyObservers(STATE_LOGIN_SUCCESS);
			}

		});
	}
}
