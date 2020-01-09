package com.derahh.moviecatalogueretrofit.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.derahh.moviecatalogueretrofit.model.MovieResult;
import com.derahh.moviecatalogueretrofit.model.Result;
import com.derahh.moviecatalogueretrofit.service.APIService;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();
    private static final String API_KEY = "06b9cd349f041c2e51292a90868062fc";
    private MutableLiveData<MovieResult> dataMovies = new MutableLiveData<>();
    private MutableLiveData<List<Result>> listDataMovies = new MutableLiveData<>();

    public void setMovie() {
        Log.d(TAG, "Running");

        String currentLanguage = Locale.getDefault().getISO3Language();
        String language = "";
        if (currentLanguage.equalsIgnoreCase("ind")) {
            language = "id-ID";
        } else if (currentLanguage.equalsIgnoreCase("eng")) {
            language = "en-US";
        }

        String url = "https://api.themoviedb.org/3/movie/";
        Log.i(TAG, "setMovie: " + url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService api = retrofit.create(APIService.class);

        api.getAllMovie(API_KEY, language).enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                Log.d("movieTitle", response.body().getResults().get(0).getTitle());
                dataMovies.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public LiveData<MovieResult> getMovie() {
        return dataMovies;
    }
}
