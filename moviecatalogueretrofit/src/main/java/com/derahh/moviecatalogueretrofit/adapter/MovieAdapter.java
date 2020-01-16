package com.derahh.moviecatalogueretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.derahh.moviecatalogueretrofit.R;
import com.derahh.moviecatalogueretrofit.model.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public interface FavoriteClickListener {
        void FavoriteClickListener(Result result);
    }

    private List<Result> listData = new ArrayList<>();
    private Context mContext;
    private boolean isFavorite = true;
    private FavoriteClickListener favoriteClickListener;

    public MovieAdapter(Context context, FavoriteClickListener favoriteClickListener) {
        mContext = context;
        this.favoriteClickListener = favoriteClickListener;
    }

    public List<Result> getListData() {
        return listData;
    }

    public void setListMovies(List<Result> listData) {
        if (listData.size() > 0){
            this.listData.clear();
        }
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(getListData().get(i).getTitle());
        viewHolder.tvDescription.setText(getListData().get(i).getOverview());

        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
        Glide.with(mContext).load(getListData().get(i).getPhoto()).apply(options).into(viewHolder.imgPhoto);

        viewHolder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteClickListener != null) {
//                    Toast.makeText(mContext, "Favorited " + getListData().get(i).getTitle(), Toast.LENGTH_SHORT).show();
                    favoriteClickListener.FavoriteClickListener(getListData().get(i));
                    setIconFavorite(viewHolder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvDescription;
        final ImageView imgPhoto, imgFavorite;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            imgFavorite = itemView.findViewById(R.id.img_favorite);
        }
    }

    private void setIconFavorite(ViewHolder holder){
        if(isFavorite()) {
            Glide.with(mContext).load(R.drawable.ic_favorite_red_24dp).into(holder.imgFavorite);
            isFavorite = true;
        } else {
            Glide.with(mContext).load(R.drawable.ic_favorite_border_24dp).into(holder.imgFavorite);
            isFavorite = false;
        }
    }
}
