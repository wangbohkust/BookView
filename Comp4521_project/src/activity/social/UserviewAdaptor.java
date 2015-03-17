package activity.social;

import hkust.comp4521.project.R;

import java.util.ArrayList;

import activity.bookview.BookView_One;
import activity.fresh.FreshPage;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import mockData.MockData;
import model.User;
import myUtil.Callable;
import android.os.Handler;
import android.provider.ContactsContract.Contacts.Data;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserviewAdaptor extends ArrayAdapter<User> {
	
	private final Context context;
	private final ArrayList<String> email;
	private final ArrayList<String> username;
	//private final ArrayList<String> imgURL;
	private final ArrayList<Drawable> portrait;
	//private final User user;
	private User[] following;
	Handler handler = new Handler();
	


	public UserviewAdaptor(Context context, User[] relatedUserArr) {
		
		super(context,R.layout.followingview,relatedUserArr);
		
		this.context = context;
		
		
		
		//this.user = User.get_active_user();
		this.email = new ArrayList<String>();
		this.username = new ArrayList<String>();
		//this.imgURL= new ArrayList<String>();
		this.portrait = new ArrayList<Drawable>();
		//Toast.makeText(getContext(),"constructor of Userview Adaptor", Toast.LENGTH_LONG).show();
		
		
		//Test with mock data
		
		following = relatedUserArr;
		
		
		
		for(int i = 0; i < following.length;++i) {
			email.add(following[i].email);
			username.add(following[i].name);
			
			
			if(following[i].get_image() !=null) {
				BitmapDrawable d = new BitmapDrawable(context.getResources(), following[i].get_image());
				portrait.add(d);
			}
			else {
				portrait.add(context.getResources().getDrawable(R.drawable.testpor1));
			}
		}
	}
	
	@Override 
	public View getView(final int position, View convertView, ViewGroup parent) {
	
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.followingview, parent,
				false);
		
		final Button followButton = (Button) rowView.findViewById(R.id.editFollowingButton);
		
		
		/*followButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					followButton.setText("Unfollow");
				}
				return false;
			}
			
		});*/
		
		/*followButton.setOnHoverListener(new OnHoverListener() {

			@Override
			public boolean onHover(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "hover event", Toast.LENGTH_LONG);
				if(event.getAction() == MotionEvent.ACTION_HOVER_ENTER) {
					followButton.setText("unfollow" + username.get(position));
				}
				return false;
			}
			
		});*/
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.editFollowingImage);
		//here to add listener to go to homepage of other users
		TextView emailName = (TextView) rowView.findViewById(R.id.editFollowinggEmailName);
		Button deleteButton = (Button) rowView.findViewById(R.id.edutFollowingDeleteButton);
		deleteButton.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
		       //delete the user from the server
		    	User.server_remove_following(following[position].get_ptr(), new Callable() {

		    		 @Override
						public void callback(final Object d) {
							handler.post(new Runnable() {

								@Override
								public void run() {
									User user = (User) d;
									Toast.makeText(context, "Successful delete the following " +user.name, Toast.LENGTH_SHORT).show();
									
								}
								
							});
						}
		    	
		    	});
		    	
		    	//refresh the GUI
		    	followButton.setText("Follow  " + username.get(position));
		    	
		    }
		});
		//TextView  userName = (TextView) rowView.findViewById(R.id.editFollowinggUserName);
		//textView2.setText(originalText.get(position));
		
		emailName.setText(email.get(position));
		//userName.setText(username.get(position));
		imageView.setImageDrawable(portrait.get(position));
		followButton.setText("Following " + username.get(position));
		
	
		
		return rowView;
	}
	

}
