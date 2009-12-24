package com.isitchristmas.android;

import java.util.Locale;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import com.isitchristmas.android.R;


public class ChristmasWidgetProvider extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildView(context);
            
            manager.updateAppWidget(appWidgetId, views);
        }
	}
	
	public static RemoteViews buildView(Context context) {
		int answerId = Christmas.answer(Christmas.isIt(), Locale.getDefault());
        String answer = context.getResources().getString(answerId);
        
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.answer, answer);
        return views;
	}
}