package id.co.derahh.moviecatalogue.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static final String SCHEME = "content";
    public static final String AUTHORITY = "id.co.derahh.moviecatalogue.database";

    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns{

        public static String title = "title";
        public static String description = "description";
        public static String year = "year";
        public static String photo = "photo";
        public static String userScore= "userScore";


        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static String TABLE_TVSHOW= "tvshow";

    public static final class TvShowColumns implements BaseColumns{

        public static String title = "title";
        public static String description = "description";
        public static String year = "year";
        public static String photo = "photo";
        public static String userScore= "userScore";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TVSHOW)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static Double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}
