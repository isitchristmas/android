package com.isitchristmas.android;

import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class ChristmasWidgetProvider extends AppWidgetProvider {

	/** 
	 * This flag is here solely for people who installed the app before the Christmas timer
	 * was added, so that the onUpdate method can also set the alarm if onEnabled was never
	 * called between now and Christmas.  Anyone who installs the updated app on Christmas Eve,
	 * with a widget already on their desktop, and who never reboots their phone before Christmas
	 * hits at midnight, will depend on this flag for that alarm to get initially set.
	 */
	public static boolean alarmSet = false;
	
	@Override
	public void onEnabled(Context context) {
		setAlarm(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
//		if (!alarmSet)
//			setAlarm(context);
		
		int answerId = Christmas.answer(Christmas.isIt(), Locale.getDefault());
        String answer = context.getResources().getString(answerId);
		
        // Perform this loop procedure for each widget that belongs to this provider
        final int length = appWidgetIds.length;
        for (int i=0; i<length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildView(context, answer);
            manager.updateAppWidget(appWidgetId, views);
        }
	}
	
	public void setAlarm(Context context) {
		long christmasTime = Christmas.time();
		
		Intent receiver = new Intent(context, ChristmasReceiver.class);
		PendingIntent christmas = PendingIntent.getBroadcast(context, 0, receiver, 0);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.RTC, christmasTime, christmas);
		
		alarmSet = true;
	}
	
	public static RemoteViews buildView(Context context, String answer) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.answer, answer);
        return views;
	}
}