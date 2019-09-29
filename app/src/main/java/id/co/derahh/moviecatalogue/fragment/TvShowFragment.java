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

import id.co.derahh.moviecatalogue.Model.TvShow;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.adapter.TvShowAdapter;
import id.co.derahh.moviecatalogue.viewModel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MovieViewModel viewModel;
    ProgressBar progressBar;
    TextView tvNoData;
    TvShowAdapter adapter;

    public TvShowFragment() {
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
        viewModel.getTvShow().observe(this, getTvShow);

        addItem();

        return view;
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setListData(tvShows);
                progressBar.setVisibility(View.GONE);
            }else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    };

    private void addItem(){
        viewModel.setTvShow();
        progressBar.setVisibility(View.VISIBLE);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TvShowAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }}
