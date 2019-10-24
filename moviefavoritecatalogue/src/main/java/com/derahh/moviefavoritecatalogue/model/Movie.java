package com.derahh.moviefavoritecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.derahh.moviefavoritecatalogue.database.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.getColumnDouble;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.getColumnInt;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.getColumnString;

public class Movie implements Parcelable {

    private int id;
    private double userScore;
    private String title, year, description, photo;

    public Movie(int id, double userScore, String title, String year, String description, String photo) {
        this.id = id;
        this.userScore = userScore;
        this.title = title;
        this.year = year;
        this.description = description;
        this.photo = photo;
    }

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.userScore = getColumnDouble(cursor, DatabaseContract.MovieColumns.userScore);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.title);
        this.year = getColumnString(cursor, DatabaseContract.MovieColumns.year);
        this.description = getColumnString(cursor, DatabaseContract.MovieColumns.description);
        this.photo = getColumnString(cursor, DatabaseContract.MovieColumns.photo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.userScore);
        dest.writeString(this.title);
        dest.writeString(this.year);
        dest.writeString(this.description);
        dest.writeString(this.photo);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.userScore = in.readDouble();
        this.title = in.readString();
        this.year = in.readString();
        this.description = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
