package com.derahh.moviecatalogueretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.derahh.moviecatalogueretrofit.adapter.MovieAdapter;
import com.derahh.moviecatalogueretrofit.model.MovieResult;
import com.derahh.moviecatalogueretrofit.viewModel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieViewModel viewModel;
    private ProgressBar progressBar;
    private TextView tvNoData;
    private MovieAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list_movie);
        progressBar = findViewById(R.id.progress_bar);
        tvNoData = findViewById(R.id.tv_no_data);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovie().observe(this, getMovie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.category_movie);
        }

        loadData();

        addItem();
    }

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
        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
