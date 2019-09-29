package id.co.derahh.moviecatalogue.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.adapter.MovieAdapter;
import id.co.derahh.moviecatalogue.viewModel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MovieViewModel viewModel;
    ProgressBar progressBar;
    TextView tvNoData;
    MovieAdapter adapter;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        progressBar = view.findViewById(R.id.progress_bar);
        tvNoData = view.findViewById(R.id.tv_no_data);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovie().observe(this, getMovie);

        addItem();

        return view;
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setListData(movies);
                progressBar.setVisibility(View.GONE);
            }else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    };

    private void addItem(){
        viewModel.setMovie();
        progressBar.setVisibility(View.VISIBLE);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MovieAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }
}
