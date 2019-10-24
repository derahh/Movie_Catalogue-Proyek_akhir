package com.derahh.moviefavoritecatalogue.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.derahh.moviefavoritecatalogue.database.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.getColumnInt;
import static com.derahh.moviefavoritecatalogue.database.DatabaseContract.getColumnString;

public class TvShow implements Parcelable {

    private int id;
    private double userScore;
    private String title, year, description, photo;

    public TvShow(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.userScore = DatabaseContract.getColumnDouble(cursor, DatabaseContract.TvShowColumns.userScore);
        this.title = getColumnString(cursor, DatabaseContract.TvShowColumns.title);
        this.year = getColumnString(cursor, DatabaseContract.TvShowColumns.year);
        this.description = getColumnString(cursor, DatabaseContract.TvShowColumns.description);
        this.photo = getColumnString(cursor, DatabaseContract.TvShowColumns.photo);
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

    public TvShow(){
    }

    private TvShow(Parcel in) {
        this.id = in.readInt();
        this.userScore = in.readDouble();
        this.title = in.readString();
        this.year = in.readString();
        this.description = in.readString();
        this.photo = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
