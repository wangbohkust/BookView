package activity.setting;

import model.User;
import myUtil.Callable;
import hkust.comp4521.project.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity {
	ImageView imageView;
	TextView  email;
	Button username;
	Button password;
	User user;
	AlertDialog.Builder builder;
	Handler handler = new Handler();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsetting);
		
		user = User.get_active_user();
		
		
		imageView = (ImageView) this.findViewById(R.id.portraitImage);
		Drawable d = new BitmapDrawable(getResources(),user.get_image());
		imageView.setImageDrawable(d);
		
		email = (TextView) this.findViewById(R.id.settingLoginEmail);
		email.setText(user.email);
		
		username = (Button) this.findViewById(R.id.settingUsername);
		String userName = user.name;
		if(userName != null) {
			username.setText(userName);
		}
		
		//builder = new AlertDialog.Builder(this);//for popping out dialogue
		
		username.setOnClickListener(new View.OnClickListener() {
			
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// Add the buttons
				//Log.i("button","click on button");
				builder = new AlertDialog.Builder(Setting.this);
				//builder.setView(findViewById(R.layout.setusername));
				builder.setTitle("Setting username");
				final EditText myText = new EditText(Setting.this);
				builder.setView(myText);
				
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User clicked OK button
				        	   user.name = myText.getText().toString();
				        	  
				        	   
				        	    user.server_put(new Callable() {

									@Override
									public void callback(Object d) {
										handler.post(new Runnable() {

											@Override
											public void run() {

												Toast.makeText(getApplicationContext(), "Successful change your username", Toast.LENGTH_SHORT).show();
												username.setText(user.name);
											}
											
										});
									}
				        	    	
				        	    });
				           }
				       });
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User cancelled the dialog
				           }
				       });
				// Set other dialog properties
				
		

				// Create the AlertDialog
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
		});
		
		password = (Button) this.findViewById(R.id.settingPassword);
		
		password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// Add the buttons
				builder = new AlertDialog.Builder(Setting.this);
				builder.setTitle("Setting password");
			
				
				View setPasswordView = View.inflate(getApplicationContext(), R.layout.setpassword, null);
				builder.setView(setPasswordView);
				
				final EditText oldPassword  = (EditText) setPasswordView.findViewById(R.id.oldpassword);
				final EditText newPassword = (EditText) setPasswordView.findViewById(R.id.newpassword);
				
				
				
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User clicked OK button
				        	   
				        	   User.set_password(oldPassword.getText().toString(), newPassword.getText().toString(), new Callable() {
				        		   @Override
									public void callback(final Object d) {
										handler.post(new Runnable() {

											@Override
											public void run() {
												if((Boolean) d == true)
													Toast.makeText(getApplicationContext(), "Successful change your password", Toast.LENGTH_SHORT).show();
												else 
													Toast.makeText(getApplicationContext(), "UnSuccessful change your password", Toast.LENGTH_SHORT).show();
											}
											
										});
									}
				        	   });
				        	   
				        	 
				        	    
				           }
				       });
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User cancelled the dialog
				           }
				       });
				// Set other dialog properties
				
		

				// Create the AlertDialog
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
		});
		
		
		
		
		
	}
	
}
