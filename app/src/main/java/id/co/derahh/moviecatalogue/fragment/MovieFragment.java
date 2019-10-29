package id.co.derahh.moviecatalogue.fragment;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.activity.SearchActivity;
import id.co.derahh.moviecatalogue.adapter.MovieAdapter;
import id.co.derahh.moviecatalogue.viewModel.MovieViewModel;

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
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        recyclerView = view.findViewById(R.id.list_main);
        progressBar = view.findViewById(R.id.progress_bar);
        tvNoData = view.findViewById(R.id.tv_no_data);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovie().observe(this, getMovie);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.category_movie);
        }
        setHasOptionsMenu(true);

        loadData();

        addItem();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(searchView.getContext(), SearchActivity.class)));
            searchView.setQueryHint(getResources().getString(R.string.search_movie));
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Intent searchMovieIntent = new Intent(getActivity(), SearchActivity.class);
                    searchMovieIntent.putExtra(SearchActivity.EXTRA_QUERY, s);
                    searchMovieIntent.putExtra(SearchActivity.EXTRA_TYPE, "movie");
                    startActivity(searchMovieIntent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
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
