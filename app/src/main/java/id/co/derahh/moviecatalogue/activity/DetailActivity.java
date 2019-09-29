package id.co.derahh.moviecatalogue.activity;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.Model.TvShow;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.database.MovieHelper;
import id.co.derahh.moviecatalogue.database.TvShowHelper;

public class DetailActivity extends AppCompatActivity{

    TextView tvTitle, tvDescription, tvYear, tvUserScore;
    ImageView imgPhoto;
    boolean isAlreadyLoved = false;

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    private Menu menu;
    private Movie movie;
    private MovieHelper movieHelper;
    private TvShow tvShow;
    private TvShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        imgPhoto = findViewById(R.id.img_photo);
        tvYear = findViewById(R.id.tv_year);
        tvUserScore = findViewById(R.id.user_score);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());

        showDetailData();
    }

    private void showDetailData(){
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

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
                getSupportActionBar().setTitle("Detail "+movie.getTitle());
            }

        }else {

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
                getSupportActionBar().setTitle("Detail "+tvShow.getTitle());
            }
        }
    }

    private int getUserScoreColor(double userScore){
        int userSCoreColorResourceId = 0;
        if (userScore < 7.0){
            userSCoreColorResourceId = R.color.user_score1;
        }else {
            userSCoreColorResourceId = R.color.user_score2;
        }
        return ContextCompat.getColor(this, userSCoreColorResourceId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        this.menu = menu;
        setFavorite();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            if (movie != null) {
                movieHelper.open();
                if (isAlreadyLoved) {
                    isAlreadyLoved = false;
                    movieHelper.deleteFavoriteMovie(movie.getId());
                    setFavorite();
                } else {
                    isAlreadyLoved = true;
                    movieHelper.insertFavoriteMovie(movie);
                    setFavorite();
                }
                movieHelper.close();
            } else {
                tvShowHelper.open();
                if (isAlreadyLoved) {
                    isAlreadyLoved = false;
                    tvShowHelper.deleteFavoriteMovie(tvShow.getId());
                    setFavorite();
                } else {
                    isAlreadyLoved = true;
                    tvShowHelper.insertFavoriteMovie(tvShow);
                    setFavorite();
                }
                tvShowHelper.close();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        MenuItem favorite = menu.findItem(R.id.action_favorite);
        if (isAlreadyLoved) {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red_24dp));
        } else {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_24dp));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movie != null) {
            isAlreadyLoved = movieHelper.isAlreadyLoved(movie.getId());
        } else {
            isAlreadyLoved = tvShowHelper.isAlreadyLoved(tvShow.getId());
        }
        Log.d("IsAlreadyLove", String.valueOf(isAlreadyLoved));
    }
}
