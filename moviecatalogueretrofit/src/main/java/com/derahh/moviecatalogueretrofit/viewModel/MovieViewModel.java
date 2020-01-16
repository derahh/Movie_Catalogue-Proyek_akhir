package com.derahh.moviecatalogueretrofit.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.derahh.moviecatalogueretrofit.database.MovieDao;
import com.derahh.moviecatalogueretrofit.database.MovieRoomDatabase;
import com.derahh.moviecatalogueretrofit.model.MovieResult;
import com.derahh.moviecatalogueretrofit.model.Result;
import com.derahh.moviecatalogueretrofit.service.APIClient;
import com.derahh.moviecatalogueretrofit.util.LanguageFormater;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends AndroidViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();
    private static final String API_KEY = "06b9cd349f041c2e51292a90868062fc";

    private MutableLiveData<MovieResult> dataMovies = new MutableLiveData<>();
    private LiveData<List<Result>> favoriteMovies;

    private APIClient api = APIClient.getInstance();
    private MovieDao movieDao;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        MovieRoomDatabase movieDB = MovieRoomDatabase.getDatabase(application);
        movieDao = movieDB.movieDao();
        favoriteMovies = movieDao.getFavMovie();
    }

    public void setMovie() {
        Log.d(TAG, "Running");

        api.getAPI().getAllMovie(API_KEY, LanguageFormater.checkCurrentLanguage()).enqueue(new Callback<MovieResult>() {
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

    public void InsertFavorite(Result result) {
        new InsertAsyncTask(movieDao).execute(result);
    }

    public void DeleteFavorite(Result result) {
        new DeleteAsyncTask(movieDao).execute(result);
    }

    public LiveData<List<Result>> getFavoriteMovie() {
        return favoriteMovies;
    }

    private class OperationAsyncTask extends AsyncTask<Result, Void, Void> {

        MovieDao mAsyncTaskDao;

        OperationAsyncTask(MovieDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationAsyncTask {

        InsertAsyncTask(MovieDao dao) {
            super(dao);
        }

        @Override
        protected Void doInBackground(Result... results) {
            mAsyncTaskDao.insert(results[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationAsyncTask {

        DeleteAsyncTask(MovieDao dao) {
            super(dao);
        }

        @Override
        protected Void doInBackground(Result... results) {
            mAsyncTaskDao.delete(results[0]);
            return null;
        }
    }
}
