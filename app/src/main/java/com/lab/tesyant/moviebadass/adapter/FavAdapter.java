package com.lab.tesyant.moviebadass.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lab.tesyant.moviebadass.R;
import com.lab.tesyant.moviebadass.model.Results;

import java.util.LinkedList;

/**
 * Created by tesyant on 9/29/17.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private LinkedList<Results> listFav;
    private Activity activity;

    public FavAdapter (Activity activity) {
        this.activity = activity;
    }

    public LinkedList<Results> getListFav() {
        return listFav;
    }

    public void setListFav(LinkedList<Results> listFav) {
        this.listFav = listFav;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_movie, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        holder.tvTitle.setText(getListFav().get(position).getTitle());
        holder.tvRelease.setText(getListFav().get(position).getReleaseDate());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + listFav.get(position).getPosterPath())
                .fitCenter().into(holder.imgCover);
    }

    @Override
    public int getItemCount() {
        return getListFav().size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease;
        ImageView imgCover;

        public FavViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvRelease = (TextView) itemView.findViewById(R.id.tv_release_date);
            imgCover = (ImageView)itemView.findViewById(R.id.img_cover);
        }
    }
}
