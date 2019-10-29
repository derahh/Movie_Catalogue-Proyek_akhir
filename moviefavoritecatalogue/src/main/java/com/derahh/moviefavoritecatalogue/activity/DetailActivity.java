package com.derahh.moviefavoritecatalogue.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.derahh.moviefavoritecatalogue.R;
import com.derahh.moviefavoritecatalogue.model.Movie;
import com.derahh.moviefavoritecatalogue.model.TvShow;

public class DetailActivity extends AppCompatActivity{

    TextView tvTitle, tvDescription, tvYear, tvUserScore;
    ImageView imgPhoto;

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        imgPhoto = findViewById(R.id.img_photo);
        tvYear = findViewById(R.id.tv_year);
        tvUserScore = findViewById(R.id.user_score);

        showDetailData();
    }

    private void showDetailData(){
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        if (movie != null) {

            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getDescription());
            tvYear.setText(movie.getYear());
            tvUserScore.setText(String.format("%s", movie.getUserScore()));

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_broken_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(this).load(movie.getPhoto()).apply(options).into(imgPhoto);

            GradientDrawable gradientDrawable = (GradientDrawable) tvUserScore.getBackground();
            int userScoreColor = getUserScoreColor(movie.getUserScore());
            gradientDrawable.setColor(userScoreColor);

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Detail "+ movie.getTitle());
            }

        }else if (tvShow != null) {

            tvTitle.setText(tvShow.getTitle());
            tvDescription.setText(tvShow.getDescription());
            tvYear.setText(tvShow.getYear());
            tvUserScore.setText(String.format("%s", tvShow.getUserScore()));

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_broken_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(this).load(tvShow.getPhoto()).apply(options).into(imgPhoto);

            GradientDrawable gradientDrawable = (GradientDrawable) tvUserScore.getBackground();
            int userScoreColor = getUserScoreColor(tvShow.getUserScore());
            gradientDrawable.setColor(userScoreColor);

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Detail "+ tvShow.getTitle());
            }
        }
    }

    private int getUserScoreColor(double userScore){
        int userSCoreColorResourceId;
        if (userScore < 7.0){
            userSCoreColorResourceId = R.color.user_score1;
        }else {
            userSCoreColorResourceId = R.color.user_score2;
        }
        return ContextCompat.getColor(this, userSCoreColorResourceId);
    }
}
