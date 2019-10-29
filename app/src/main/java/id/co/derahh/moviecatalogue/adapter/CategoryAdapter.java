package id.co.derahh.moviecatalogue.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import id.co.derahh.moviecatalogue.fragment.MovieFavoriteFragment;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.fragment.TvShowFavoriteFragment;


public class CategoryAdapter extends FragmentPagerAdapter {

    private String[] titleTab;

    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        titleTab = context.getResources().getStringArray(R.array.data_title_tab);
    }

    @NotNull
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
