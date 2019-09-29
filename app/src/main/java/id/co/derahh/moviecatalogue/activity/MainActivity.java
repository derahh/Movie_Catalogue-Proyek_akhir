package id.co.derahh.moviecatalogue.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import id.co.derahh.moviecatalogue.adapter.CategoryAdapter;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.fragment.FavoriteFragment;
import id.co.derahh.moviecatalogue.fragment.MovieFragment;
import id.co.derahh.moviecatalogue.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOAD_FRAGMENT_MAIN_MENU = "load_fragment_main_menu";
    public static final String KEY_FRAGMENT = "fragment";

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment = new MovieFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_movie:
                        fragment = new MovieFragment();
                        break;
                    case R.id.menu_tv_show:
                        fragment = new TvShowFragment();
                        break;
                    case R.id.menu_favorite:
                        fragment = new FavoriteFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
                return true;
            }
        });

        if (savedInstanceState == null) getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
        else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
        }
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        Intent intent = getIntent();
//        int intentFragment = intent.getIntExtra(LOAD_FRAGMENT_MAIN_MENU, value);
//        if (intentFragment != -1) {
//            loadFragmentFromIntent(intentFragment);
//        } else {
//            loadFragment(new TvShowFragment());
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }
}
