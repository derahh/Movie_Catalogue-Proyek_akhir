package id.co.derahh.moviecatalogue.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns{

        public static String title = "title";
        public static String description = "description";
        public static String year = "year";
        public static String photo = "photo";
        public static String userScore= "userScore";
    }

    public static String TABLE_TVSHOW= "tvshow";

    public static final class TvShowColumns implements BaseColumns{

        public static String title = "title";
        public static String description = "description";
        public static String year = "year";
        public static String photo = "photo";
        public static String userScore= "userScore";
    }
}
