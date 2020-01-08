package com.derahh.moviecatalogueretrofit.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.derahh.moviecatalogueretrofit.R;
import com.derahh.moviecatalogueretrofit.fragment.MovieFragment;


public class CategoryAdapter extends FragmentPagerAdapter {

    private String[] titleTab;

    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        titleTab = context.getResources().getStringArray(R.array.data_title_tab);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) return new MovieFragment();
        else return  new MovieFragment();
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
