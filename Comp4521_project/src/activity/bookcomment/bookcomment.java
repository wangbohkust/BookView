package activity.bookcomment;

import hkust.comp4521.project.R;
import activity.compose.Compose;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
 
public class bookcomment extends FragmentActivity  {
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_bookcomment);
    	
        Button compose_b = (Button) findViewById(R.id.compose_button);
		
		
		compose_b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//get information and submit to server
				
				final model.Book book = (model.Book)getIntent().getExtras().getSerializable("book");
				
				Intent intent = new Intent(bookcomment.this, Compose.class);
				intent.putExtra("book", book);
                startActivity(intent);
			}
			
		});
    }
}