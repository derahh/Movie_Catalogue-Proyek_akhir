package com.derahh.moviecatalogueretrofit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.derahh.moviecatalogueretrofit.adapter.CategoryAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager_favorite);
        adapter = new CategoryAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_favorite);
        tabLayout.setupWithViewPager(viewPager);
    }
}
