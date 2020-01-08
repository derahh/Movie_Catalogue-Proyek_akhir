package com.derahh.moviecatalogueretrofit.service;

import com.derahh.moviecatalogueretrofit.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("movies")
    Call<List<Movie>> getAllMovie();
}
