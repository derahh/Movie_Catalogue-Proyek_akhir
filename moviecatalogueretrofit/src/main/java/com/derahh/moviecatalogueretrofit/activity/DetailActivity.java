package com.derahh.moviecatalogueretrofit.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.derahh.moviecatalogueretrofit.R;
import com.derahh.moviecatalogueretrofit.model.Result;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvTitle, tvDescription, tvYear, tvUserScore;
    ImageView imgPhoto;

    private boolean isFavorite = false;

    private Result movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        imgPhoto = findViewById(R.id.img_photo);
        tvYear = findViewById(R.id.tv_year);
        tvUserScore = findViewById(R.id.user_score);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
    }


    private void showMovieData(){
        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getOverview());
        tvYear.setText(movie.getReleaseDate());
        tvUserScore.setText(String.format("%s", movie.getVoteAverage()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(this).load(movie.getPhoto()).apply(options).into(imgPhoto);

        GradientDrawable gradientDrawable = (GradientDrawable) tvUserScore.getBackground();
        int userScoreColor = getUserScoreColor(movie.getVoteAverage());
        gradientDrawable.setColor(userScoreColor);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail " + movie.getTitle());
        }
    }

    private int getUserScoreColor(double userScore) {
        int userSCoreColorResourceId;
        if (userScore < 7.0) {
            userSCoreColorResourceId = R.color.user_score1;
        } else {
            userSCoreColorResourceId = R.color.user_score2;
        }
        return ContextCompat.getColor(this, userSCoreColorResourceId);
    }

    private void setIconFavorite(Menu menu){
        MenuItem favorite = menu.findItem(R.id.action_favorite);
        if (isFavorite) {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp));
        } else {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_24dp));
        }
    }
}
