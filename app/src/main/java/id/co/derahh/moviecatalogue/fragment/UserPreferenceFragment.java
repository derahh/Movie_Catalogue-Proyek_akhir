package id.co.derahh.moviecatalogue.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import java.util.Objects;

import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.receiver.DailyReminderReceiver;
import id.co.derahh.moviecatalogue.receiver.ReleaseTodayReminderReceiver;

public class UserPreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private static String keyReleaseReminder, keyDailyReminder;

    private DailyReminderReceiver dailyReceiver = new DailyReminderReceiver();
    private ReleaseTodayReminderReceiver releaseTodayReminderReceiver = new ReleaseTodayReminderReceiver();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference_user);
        init();
    }

    private void init() {
        keyDailyReminder = getResources().getString(R.string.key_daily_reminder);
        keyReleaseReminder = getResources().getString(R.string.key_release_reminder);

        SwitchPreference spReleaseReminder = (SwitchPreference) findPreference(keyReleaseReminder);
        spReleaseReminder.setOnPreferenceChangeListener(this);

        SwitchPreference spDailyReminder = (SwitchPreference) findPreference(keyDailyReminder);
        spDailyReminder.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key =preference.getKey();
        boolean state = (boolean) newValue;

        if (key.equals(keyDailyReminder)) {
            if (state) {
                dailyReceiver.setRepeatingAlarm(getActivity(), "07:00");
                Toast.makeText(getActivity(), "Daily Reminder setup", Toast.LENGTH_SHORT).show();
            } else {
                dailyReceiver.cancelAlarm(Objects.requireNonNull(getActivity()));
                Toast.makeText(getActivity(), "Cancel Daily Reminder", Toast.LENGTH_SHORT).show();
            }
        } else if (key.equals(keyReleaseReminder)) {
            if (state) {
                releaseTodayReminderReceiver.setRepeatingAlarm(getActivity(), "08:00");
                Toast.makeText(getActivity(), "Release Reminder setup", Toast.LENGTH_SHORT).show();
            } else {
                releaseTodayReminderReceiver.cancelAlarm(Objects.requireNonNull(getActivity()));
                Toast.makeText(getActivity(), "Cancel Release Reminder", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
