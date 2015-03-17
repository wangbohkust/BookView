package activity.tabLayout;

import activity.bookview.BookView;
import activity.fresh.FreshPage;
import activity.home.HomePage;
import activity.popular.PopPage;
import activity.search.Search;
import activity.social.Social;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 

	@Override
    public Fragment getItem(int index) {
	 
        switch (index) {
        case 0:
            return new FreshPage();
        case 1:
            return new HomePage();
        case 2:
            return new PopPage();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}