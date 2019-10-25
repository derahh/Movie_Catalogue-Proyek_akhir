package id.co.derahh.moviecatalogue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.Model.TvShow;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.adapter.MovieAdapter;
import id.co.derahh.moviecatalogue.adapter.TvShowAdapter;
import id.co.derahh.moviecatalogue.viewModel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_TYPE = "extra_type";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvNoData;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.list_main);
        progressBar = findViewById(R.id.progress_bar);
        tvNoData = findViewById(R.id.tv_no_data);

        query = getIntent().getStringExtra(EXTRA_QUERY);

        progressBar.setVisibility(View.VISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.search_result));
        }

        String type = getIntent().getStringExtra(EXTRA_TYPE);

        if (type.equals("movie")) {
            setupSearchMovieViewModel();

        } else {
            setupSearchTvShowViewModel();
        }
    }

    private void setupSearchMovieViewModel() {
        SearchViewModel searchMovieViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchMovieViewModel.searchMovie(query);
        searchMovieViewModel.getSearchMovie().observe(this, getListMovie);
    }

    private void setupSearchTvShowViewModel() {
        SearchViewModel searchTvShowViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchTvShowViewModel.searchTvShow(query);
        searchTvShowViewModel.getSearchTvShow().observe(this, getListTvShow);
    }

    private final Observer<ArrayList<Movie>> getListMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                if (movies.size() > 0) {
                    showSearchMovieRecyclerView(movies);
                    progressBar.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    private final Observer<ArrayList<TvShow>> getListTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                if (tvShows.size() > 0) {
                    showSearchTvShowRecyclerView(tvShows);
                    progressBar.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    private void showSearchMovieRecyclerView(ArrayList<Movie> movies) {
        MovieAdapter searchMovieAdapter = new MovieAdapter(this);
        searchMovieAdapter.notifyDataSetChanged();
        searchMovieAdapter.setListData(movies);

        RecyclerView rvListSearchMovie = findViewById(R.id.list_main);
        rvListSearchMovie.setHasFixedSize(true);
        rvListSearchMovie.setLayoutManager(new LinearLayoutManager(this));
        rvListSearchMovie.setAdapter(searchMovieAdapter);
    }

    private void showSearchTvShowRecyclerView(ArrayList<TvShow> tvShows) {
        TvShowAdapter searchTvShowAdapter = new TvShowAdapter(this);
        searchTvShowAdapter.notifyDataSetChanged();
        searchTvShowAdapter.setListData(tvShows);

        RecyclerView rvListSearchTvShow = findViewById(R.id.list_main);
        rvListSearchTvShow.setHasFixedSize(true);
        rvListSearchTvShow.setLayoutManager(new LinearLayoutManager(this));
        rvListSearchTvShow.setAdapter(searchTvShowAdapter);
    }
}
