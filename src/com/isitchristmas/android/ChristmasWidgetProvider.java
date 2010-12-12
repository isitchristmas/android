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

	@Override
	public void onEnabled(Context context) {
		long christmasTime = Christmas.time();
		
		Intent receiver = new Intent(context, ChristmasWidgetReceiver.class);
		PendingIntent christmas = PendingIntent.getBroadcast(context, 0, receiver, 0);
		
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.RTC, christmasTime, christmas);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {		
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
	
	public static RemoteViews buildView(Context context, String answer) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.answer, answer);
        return views;
	}
}