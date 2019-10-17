package id.co.derahh.moviecatalogue.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.Model.TvShow;

public class SearchViewModel extends ViewModel {

    private static final String TAG = MovieViewModel.class.getSimpleName();
    private static final String API_KEY = "06b9cd349f041c2e51292a90868062fc";
    private MutableLiveData<ArrayList<Movie>> listSearchMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> listSearchTvShow = new MutableLiveData<>();

    public void searchMovie(String query) {
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list = new ArrayList<>();

        String currentLanguage = Locale.getDefault().getISO3Language();
        String language = "";
        if (currentLanguage.equalsIgnoreCase("ind")) {
            language = "id-ID";
        } else if (currentLanguage.equalsIgnoreCase("eng")) {
            language = "en-US";
        }

        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=" + currentLanguage + "&query=" + query;
        Log.e(TAG, "setMovie: " + url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray movieResults = responseObject.getJSONArray("results");
                    for (int i = 0; i < movieResults.length(); i++) {
                        JSONObject currentMovie = movieResults.getJSONObject(i);
                        Movie movie = new Movie(currentMovie);
                        list.add(movie);
                    }
                    listSearchMovies.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailureMovie: ", error);
            }
        });
    }

    public LiveData<ArrayList<Movie>> getSearchMovie() {
        return listSearchMovies;
    }

    public void searchTvShow(String query) {
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> list = new ArrayList<>();

        String currentLanguage = Locale.getDefault().getISO3Language();
        String language = "";
        if (currentLanguage.equalsIgnoreCase("ind")) {
            language = "id-ID";
        } else if (currentLanguage.equalsIgnoreCase("eng")) {
            language = "en-US";
        }

        String url = " https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=" + currentLanguage + "&query=" + query;
        Log.e(TAG, "setTvShow: " + url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray movieResults = responseObject.getJSONArray("results");
                    for (int i = 0; i < movieResults.length(); i++) {
                        JSONObject currentMovie = movieResults.getJSONObject(i);
                        TvShow movie = new TvShow(currentMovie);
                        list.add(movie);
                    }
                    listSearchTvShow.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailureTvShow: ", error);
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getSearchTvShow() {
        return listSearchTvShow;
    }
}
