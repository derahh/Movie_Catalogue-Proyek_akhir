package com.derahh.moviecatalogueretrofit.service;

import com.derahh.moviecatalogueretrofit.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("now_playing")
    Call<MovieResult> getAllMovie(
            @Query("api_key") String key,
            @Query("language") String language
    );
}
