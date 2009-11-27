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
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            
            int answerId = Christmas.answer(Christmas.isIt(), Locale.getDefault());
            String answer = context.getResources().getString(answerId);
            views.setTextViewText(R.id.answer, answer);
            
            manager.updateAppWidget(appWidgetId, views);
        }
	}
	
}