package com.derahh.moviefavoritecatalogue.helper;

import android.database.Cursor;

import com.derahh.moviefavoritecatalogue.model.Movie;
import com.derahh.moviefavoritecatalogue.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.MovieColumns;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.TvShowColumns;


public class MappingHelper {

    public static ArrayList<Movie> mapCursorMovieToArrayList(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

            movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.title)));
            movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.description)));
            movie.setYear(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.year)));
            movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.photo)));
            movie.setUserScore(cursor.getDouble(cursor.getColumnIndexOrThrow(MovieColumns.userScore)));
            movies.add(movie);
        }
        return movies;
    }

    public static ArrayList<TvShow> mapCursorTvShowToArrayList(Cursor cursor) {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        while (cursor.moveToNext()) {
            TvShow tvShow = new TvShow();
            tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));

            tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.title)));
            tvShow.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.description)));
            tvShow.setYear(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.year)));
            tvShow.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.photo)));
            tvShow.setUserScore(cursor.getDouble(cursor.getColumnIndexOrThrow(TvShowColumns.userScore)));
            tvShows.add(tvShow);
        }
        return tvShows;
    }
}
