package com.isitchristmas.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

public class ChristmasAlarm {
	
	public static void setAlarms(Context context) {
		setChristmasAlarm(context);
		setRecurringAlarm(context);
	}
	
	public static long setChristmasAlarm(Context context) {
		long time = Christmas.time();
		
		// debug, 3 seconds from execution
		// time = System.currentTimeMillis() + 1000;
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = singleAlarmIntent(context);
	    manager.cancel(alarmIntent);
	    
	    manager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
	    
	    return time;
	}
	
	public static void cancelChristmasAlarm(Context context) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = singleAlarmIntent(context);
	    manager.cancel(alarmIntent);
	}
	
	public static long setRecurringAlarm(Context context) {
		return setRecurringAlarm(context, getInterval(context));
	}
	
	public static long setRecurringAlarm(Context context, String intervalValue) {
		long time = firstRecurringTime(intervalValue);
		long interval = getIntervalMillis(intervalValue);
		
		if (time < 0 || interval < 0) {
			Log.i(Christmas.TAG, "Invalid value for recurring notification interval, no recurring notifications were scheduled");
			return -1;
		}
		
		// debug
		// time = System.currentTimeMillis() + 1000;
		// interval = 3000;
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = recurringAlarmIntent(context);
		manager.cancel(alarmIntent);
		
		manager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, alarmIntent);
		
		return time;
	}
	
	public static void cancelRecurringAlarm(Context context) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = recurringAlarmIntent(context);
		manager.cancel(alarmIntent);
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
}