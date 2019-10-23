package id.co.derahh.moviecatalogue.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;

import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.activity.UserPreferenceActivity;
import id.co.derahh.moviecatalogue.adapter.CategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    CategoryAdapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        super.onViewCreated(view, savedInstanceState);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        viewPager = view.findViewById(R.id.view_pager);
        adapter = new CategoryAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapter);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.category_favorite);
        }

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_preference_settings) {
            Intent mIntent = new Intent(getContext(), UserPreferenceActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
