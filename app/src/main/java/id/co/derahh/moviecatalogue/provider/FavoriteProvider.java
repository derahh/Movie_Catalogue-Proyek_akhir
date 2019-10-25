package id.co.derahh.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import id.co.derahh.moviecatalogue.database.DatabaseContract;
import id.co.derahh.moviecatalogue.database.MovieHelper;
import id.co.derahh.moviecatalogue.database.TvShowHelper;
import id.co.derahh.moviecatalogue.fragment.MovieFavoriteFragment;
import id.co.derahh.moviecatalogue.fragment.TvShowFavoriteFragment;

import static id.co.derahh.moviecatalogue.database.DatabaseContract.AUTHORITY;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TABLE_MOVIE;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TABLE_TVSHOW;

public class FavoriteProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV_SHOW = 3;
    private static final int TV_SHOW_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private MovieHelper movieHelper;
    private TvShowHelper tvShowHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);

        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW, TV_SHOW);
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW + "/#", TV_SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        tvShowHelper = TvShowHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        movieHelper.open();
        tvShowHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV_SHOW:
                cursor = tvShowHelper.queryProvider();
                break;
            case TV_SHOW_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        movieHelper.open();
        tvShowHelper.open();
        long added;
        Uri favoriteUri;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(values);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.MovieColumns.CONTENT_URI, new MovieFavoriteFragment.DataObserver(new Handler(), getContext()));
                favoriteUri = Uri.parse(DatabaseContract.MovieColumns.CONTENT_URI + "/" + added);
                break;
            case TV_SHOW:
                added = tvShowHelper.insertProvider(values);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.TvShowColumns.CONTENT_URI, new TvShowFavoriteFragment.DataObserver(new Handler(), getContext()));
                favoriteUri = Uri.parse(DatabaseContract.TvShowColumns.CONTENT_URI + "/" + added);
                break;
            default:
                added = 0;
                favoriteUri = null;
                break;
        }
        return favoriteUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        movieHelper.open();
        tvShowHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.MovieColumns.CONTENT_URI, new MovieFavoriteFragment.DataObserver(new Handler(), getContext()));
                break;
            case TV_SHOW_ID:
                deleted = tvShowHelper.deleteProvider(uri.getLastPathSegment());
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseContract.TvShowColumns.CONTENT_URI, new TvShowFavoriteFragment.DataObserver(new Handler(), getContext()));
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
