package id.co.derahh.moviecatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns;
import id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static String DATABASE_NAME = "dbmoviecatalogue";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE =
            String.format("CREATE TABLE %s"
                            + "(%s INTEGER NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s REAL NOT NULL)",
                    DatabaseContract.TABLE_MOVIE,
                    MovieColumns._ID,
                    MovieColumns.title,
                    MovieColumns.description,
                    MovieColumns.year,
                    MovieColumns.photo,
                    MovieColumns.userScore);

    private static final String SQL_CREATE_TABLE_TV_SHOW =
            String.format("CREATE TABLE %s"
                            + "(%s INTEGER NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s TEXT NOT NULL,"
                            + "%s REAL NOT NULL)",
                    DatabaseContract.TABLE_TVSHOW,
                    TvShowColumns._ID,
                    TvShowColumns.title,
                    TvShowColumns.description,
                    TvShowColumns.year,
                    TvShowColumns.photo,
                    TvShowColumns.userScore);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        onCreate(db);
    }
}
