package id.co.derahh.moviecatalogue;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.Movie;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}

