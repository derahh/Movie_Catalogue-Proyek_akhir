package id.co.derahh.moviecatalogue.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import id.co.derahh.moviecatalogue.R;

public class UserPreferenceFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String keyReleaseReminder, keyDailyReminder;

    private SwitchPreference spReleaseReminder, spDailyReminder;

    private DailyReminderReceiver dailyReceiver = new DailyReminderReceiver();
    private ReleaseTodayReminderReceiver releaseTodayReminderReceiver = new ReleaseTodayReminderReceiver();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference_user);
        init();
        setSummaries();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equalsIgnoreCase(keyDailyReminder)) {
            boolean isDailryReminderChecked = sharedPreferences.getBoolean(keyDailyReminder, false);
            if (isDailryReminderChecked) {
                dailyReceiver.setRepeatingAlarm(getActivity());
            } else {
                dailyReceiver.cancelAlarm(getActivity());
            }
        }

        if (key.equalsIgnoreCase(keyReleaseReminder)) {
            boolean isTodayRelaseReminder = sharedPreferences.getBoolean(keyDailyReminder, false);
            if (isTodayRelaseReminder) {
                releaseTodayReminderReceiver.setRepeatingAlarm(getActivity());
            } else {
                releaseTodayReminderReceiver.cancelAlarm(getActivity());
            }
        }
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        boolean isDailryReminderChecked = sh.getBoolean(keyDailyReminder, false);
        if (isDailryReminderChecked) {
            dailyReceiver.setRepeatingAlarm(getActivity());
        } else {
            dailyReceiver.cancelAlarm(getActivity());
        }

        boolean isTodayRelaseReminder = sh.getBoolean(keyDailyReminder, false);
        if (isTodayRelaseReminder) {
            releaseTodayReminderReceiver.setRepeatingAlarm(getActivity());
        } else {
            releaseTodayReminderReceiver.cancelAlarm(getActivity());
        }
    }

    private void init() {
        keyDailyReminder = getResources().getString(R.string.key_daily_reminder);
        keyReleaseReminder = getResources().getString(R.string.key_release_reminder);

        spReleaseReminder = (SwitchPreference) findPreference(keyReleaseReminder);
        spDailyReminder = (SwitchPreference) findPreference(keyDailyReminder);
    }
}
