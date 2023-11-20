package ph.edu.dlsu.mobdeve.tigcal.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.util.Calendar

/**
 * Implementation of App Widget functionality.
 */
class CountdownWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.countdown_widget)

    //Count down
    val christmasDay = Calendar.getInstance()
    christmasDay[Calendar.MONTH] = Calendar.DECEMBER
    christmasDay[Calendar.DAY_OF_MONTH] = 25
    val calendarNow = Calendar.getInstance()
    val days = (christmasDay.timeInMillis - calendarNow.timeInMillis) / (24 * 60 * 60 * 1000)
    if (days > 0) {
        views.setTextViewText(R.id.appwidget_text, days.toString())
    } else {
        views.setTextViewText(R.id.appwidget_text, "0")
    }

    //Open main activity on widget click
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}