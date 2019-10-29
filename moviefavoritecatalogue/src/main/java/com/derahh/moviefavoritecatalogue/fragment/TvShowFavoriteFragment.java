package com.derahh.moviefavoritecatalogue.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.derahh.moviefavoritecatalogue.LoadMovieCallback;
import com.derahh.moviefavoritecatalogue.R;
import com.derahh.moviefavoritecatalogue.adapter.TvShowFavoriteAdapter;
import com.derahh.moviefavoritecatalogue.helper.MappingHelper;
import com.derahh.moviefavoritecatalogue.model.TvShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.TvShowColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements LoadMovieCallback {

    private static final String TAG = TvShowFavoriteFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private ProgressBar progressBar;
    private TextView tvNoData;

    private TvShowFavoriteAdapter adapter;


    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_favorite,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        tvNoData = view.findViewById(R.id.tv_no_data);
        RecyclerView recyclerView = view.findViewById(R.id.list_favorite);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        adapter = new TvShowFavoriteAdapter(getContext());
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadMovieAsync(getContext(), this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListData(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        ArrayList<TvShow> tvShows = MappingHelper.mapCursorTvShowToArrayList(cursor);
        if (tvShows != null) {
            if (tvShows.size() > 0) {
                adapter.setListData(tvShows);
                tvNoData.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, " MOVIE TIDAK NULL");
            } else {
                tvNoData.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                adapter.setListData(new ArrayList<TvShow>());
                Log.d(TAG, " MOVIE NULL");
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            adapter.setListData(new ArrayList<TvShow>());
            Log.d(TAG, " MOVIE NULL");
        }
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
            Log.d(TAG, "onPostExecute: ");
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}
