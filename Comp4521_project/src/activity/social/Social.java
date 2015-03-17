package activity.social;

import hkust.comp4521.project.R;
import activity.bookview.BookView_One;
import activity.fresh.FreshPage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
 
public class Social extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_social);
		
		 Button following = (Button) findViewById(R.id.followingButton);
		 
		 following.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent in = new Intent(getApplicationContext(), ListBookBuddyActivity.class);
	        		Social.this.startActivity(in);
	               
	            }
	        });
	       
	        
	        
	        Button toFollow = (Button) findViewById(R.id.toFollowButton);
	        
	        toFollow.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	Intent in = new Intent(getApplicationContext(), AddBookBuddyActivity.class);
	        		Social.this.startActivity(in);
	                
	            }
	        });
	}

}