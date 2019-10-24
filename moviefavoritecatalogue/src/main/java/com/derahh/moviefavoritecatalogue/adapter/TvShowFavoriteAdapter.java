package com.derahh.moviefavoritecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.derahh.moviefavoritecatalogue.R;
import com.derahh.moviefavoritecatalogue.activity.DetailActivity;
import com.derahh.moviefavoritecatalogue.model.TvShow;

import java.util.ArrayList;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.ViewHolder> {

    private Context mContext;
    private TvShow tvShow;
    public ArrayList<TvShow> getListData() {
        return listData;
    }

    public void setListData(ArrayList<TvShow> listData) {
        this.listData.clear();
        this.listData = listData;
        notifyDataSetChanged();
    }

    private ArrayList<TvShow> listData = new ArrayList<>();

    public TvShowFavoriteAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_favorite, viewGroup, false);
        return new TvShowFavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(getListData().get(i).getTitle());
        viewHolder.tvDescription.setText(getListData().get(i).getDescription());
        Glide.with(mContext).load(getListData().get(i).getPhoto()).into(viewHolder.imgPhoto);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShow = getListData().get(i);
                showSelectedTvShow(tvShow);
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

    private void showSelectedTvShow(TvShow tvShow){
        Intent detailMovieIntent = new Intent(mContext, DetailActivity.class);
        detailMovieIntent.putExtra(DetailActivity.EXTRA_TV_SHOW, tvShow);
        mContext.startActivity(detailMovieIntent);
    }
}
