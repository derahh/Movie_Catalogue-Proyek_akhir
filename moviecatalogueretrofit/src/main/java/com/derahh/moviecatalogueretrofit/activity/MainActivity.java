package com.derahh.moviecatalogueretrofit.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.derahh.moviecatalogueretrofit.R;
import com.derahh.moviecatalogueretrofit.adapter.MovieAdapter;
import com.derahh.moviecatalogueretrofit.model.MovieResult;
import com.derahh.moviecatalogueretrofit.model.Result;
import com.derahh.moviecatalogueretrofit.viewModel.MovieViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.FavoriteClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvNoData;
    private TextView tvLastFavorite;

    private MovieViewModel viewModel;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list_movie);
        progressBar = findViewById(R.id.progress_bar);
        tvNoData = findViewById(R.id.tv_no_data);
        tvLastFavorite = findViewById(R.id.tv_last_favorite);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovie().observe(this, getMovie);
        viewModel.getFavoriteMovie().observe(this, getLastFavoriteMovie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.category_movie);
        }

        loadData();

        addItem();
    }

    private Observer<List<Result>> getLastFavoriteMovie = new Observer<List<Result>>() {
        @Override
        public void onChanged(List<Result> results) {
            if (results.size() != 0) {
                tvLastFavorite.setText(results.get(0).getTitle());
            } else {
                tvLastFavorite.setText("No Data Favorite");
            }
        }
    };

    private Observer<MovieResult> getMovie = new Observer<MovieResult>() {
        @Override
        public void onChanged(@Nullable MovieResult movies) {
            if (movies != null) {
                adapter.setListMovies(movies.getResults());
                progressBar.setVisibility(View.GONE);
            }else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    };

    private void loadData(){
        viewModel.setMovie();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void addItem(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MovieAdapter(this, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("ShowToast")
    @Override
    public void FavoriteClickListener(Result result) {
        if (adapter.isFavorite()) {
            viewModel.InsertFavorite(result);
            Toast.makeText(this, "Favorited " + result.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            viewModel.DeleteFavorite(result);
            Toast.makeText(this, "Unfavorited " + result.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
