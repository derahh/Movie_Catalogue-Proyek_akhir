package com.derahh.moviecatalogueretrofit.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.derahh.moviecatalogueretrofit.model.Result;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Result result);

    @Delete
    void delete(Result result);

    @Query("SELECT * FROM movie_table")
    LiveData<List<Result>> getFavMovie();
}
