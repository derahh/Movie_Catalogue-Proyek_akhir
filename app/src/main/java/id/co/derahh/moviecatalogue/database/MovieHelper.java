package id.co.derahh.moviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.Movie;

import static android.provider.BaseColumns._ID;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns.description;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns.photo;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns.title;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns.userScore;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns.year;
import static id.co.derahh.moviecatalogue.database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
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

    public ArrayList<Movie> getAllFavoriteMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(title)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(description)));
                movie.setYear(cursor.getString(cursor.getColumnIndexOrThrow(year)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(photo)));
                movie.setUserScore(cursor.getDouble(cursor.getColumnIndexOrThrow(userScore)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertFavoriteMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, movie.getId());
        contentValues.put(title, movie.getTitle());
        contentValues.put(description, movie.getDescription());
        contentValues.put(year, movie.getYear());
        contentValues.put(photo, movie.getPhoto());
        contentValues.put(userScore, movie.getUserScore());

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
