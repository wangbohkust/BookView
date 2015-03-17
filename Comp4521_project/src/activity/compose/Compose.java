package activity.compose;

import model.User;
import myUtil.Callable;
import hkust.comp4521.project.R;
import activity.bookcomment.bookcomment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Compose extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compose);
		
		
		final EditText orgText = (EditText) findViewById(R.id.editText1);
		
		final EditText bookview = (EditText) findViewById(R.id.composeBookReviewTextEdit);
		
		Button releaseBut = (Button) findViewById(R.id.releaseButton);
		
		final model.Book book = (model.Book)this.getIntent().getExtras().getSerializable("book");
		
		releaseBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				model.BookView new_bookview= new model.BookView();
				new_bookview.authorPtr = User.get_active_user().get_ptr();
				new_bookview.bookPtr = book.get_ptr();
				new_bookview.refText = orgText.getText().toString();
				new_bookview.content = bookview.getText().toString();
				new_bookview.server_put(new Callable() {

					@Override
					public void callback(Object d) {
						// TODO Auto-generated method stub
						//Toast.makeText(getApplicationContext(), "compose successful!", Toast.LENGTH_SHORT).show();
						//Intent in = new Intent(getApplicationContext(),bookcomment.class);
						Compose.this.finish();
					}
					
				});
				
				//get information and submit to server
				
			}
			
		});
		


		
		
	}
		
}
