package id.co.derahh.moviecatalogue.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.activity.DetailActivity;

public class ReleaseTodayReminderReceiver extends BroadcastReceiver {

    private static final String API_KEY = "06b9cd349f041c2e51292a90868062fc";
    private static final int NOTIF_ID_REPEATING = 102;
    private static final String CHANNEL_ID = "todayremainder" ;
    public static CharSequence CHANNEL_NAME = "NOTIFICATION";
    private List<Movie> movieList = new ArrayList<>();

    public ReleaseTodayReminderReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String title = context.getString(R.string.release_today);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String currentDate = sdf.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+ API_KEY +"&primary_release_date.gte="+ currentDate +"&primary_release_date.lte="+ currentDate;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        Movie listMovie = new Movie(movie);
                        if (!listMovie.getPhoto().equals("null")) {
                            showNotification(context, listMovie);
                        }
                    }

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", "Get data failed!");
            }
        });

//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + currentDate + "&primary_release_date.lte=" + currentDate;
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String result = new String(responseBody);
//                try {
//                    JSONObject responseObject = new JSONObject(result);
//                    JSONArray movieResults = responseObject.getJSONArray("results");
//                    for (int i = 0; i < movieResults.length(); i++) {
//                        JSONObject currentMovie = movieResults.getJSONObject(i);
//                        Movie movie = new Movie(currentMovie);
//                        movieList.add(movie);
//                    }
//                    if (movieList.size() > 0) {
//                        for (int i = 0; i < movieList.size(); i++) {
//                            Movie movie2 = movieList.get(i);
//                            showAlarmNotification(context, title, movie2.getTitle(), i);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Toast.makeText(context, R.string.toast_failed, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void showNotification(Context context, Movie movie) {

        Intent intent = new Intent(context, DetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/" + movie.getId());
        intent.setData(uri);
        intent.putExtra(DetailActivity.EXTRA_TYPE, "movie");
        intent.putExtra(DetailActivity.EXTRA_ID, movie.getId());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(context.getString(R.string.message_release_today_reminder))
                .setContentText(movie.getTitle() + context.getString(R.string.has_release_today))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        if (notificationManager != null) notificationManager.notify(movie.getId(), notification);
    }

    private void showAlarmNotification(Context context, String title, String content, int notifId) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(notifId, notification);
        }
    }

    public void setRepeatingAlarm(Context context) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 38);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                getPendingIntent(context));
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, ReleaseTodayReminderReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    }
}
