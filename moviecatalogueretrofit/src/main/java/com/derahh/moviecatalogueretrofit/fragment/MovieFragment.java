package com.derahh.moviecatalogueretrofit.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.derahh.moviecatalogueretrofit.R;
import com.derahh.moviecatalogueretrofit.adapter.MovieAdapter;
import com.derahh.moviecatalogueretrofit.model.MovieResult;
import com.derahh.moviecatalogueretrofit.viewModel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieViewModel viewModel;
    private ProgressBar progressBar;
    private TextView tvNoData;
    private MovieAdapter adapter;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_movie,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.list_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        tvNoData = view.findViewById(R.id.tv_no_data);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovie().observe(this, getMovie);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MovieAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
