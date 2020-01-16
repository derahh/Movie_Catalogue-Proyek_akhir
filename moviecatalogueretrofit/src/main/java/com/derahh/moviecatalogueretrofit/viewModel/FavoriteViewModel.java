package com.derahh.moviecatalogueretrofit.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.derahh.moviecatalogueretrofit.database.MovieDao;
import com.derahh.moviecatalogueretrofit.model.Result;

public class FavoriteViewModel extends AndroidViewModel {

    private MovieDao movieDao;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    public void InsertFavorite(Result result) {
        new InsertAsyncTask(movieDao).execute(result);
    }

    public void DeleteFavorite(Result result) {
        new DeleteAsyncTask(movieDao).execute(result);
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
