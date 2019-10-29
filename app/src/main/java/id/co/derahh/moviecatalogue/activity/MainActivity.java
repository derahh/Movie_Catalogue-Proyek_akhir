package id.co.derahh.moviecatalogue.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.fragment.FavoriteFragment;
import id.co.derahh.moviecatalogue.fragment.MovieFragment;
import id.co.derahh.moviecatalogue.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_FRAGMENT = "fragment";

    private Fragment fragment = new MovieFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
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
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }
}
