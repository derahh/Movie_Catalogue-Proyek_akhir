package com.derahh.moviecatalogueretrofit.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.derahh.moviecatalogueretrofit.model.MovieResult;
import com.derahh.moviecatalogueretrofit.service.APIClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();
    private static final String API_KEY = "06b9cd349f041c2e51292a90868062fc";
    private MutableLiveData<MovieResult> dataMovies = new MutableLiveData<>();
    private APIClient api = APIClient.getInstance();

    public void setMovie() {
        Log.d(TAG, "Running");

        String currentLanguage = Locale.getDefault().getISO3Language();
        String language = "";
        if (currentLanguage.equalsIgnoreCase("ind")) {
            language = "id-ID";
        } else if (currentLanguage.equalsIgnoreCase("eng")) {
            language = "en-US";
        }

        api.getAPI().getAllMovie(API_KEY, language).enqueue(new Callback<MovieResult>() {
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
