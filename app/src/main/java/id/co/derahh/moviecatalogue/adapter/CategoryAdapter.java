package id.co.derahh.moviecatalogue.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.co.derahh.moviecatalogue.fragment.MovieFavoriteFragment;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.fragment.TvShowFavoriteFragment;


public class CategoryAdapter extends FragmentPagerAdapter {

    private String[] titleTab;

    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        titleTab = context.getResources().getStringArray(R.array.data_title_tab);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) return new MovieFavoriteFragment();
        else return  new TvShowFavoriteFragment();
    }

    @Override
    public int getCount() {
        return titleTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleTab[position];
    }
}
