package id.co.derahh.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.activity.DetailActivity;

import static id.co.derahh.moviecatalogue.database.DatabaseContract.MovieColumns.CONTENT_URI;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private Movie movie;

    private ArrayList<Movie> getListData() {
        return listData;
    }

    public void setListData(ArrayList<Movie> listData) {
        this.listData.clear();
        this.listData = listData;
        notifyDataSetChanged();
    }

    private ArrayList<Movie> listData = new ArrayList<>();

    public MovieAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(getListData().get(i).getTitle());
        viewHolder.tvDescription.setText(getListData().get(i).getDescription());
        Glide.with(mContext).load(getListData().get(i).getPhoto()).into(viewHolder.imgPhoto);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie = getListData().get(i);
                showSelectedMovie(movie, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvDescription;
        final ImageView imgPhoto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }

    private void showSelectedMovie(Movie movie, int position){
        Intent detailMovieIntent = new Intent(mContext, DetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI + "/" + getListData().get(position).getId());
        detailMovieIntent.setData(uri);
        detailMovieIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        mContext.startActivity(detailMovieIntent);
    }
}
