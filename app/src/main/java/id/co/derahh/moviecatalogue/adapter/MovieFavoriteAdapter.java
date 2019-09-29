package id.co.derahh.moviecatalogue.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.co.derahh.moviecatalogue.Model.Movie;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.activity.DetailActivity;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder> {

    private ArrayList<Movie> listData = new ArrayList<>();
    private Context mContext;
    private Movie movie;

    public MovieFavoriteAdapter(Context context) {
        mContext = context;
    }

    public ArrayList<Movie> getListData() {
        return listData;
    }

    public void setListMovies(ArrayList<Movie> listData) {
        if (listData.size() > 0){
            this.listData.clear();
        }
        this.listData.addAll(listData);
        notifyDataSetChanged();
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
                showSelectedMovie(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvDescription;
        final ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }

    private void showSelectedMovie(Movie movie){
        Intent detailMovieIntent = new Intent(mContext, DetailActivity.class);
        detailMovieIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        mContext.startActivity(detailMovieIntent);
    }
}
