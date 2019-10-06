package id.co.derahh.moviecatalogue.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.fragment.UserPreferenceFragment;

public class UserPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);

        getSupportFragmentManager().beginTransaction().add(R.id.setting_preference, new UserPreferenceFragment()).commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.reminder_setting);
        }
    }
}
