package id.co.derahh.moviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.TvShow;

import static android.provider.BaseColumns._ID;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns.description;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns.photo;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns.title;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns.userScore;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns.year;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TABLE_TVSHOW;

public class TvShowHelper {

    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<TvShow> getAllFavoriteTvShow() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        TvShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(title)));
                tvShow.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(description)));
                tvShow.setYear(cursor.getString(cursor.getColumnIndexOrThrow(year)));
                tvShow.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(photo)));
                tvShow.setUserScore(cursor.getDouble(cursor.getColumnIndexOrThrow(userScore)));
                arrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertFavoriteMovie(TvShow tvShow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, tvShow.getId());
        contentValues.put(title, tvShow.getTitle());
        contentValues.put(description, tvShow.getDescription());
        contentValues.put(year, tvShow.getYear());
        contentValues.put(photo, tvShow.getPhoto());
        contentValues.put(userScore, tvShow.getUserScore());

        database.insert(DATABASE_TABLE, null, contentValues);
    }

    public boolean isAlreadyLoved(int movieId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        boolean isFavorite = false;

        try {
            Cursor cursor;
            String sql = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + _ID + " = '" + movieId + "'";
            cursor = db.rawQuery(sql, null);
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFavorite;
    }

    public void deleteFavoriteMovie(int movieId) {
        database.delete(DATABASE_TABLE, _ID + " = '" + movieId + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
