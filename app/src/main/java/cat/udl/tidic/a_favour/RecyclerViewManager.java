package cat.udl.tidic.a_favour;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cat.udl.tidic.a_favour.Views.ProfileView;

public class RecyclerViewManager extends FragmentPagerAdapter
{

    String tabTitles[] = new String[] { "Favours", "Favourites", "Opinions" };
    Context context;

    public RecyclerViewManager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new BlankFragment();
            case 1:
                return new BlankFragment();
            case 2:
                return new BlankFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public View getTabView(int position) {
        Log.d("himini" ,"asdDDDDDDDDDD");
        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;
    }

}