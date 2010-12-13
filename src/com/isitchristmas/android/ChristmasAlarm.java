package com.isitchristmas.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;

public class ChristmasAlarm {
	
	public static void setEnabledAlarms(Context context) {
		if (singleEnabled(context))
			setChristmasAlarm(context);
		
		if (recurringEnabled(context))
			setRecurringAlarm(context);
	}
	
	public static void setChristmasAlarm(Context context) {
		long time = Christmas.time();
		
		// debug
		// time = System.currentTimeMillis() + 5000;
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = singleAlarmIntent(context);
	    manager.cancel(alarmIntent);
	    
	    manager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
	    
	    Log.d(Christmas.TAG, "Scheduled single Christmas alarm for " + formatTime(time));
	}
	
	public static void cancelChristmasAlarm(Context context) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = singleAlarmIntent(context);
	    manager.cancel(alarmIntent);
	    
	    Log.d(Christmas.TAG, "Canceled single Christmas alarm");
	}
	
	public static void setRecurringAlarm(Context context) {
		setRecurringAlarm(context, getInterval(context));
	}
	
	public static void setRecurringAlarm(Context context, String intervalValue) {
		long time = firstRecurringTime(intervalValue);
		long interval = getIntervalMillis(intervalValue);
		
		if (time < 0 || interval < 0) {
			Log.i(Christmas.TAG, "Invalid value for recurring notification interval, no recurring notifications were scheduled");
			return;
		}
		
		// debug
//		time = System.currentTimeMillis() + 2000;
//		interval = 10000;
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = recurringAlarmIntent(context);
		manager.cancel(alarmIntent);
		
		manager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, alarmIntent);
		
		Log.d(Christmas.TAG, "Scheduled recurring Christmas alarm for " + formatTime(time));
	}
	
	public static void cancelRecurringAlarm(Context context) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = recurringAlarmIntent(context);
		manager.cancel(alarmIntent);
		
		Log.d(Christmas.TAG, "Canceled recurring Christmas alarm");
	}
	
	// helper methods
	
	private static PendingIntent singleAlarmIntent(Context context) {
		Intent intent = new Intent(context, ChristmasNotificationReceiver.class);
		intent.setData(Uri.parse("christmas://single"));
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
	
	private static PendingIntent recurringAlarmIntent(Context context) {
		Intent intent = new Intent(context, ChristmasNotificationReceiver.class);
		intent.setData(Uri.parse("christmas://multiple"));
		return PendingIntent.getBroadcast(context, 0, intent, 0);
	}
	
	private static long firstRecurringTime(String interval) {
		if (interval.equals("daily"))
			return nextNoon();
		else if (interval.equals("fifteen"))
			return System.currentTimeMillis() + (15 * 60 * 1000);
		else
			return -1;
	}
	
	private static String getInterval(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(ChristmasPreferences.RECURRING_INTERVAL_KEY, ChristmasPreferences.RECURRING_INTERVAL_DEFAULT);
	}
	
	private static long getIntervalMillis(String interval) {
		if (interval.equals("daily"))
			return (24 * 60 * 60 * 1000);
		else if (interval.equals("fifteen"))
			return (15 * 60 * 1000);
		else
			return -1;
	}
	
	private static long nextNoon() {
		Time now = new Time();
		now.setToNow();
		
		Time noon = new Time();
		
		if (now.hour >= 12)
			noon.set(0, 0, 12, now.monthDay + 1, now.month, now.year);
		else
			noon.set(0, 0, 12, now.monthDay    , now.month, now.year);
		
		return noon.toMillis(false);
	}
	
	private static boolean singleEnabled(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(ChristmasPreferences.SINGLE_ENABLED_KEY, ChristmasPreferences.SINGLE_ENABLED_DEFAULT);
	}
	
	private static boolean recurringEnabled(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(ChristmasPreferences.RECURRING_ENABLED_KEY, ChristmasPreferences.RECURRING_ENABLED_DEFAULT);
	}
	
	private static String formatTime(long time) {
		return time + " (" + DateFormat.format("MM-dd-yyyy hh:mm:ss", time) + ")";
	}
}