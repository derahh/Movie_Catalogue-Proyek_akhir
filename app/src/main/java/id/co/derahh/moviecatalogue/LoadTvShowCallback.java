package id.co.derahh.moviecatalogue;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.TvShow;

public interface LoadTvShowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> tvShows);
}
