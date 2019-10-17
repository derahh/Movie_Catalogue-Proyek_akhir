package id.co.derahh.moviecatalogue.fragment;

import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.receiver.DailyReminderReceiver;
import id.co.derahh.moviecatalogue.receiver.ReleaseTodayReminderReceiver;

public class UserPreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    public static String keyReleaseReminder, keyDailyReminder;

    private SwitchPreference spReleaseReminder, spDailyReminder;

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

        spReleaseReminder = (SwitchPreference) findPreference(keyReleaseReminder);
        spReleaseReminder.setOnPreferenceChangeListener(this);

        spDailyReminder = (SwitchPreference) findPreference(keyDailyReminder);
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
                dailyReceiver.cancelAlarm(getActivity());
                Toast.makeText(getActivity(), "Cancel Daily Reminder", Toast.LENGTH_SHORT).show();
            }
        } else if (key.equals(keyReleaseReminder)) {
            if (state) {
                releaseTodayReminderReceiver.setRepeatingAlarm(getActivity(), "22:42");
                Toast.makeText(getActivity(), "Release Reminder setup", Toast.LENGTH_SHORT).show();
            } else {
                releaseTodayReminderReceiver.cancelAlarm(getActivity());
                Toast.makeText(getActivity(), "Cancel Release Reminder", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
