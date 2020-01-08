package com.derahh.moviecatalogueretrofit.model;

public class Movie {

    private int id;
    private double vote_average;
    private String title, release_date, overview, poster_path, photo;

    public Movie(int id, double vote_average, String title, String release_date, String overview, String poster_path) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.poster_path = poster_path;
        this.photo = "https://image.tmdb.org/t/p/w342/" + poster_path;
    }

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getPhoto() {
        return photo;
    }
}
