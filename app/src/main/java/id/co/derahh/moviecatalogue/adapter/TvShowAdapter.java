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

import id.co.derahh.moviecatalogue.Model.TvShow;
import id.co.derahh.moviecatalogue.R;
import id.co.derahh.moviecatalogue.activity.DetailActivity;

import static id.co.derahh.moviecatalogue.database.DatabaseContract.TvShowColumns.CONTENT_URI;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private Context mContext;
    private TvShow tvShow;
    private ArrayList<TvShow> getListData() {
        return listData;
    }

    public void setListData(ArrayList<TvShow> listData) {
        this.listData.clear();
        this.listData = listData;
        notifyDataSetChanged();
    }

    private ArrayList<TvShow> listData = new ArrayList<>();

    public TvShowAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, viewGroup, false);
        return new TvShowAdapter.TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(getListData().get(i).getTitle());
        viewHolder.tvDescription.setText(getListData().get(i).getDescription());
        Glide.with(mContext).load(getListData().get(i).getPhoto()).into(viewHolder.imgPhoto);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShow = getListData().get(i);
                showSelectedTvShow(tvShow, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListData().size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvDescription;
        final ImageView imgPhoto;

        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }

    private void showSelectedTvShow(TvShow tvShow, int position){
        Intent detailTvShowIntent = new Intent(mContext, DetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI + "/" + getListData().get(position).getId());
        detailTvShowIntent.setData(uri);
        detailTvShowIntent.putExtra(DetailActivity.EXTRA_TV_SHOW, tvShow);
        mContext.startActivity(detailTvShowIntent);
    }
}
