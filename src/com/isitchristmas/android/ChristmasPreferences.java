package com.isitchristmas.android;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class ChristmasPreferences extends PreferenceActivity {
	Preference notificationDelay;
	
	public static final String SINGLE_ENABLED_KEY = "notify_single_enable";
	public static final boolean SINGLE_ENABLED_DEFAULT = true;
	
//	public static final String RECURRING_ENABLED_KEY = "notify_recurring_enable";
//	public static final boolean RECURRING_ENABLED_DEFAULT = true;
	
//	public static final String RECURRING_INTERVAL_KEY = "notify_recurring_interval";
//	public static final String DEFAULT_RECURRING_INTERVAL = "86400000"; // daily
	
	public static final String VIBRATE_KEY = "notify_vibrate";
	public static final boolean VIBRATE_DEFAULT = true;
	
	public static final String RINGTONE_KEY = "notify_ringtone";
	public static final String RINGTONE_DEFAULT = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings);
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		
		setupControls();
	}
		
	public void setupControls() {
//		notificationDelay = findPreference("notification_delay");
		
		// initially set the summary of the notification delay preference
//		String delay = PreferenceManager.getDefaultSharedPreferences(this).getString("notification_delay", DEFAULT_INTERVAL);
//		if (delay != null)
//			notificationDelay.setSummary(Utils.codeToName(this, delay));
		
		// schedule/cancel single christmas alarm based on whether preference is checked
		CheckBoxPreference notifications = (CheckBoxPreference) findPreference(SINGLE_ENABLED_KEY);
		notifications.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				boolean value = ((Boolean) newValue).booleanValue();
				if (value) {
					ChristmasUtils.setChristmasAlarm(ChristmasPreferences.this);
					Log.d(ChristmasUtils.TAG, "Scheduled single Christmas alarm");
				} else {
					ChristmasUtils.cancelChristmasAlarm(ChristmasPreferences.this);
					Log.d(ChristmasUtils.TAG, "Canceled single Christmas alarm");
				}
				return true;
			}
		});
		
		findPreference(RINGTONE_KEY).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				updateRingtoneSummary((String) newValue);
				return true;
			}
		});
		
		// keep the summary of the interval preference up to date
//		notificationDelay.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//			@Override
//			public boolean onPreferenceChange(Preference preference, Object newValue) {
//				notificationDelay.setSummary(Utils.codeToName(ChristmasPreferences.this, (String) newValue));
//				return true;
//			}
//		});
		
		
	}
	
	private void updateRingtoneSummary(String uri) {
		String summary;
		
		if (uri != null && !uri.equals(""))
			summary = RingtoneManager.getRingtone(this, Uri.parse(uri)).getTitle(this);
		else
			summary = "Silent";
		
		findPreference(RINGTONE_KEY).setSummary(summary);
	}
	
}