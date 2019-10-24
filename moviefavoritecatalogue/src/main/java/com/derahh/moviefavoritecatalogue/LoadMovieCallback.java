package com.derahh.moviefavoritecatalogue;

import android.database.Cursor;

public interface LoadMovieCallback {
    void postExecute(Cursor cursor);
}

