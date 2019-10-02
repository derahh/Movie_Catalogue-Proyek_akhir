package id.co.derahh.moviecatalogue.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.co.derahh.moviecatalogue.LoadTvShowCallback;
import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.Model.TvShow;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.adapter.TvShowFavoriteAdapter;
import id.co.derahh.moviecatalogue.database.MovieHelper;
import id.co.derahh.moviecatalogue.database.TvShowHelper;
import id.co.derahh.moviecatalogue.viewModel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements LoadTvShowCallback {

    private static final String TAG = TvShowFavoriteFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    TextView tvNoData;

    private TvShowFavoriteAdapter adapter;
    private TvShowHelper tvShowHelper;


    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_favorite,container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        tvNoData = view.findViewById(R.id.tv_no_data);
        recyclerView = view.findViewById(R.id.list_favorite);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();

        adapter = new TvShowFavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTvShowAsync(tvShowHelper, this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListData(list);
            }
        }

        return view;
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);
                Log.d(TAG, "run: PreExecute");
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShow> tvShows) {
        progressBar.setVisibility(View.GONE);
        if (tvShows.size() != 0) {
            adapter.setListData(tvShows);
            tvNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "postExecute: ");
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "postExecute: data null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tvShowHelper.close();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListData());
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<TvShowHelper> weakTvShowHelper;
        private final WeakReference<LoadTvShowCallback> weakCallback;

        private LoadTvShowAsync(TvShowHelper tvShowHelper, LoadTvShowCallback callback) {
            weakTvShowHelper = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakTvShowHelper.get().getAllFavoriteTvShow();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute(tvShows);
            Log.d(TAG, "onPostExecute: ");
        }
    }
}
