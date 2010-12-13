package com.isitchristmas.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

public class ChristmasUtils {
	public static final String TAG = "IsItChristmas";

	public static boolean alarmsInitialized(Context context) {
		return getBooleanPreference(context, "init_alarms", false);
	}
	
	public static void initializeAlarms(Context context) {
		setChristmasAlarm(context);
		//TODO: setRecurringChristmasAlarm(context);
		setBooleanPreference(context, "init_alarms", true);
	}
	
	public static void setChristmasAlarm(Context context) {
		long time = Christmas.time();
		
		// debug, 3 seconds from execution
		// time = System.currentTimeMillis() + 3000;
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, ChristmasNotificationReceiver.class), 0);
	    manager.cancel(alarmIntent);
	    manager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
	}
	
	public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
	}
	
	public static boolean setBooleanPreference(Context context, String key, boolean value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).commit();
	}
}