package com.isitchristmas.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ChristmasUtils {
	public static final String TAG = "IsItChristmas";

	
	public static void setAlarms(Context context) {
		setChristmasAlarm(context);
		//TODO: setRecurringChristmasAlarm(context);
	}
	
	public static void setChristmasAlarm(Context context) {
		long time = Christmas.time();
		
		// debug, 3 seconds from execution
		// time = System.currentTimeMillis() + 1000;
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = singleAlarmIntent(context);
	    manager.cancel(alarmIntent);
	    
	    manager.set(AlarmManager.RTC_WAKEUP, time, alarmIntent);
	}
	
	public static void cancelChristmasAlarm(Context context) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent alarmIntent = singleAlarmIntent(context);
	    manager.cancel(alarmIntent);
	}
	
	
	private static PendingIntent singleAlarmIntent(Context context) {
		return PendingIntent.getBroadcast(context, 0, new Intent(context, ChristmasNotificationReceiver.class), 0);
	}
}