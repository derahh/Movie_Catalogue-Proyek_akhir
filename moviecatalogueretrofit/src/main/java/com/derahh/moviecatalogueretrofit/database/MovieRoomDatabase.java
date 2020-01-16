package com.derahh.moviecatalogueretrofit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.derahh.moviecatalogueretrofit.model.Result;

@Database(entities = {Result.class}, version = 1)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static volatile MovieRoomDatabase mInstance;

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (mInstance == null) {
            synchronized (MovieRoomDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return mInstance;
    }
}
