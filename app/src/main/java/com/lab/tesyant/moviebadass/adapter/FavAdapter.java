package com.lab.tesyant.moviebadass.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lab.tesyant.moviebadass.R;
import com.lab.tesyant.moviebadass.model.search.Results;

import java.util.ArrayList;

/**
 * Created by tesyant on 9/29/17.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private ArrayList<Results> mData = new ArrayList<>();
    private Activity activity;
    private Context context;
    private LayoutInflater mInflater;
    CustomItemClickListener listener;

    public FavAdapter (Context context, ArrayList<Results> data, CustomItemClickListener listener, Activity activity) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = data;
        this.listener = listener;
        this.activity = activity;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_movie, parent, false);
        final FavViewHolder myViewHolder = new FavViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, myViewHolder.getAdapterPosition() );
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvRelease.setText(mData.get(position).getReleaseDate());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + mData.get(position).getPosterPath())
                .fitCenter().into(holder.imgCover);
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
