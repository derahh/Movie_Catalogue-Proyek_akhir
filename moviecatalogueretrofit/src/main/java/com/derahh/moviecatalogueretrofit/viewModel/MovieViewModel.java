package com.derahh.moviecatalogueretrofit.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.derahh.moviecatalogueretrofit.model.Movie;
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
    private MutableLiveData<List<Movie>> listDataMovies = new MutableLiveData<>();

    public void setMovie() {
        Log.d(TAG, "Running");

        String currentLanguage = Locale.getDefault().getISO3Language();
        String language = "";
        if (currentLanguage.equalsIgnoreCase("ind")) {
            language = "id-ID";
        } else if (currentLanguage.equalsIgnoreCase("eng")) {
            language = "en-US";
        }

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=" + language;
        Log.e(TAG, "setMovie: " + url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService api = retrofit.create(APIService.class);

        api.getAllMovie().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                listDataMovies.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });
    }
}
