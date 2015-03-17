package activity.tabLayout;

import java.util.List;

import hkust.comp4521.project.R;
import activity.bookview.BookView_One;
import activity.compose.Compose;
import activity.fresh.FreshPage;
import activity.search.Search;
import activity.setting.Setting;
import activity.social.ListBookBuddyActivity;
import activity.social.Social;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TabLayout extends FragmentActivity implements
		ActionBar.TabListener, SearchView.OnQueryTextListener{
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private SearchView mSearchView;
	private TextView mStatusView; //for test
	
	// Tab titles
	private String[] tabs = { "Fresh", "Home", "Pop" };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_layout);

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//for test
		mStatusView = (TextView) findViewById(R.id.status_text);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.whole_page, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		
		mStatusView = (TextView) findViewById(R.id.status_text);
	
		mSearchView = (SearchView) searchItem.getActionView();
		if(mSearchView == null)
			Toast.makeText(getApplicationContext(), "searchView is null",
					Toast.LENGTH_LONG).show();
		
		setupSearchView(searchItem);
		
		return true;
	}

	private void setupSearchView(MenuItem searchItem) {
		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		
		//String pkg = "activity.search";
		//String cls = "activity.search.SearchActivity";
	    //ComponentName mycomponent = new ComponentName(pkg,cls);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchableInfo infomation = searchManager.getSearchableInfo(getComponentName());
		if(infomation!=null){
			mSearchView.setSearchableInfo(infomation);
			mSearchView.setOnQueryTextListener(this);
		}
		else 
			mStatusView.setText("Cannot find the class to for search function");
	

        

	}

	public boolean onQueryTextChange(String newText) {
		mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		mStatusView.setText("Query = " + query + " : submitted");
		return false;
	}

	public boolean onClose() {
		mStatusView.setText("Closed!");
		return false;
	}

	private boolean isAlwaysExpanded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_search:
			Toast.makeText(getApplicationContext(), "searchView",
					Toast.LENGTH_LONG).show();
			//new Search();
			return true;
		case R.id.action_circle:
			Intent intent = new Intent(this, Social.class);
			this.startActivity(intent);
			return true;
		case R.id.action_settings:
			Intent intent1 = new Intent(this, Setting.class);
		    this.startActivity(intent1);
			return true;
			
		/*case R.id.action_compose:
			Intent intent2 = new Intent(this, Compose.class);
			this.startActivity(intent2);
			return true;*/
			
		
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}
