package id.co.derahh.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.database.DatabaseContract;
import id.co.derahh.moviecatalogue.database.DatabaseHelper;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Movie> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor cursor;

    private final static String TAG = "WIDGET_DATA";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        dataBanner();
    }

    @Override
    public void onDataSetChanged() {
        dataBanner();
    }

    private void dataBanner(){
        mWidgetItems.clear();

        DatabaseHelper helper = new DatabaseHelper(mContext);
        SQLiteDatabase database = helper.getWritableDatabase();

        cursor = database.query(DatabaseContract.TABLE_MOVIE, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor);
                mWidgetItems.add(movie);
                Log.i(TAG, "loadWidgetData: " + movie.getTitle());
                Log.i(TAG, "loadWidgetData: " + movie.getPhoto());
                Log.i(TAG, "loadWidgetData: " + mWidgetItems.size());
            } while (cursor.moveToNext());
        }

        cursor = database.query(DatabaseContract.TABLE_TVSHOW, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor);
                mWidgetItems.add(movie);

                Log.i(TAG, "loadWidgetData: " + movie.getTitle());
                Log.i(TAG, "loadWidgetData: " + movie.getPhoto());
                Log.i(TAG, "loadWidgetData: " + mWidgetItems.size());
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
        mWidgetItems.clear();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String photo = mWidgetItems.get(i).getPhoto();
        String title = mWidgetItems.get(i).getTitle();
        Log.i(TAG, "loadWidgetData: " + photo);
        try {
            Bitmap preview = Glide.with(mContext)
                    .asBitmap()
                    .load(photo)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, preview);
        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, "error");
        }

        Bundle extras = new Bundle();
        extras.putString(ImagesBannerWidget.EXTRA_ITEM, title);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
