package activity.book;

import java.util.ArrayList;

import model.Book;
import hkust.comp4521.project.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookAdaptor extends ArrayAdapter<Book> {

	private final Context context;
	private final ArrayList<String> author;
	private final ArrayList<String> bookid;
	private final ArrayList<String> bookname;


	public BookAdaptor(Context context, Book[] booklist) {
		super( context, R.layout.fragment_book, booklist);
		//extract information
		this.context= context;
		
		this.author = new ArrayList<String>();
     	this.bookid = new ArrayList<String>();
		this.bookname = new ArrayList<String>();
		
		for(int i = 0; i < booklist.length;++i) {
			
			 
			author.add(booklist[i].author);
			bookid.add(booklist[i].ISBN);
			bookname.add(booklist[i].name);
		}
		
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.fragment_book, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.book_bookid);
		TextView textView2 = (TextView) rowView.findViewById(R.id.book_author);
		TextView textView3 = (TextView) rowView.findViewById(R.id.book_bookname);
		if(bookid.get(position).length()>40){
		    textView.setText(bookid.get(position).substring(0, 40));
		}
		else{
			textView.setText(bookid.get(position));
		}
		if(author.get(position).length()>40){
		    textView2.setText(author.get(position).substring(0, 40));
		}
		else{
			textView2.setText(author.get(position));
		}
		if(bookname.get(position).length()>40){
		    textView3.setText(bookname.get(position).substring(0, 40));
		}
		else{
			textView3.setText(bookname.get(position));
		}
		
		return rowView;
	}

}

