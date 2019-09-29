package id.co.derahh.moviecatalogue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {

    private int id;
    private double userScore;
    private String title, year, description, photo;

    public TvShow(JSONObject currentMovie) {
        try {
            int id = currentMovie.getInt("id");
            double userScore = currentMovie.getDouble("vote_average");
            String title = currentMovie.getString("name");
            String year = currentMovie.getString("first_air_date");
            String description = currentMovie.getString("overview");
            String posterPath = currentMovie.getString("poster_path");
            String photo = "https://image.tmdb.org/t/p/w342/" + posterPath;

            this.id = id;
            this.userScore = userScore;
            this.title = title;
            this.year = year;
            this.description = description;
            this.photo = photo;
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
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
