package activity.MainActivity;

import hkust.comp4521.project.R;

import java.util.Observable;
import java.util.Observer;


import model.User;
import myUtil.*;
import activity.tabLayout.TabLayout;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Observer
{

	
	private EditText emailInput = null;
	private Button btnLogin = null;
	private EditText passwordInput = null;
	private MainActivityController controller = new MainActivityController();
	private Handler handler = new Handler();
	public boolean loginResult = false; // for junit test

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.controller.addObserver(this);
		
		emailInput = (EditText) this.findViewById(R.id.myUsername);
		passwordInput = (EditText) this.findViewById(R.id.myPassword);
		
		
		// bind events
		btnLogin = (Button) this.findViewById(R.id.loginBut);
		btnLogin.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				login();

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
	public void login() {
		this.controller.login(this.emailInput.getText().toString(), this.passwordInput.getText().toString());
		
	}

	@Override
	public void update(Observable observable, final Object data) {
		this.handler.post(new Runnable() {

			@Override
			public void run() {
				update_sync(data);
				
			}
			
		});
		
	}
	private void update_sync(Object data) {
		switch((Integer) data) {
		case MainActivityController.STATE_LOGIN_SUCCESS:
			this.loginResult = true;
			Toast.makeText(this, "Welcome, " + User.get_active_user().email, Toast.LENGTH_SHORT).show();
			Intent in = new Intent(this, TabLayout.class);
			this.startActivity(in);
			break;
		case MainActivityController.STATE_LOGIN_FAIL:
			this.loginResult = false;
			Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
