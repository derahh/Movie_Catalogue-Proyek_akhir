package com.derahh.moviefavoritecatalogue.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static final String SCHEME = "content";
    private static final String AUTHORITY = "id.co.derahh.moviecatalogue.database";

    private static String TABLE_MOVIE = "movie";

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

    private static String TABLE_TVSHOW= "tvshow";

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
}
