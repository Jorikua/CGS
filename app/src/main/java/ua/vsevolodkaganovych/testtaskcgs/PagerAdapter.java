package ua.vsevolodkaganovych.testtaskcgs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;


public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FragmentSearch();
            case 1: return new FragmentFavorites();
        }
        return new FragmentSearch();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0: return mContext.getString(R.string.search).toUpperCase(l);
            case 1: return mContext.getString(R.string.favorites).toUpperCase(l);
        }
        return null;
    }
}
